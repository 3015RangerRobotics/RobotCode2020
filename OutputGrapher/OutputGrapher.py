# import xlsxwriter module --> needed to be installed once w/ 'pip install xlsxwriter'
import xlsxwriter
# Standard imports
import os
import csv

# Creates a relative path for file reading / writing
THIS_FOLDER = os.path.dirname(os.path.abspath(__file__))

# Generic class to define the needed data for a subsystem. This includes
# basic graphing attributes and the ability to add unique:
# time, actual and expected data fields from .csv file or other soruce (future)
class SubsystemData:
    
    def __init__ ( self, name, title, xAxisName, yAxisName, 
                  expectedDataHeaderName, actualDataHeaderName ):
        self.name                   = name
        self.title                  = title
        self.xAxisName              = xAxisName
        self.yAxisName              = yAxisName
        self.expectedDataHeaderName = expectedDataHeaderName
        self.actualDataHeaderName   = actualDataHeaderName
        self.timeData               = []
        self.actualData             = []
        self.expectedData           = []
    
    # Updates all data lists equally as new line data comes in from file or main()
    # NOTE: All data lists should contain the same number of entries at any given time.
    # The only caller to add data should be through this function to avoid unexpected problems
    # when generating the sheets and graphs
    def addToData ( self, data: list ):
        # Add to time data
        maxTimeIndex = len(self.timeData) - 1
        lastTime = 0
        if maxTimeIndex > -1:
            lastTime = self.timeData[maxTimeIndex]
        self.timeData.append( lastTime + TIME_SCALE )
        # Casted to float due to reading in from text (.txt -> .csv format)
        self.expectedData.append(float(data[0]))
        self.actualData.append(float(data[1]))

    # If data has been added to a list, it is assumed the subsystem is in use.
    # This also means if data for a certain subsystem hasn't been provided then
    # other classes / functions know this by this simple call.
    def isSubSystemInUse ( self ):
        if (len(self.timeData) or len(self.actualData) or len(self.expectedData)) == 0:
            return False
        return True

# This class contains all of the logic for the output excel file and its tables / graphs.
# The class variables and __init__ formatters can be edited for changing up the graph style
# and location of certain excel attributes
class SheetProperties:
    # ASCII representation of numbers. Recommended to not touch these.
    ASCII_A            = 65
    ASCII_B            = 66
    ASCII_C            = 67
    # Edit these if more fields are added in the future or need to move
    # Starting position of the graphs
    COLUMN_OFFSET      = 3
    CHART_ROW_SPACING  = 15
    CHART_COLUMN_START = 'M'
    
    def __init__ ( self, subsystemList: list ):
        self.subsystems     = subsystemList
        # Creates our new workbook to add tables / graphs to
        self.workbook       = xlsxwriter.Workbook(SHEET_NAME)
        # Different formatting levels
        self.groupingFormat = self.workbook.add_format({'font_size':20, 'align':'center', 'bold': 1})
        self.headerFormat   = self.workbook.add_format({'font_size':14, 'align':'center', 'bold': 1})
        self.entryFormat    = self.workbook.add_format({'font_size':12, 'align':'center'})
    
    # Controls the building up of our excel output file
    def generateFile ( self ):
        # Check for if subsystems has been filled
        if self.subsystems == []:
            raise AssertionError("[GRAPHER] No data provided! No sheet can be generated.")
        # Add a worksheet to our workbook
        blankWorksheet = self.workbook.add_worksheet()  
        # Generate tables within the worksheet (fill from left to right)
        worksheet = self.generateTables(blankWorksheet)
        # Format the columns to be equal distance apart
        self.formatWidthColumns(worksheet)
        # Starting from the right, create graphs from M1 Column
        # then to M15... M30 etc.
        self.generateGraphs(worksheet)
        # Close workbook for clean exit
        self.workbook.close()
    
    # Generates a clean view of all of the .csv subsystem data
    def generateTables ( self, worksheet ):
        # Creates a start w/ A, B and C columns before loop begins
        COL_1 = SheetProperties.ASCII_A
        COL_2 = SheetProperties.ASCII_B
        COL_3 = SheetProperties.ASCII_C
        # Only fill in data for subsystems that are present
        for count, subsystem in enumerate(self.subsystems):
            if count != 0:
                # Needed offsets for if more than subsystem has been provided AND from
                # overlapping data
                COL_1 = SheetProperties.ASCII_A + (SheetProperties.COLUMN_OFFSET*count)
                COL_2 = SheetProperties.ASCII_B + (SheetProperties.COLUMN_OFFSET*count)
                COL_3 = SheetProperties.ASCII_C + (SheetProperties.COLUMN_OFFSET*count)
            # Begin subsystem base name text with the grouping format. EX A1:C1... -> D1:F1 etc.
            worksheet.merge_range('%s1:%s1' % (chr(COL_1), chr(COL_3)), subsystem.name, self.groupingFormat)
            # Begin subsystem header names text with the header format. EX A2, B2, C2... -> D2, E2, F2 etc.
            headings = [TIME_AXIS, subsystem.expectedDataHeaderName, subsystem.actualDataHeaderName]  
            worksheet.write_row('%s2' % (chr(COL_1)), headings, self.headerFormat)
            # Begin subsystem header data values with the entry format. EX A3:A(N), B3:B(N), C3:C(N)... 
            # -> D3:D(N), E3:E(N), F3:F(N)... where N is the last entry in the data list
            data = [  
                subsystem.timeData,
                subsystem.expectedData,
                subsystem.actualData,
            ]
            worksheet.write_column('%s3' % (chr(COL_1)), data[0], self.entryFormat)
            worksheet.write_column('%s3' % (chr(COL_2)), data[1], self.entryFormat)
            worksheet.write_column('%s3' % (chr(COL_3)), data[2], self.entryFormat)
        # Returned completed worksheet with filled in tables
        return worksheet

    # This searches for the longest header name and makes this the column width 
    # (plus 10 units) for each column generated in the tables
    def formatWidthColumns ( self, worksheet ):
        # Initialize with time_axis since every subsystem has the same one.. Less parsing
        columnWidth = len(TIME_AXIS)
        for subsystem in self.subsystems:
            headings = [subsystem.expectedDataHeaderName, subsystem.actualDataHeaderName]
            for header in headings:
                if len(header) > columnWidth:
                    columnWidth = len(header)
        maxColumn = len(self.subsystems) * SheetProperties.COLUMN_OFFSET
        worksheet.set_column(0, maxColumn, columnWidth+10)
    
    # Generates the graphs from the table data above
    def generateGraphs ( self, worksheet ):
        COL_1 = SheetProperties.ASCII_A
        COL_2 = SheetProperties.ASCII_B
        COL_3 = SheetProperties.ASCII_C
        # Can be expanded in the future to use a different sheet... just will need a new 
        # worksheet to be created before this
        sheetName = "Sheet1"
        # Only create graphs for subsystems tables that were created
        for count, subsystem in enumerate(self.subsystems):
            # Generate a blank scatter graph with smooth line properties
            chart = self.workbook.add_chart({'type': 'scatter', 'subtype': 'smooth'})  
            if count != 0:
                COL_1 = SheetProperties.ASCII_A + (SheetProperties.COLUMN_OFFSET*count)
                COL_2 = SheetProperties.ASCII_B + (SheetProperties.COLUMN_OFFSET*count)
                COL_3 = SheetProperties.ASCII_C + (SheetProperties.COLUMN_OFFSET*count)
            # Expected Series Plot Logic
            # Caputres x-axis values from table @ that subsystem (same one used in expected and actual plots)
            categories = '=%s!$%s$3:$%s$%d' % (sheetName, chr(COL_1), chr(COL_1), len(subsystem.timeData)+1)
            # Line header label in graph for the expected plot
            nameExpected       = '=%s!$%s$2'        % (sheetName, chr(COL_2)) 
            # Caputres y-axis values from table @ that subsystem
            valuesExpected     = '=%s!$%s$3:$%s$%d' % (sheetName, chr(COL_2), chr(COL_2), len(subsystem.expectedData)+1)
            # Generic excel stuff..
            chart.add_series({  
                'name'       : nameExpected,
                'categories' : categories,
                'values'     : valuesExpected,
            })
        
            # Actual Series Plot Logic
            # Line header label in graph for the actual plot
            nameActual       = '=%s!$%s$2'        % (sheetName, chr(COL_3))
            # Caputres y-axis values from table @ that subsystem
            valuesActual     = '=%s!$%s$3:$%s$%d' % (sheetName, chr(COL_3), chr(COL_3), len(subsystem.actualData)+1)
            # Generic excel stuff..
            chart.add_series({  
                'name'       : nameActual,
                'categories' : categories,
                'values'     : valuesActual,
            })
            # Set the graph properties from subsystem data
            chart.set_title ({'name': subsystem.title})
            chart.set_x_axis({'name': subsystem.xAxisName})
            chart.set_y_axis({'name': subsystem.yAxisName})
            # Sets style of chart... Can change :)
            # Link: https://xlsxwriter.readthedocs.io/example_chart_styles.html
            chart.set_style(11)
            
            # Insert our completed chart into the excel worksheet (right of the table columns then work top to bottom)
            worksheet.insert_chart('%s%d' % (SheetProperties.CHART_COLUMN_START, SheetProperties.CHART_ROW_SPACING * count+1), chart)

###############################################
#
# TABLE / GRAPH CONSTANTS
#
##############################################
# NOTE: This is where the customizable aspect of the program comes in, feel free to change these variables 
# to update the outputted excel file as needed

# INPUT / OUTPUT 
DATA_FILE        = "SUBSYSTEM_PRINTOUT_DATA.csv"
SHEET_NAME       = "Test Data Sheet.xlsx"
VALID_DATA_NAMES = ["shooter", "drive_train_left", "drive_train_right", "turret"]

# X AXIS / TIME GRAPH PROPERTIES
TIME_SCALE = 10 # ms
TIME_UNITS = "ms"
TIME_AXIS  = "Time (%s)" % TIME_UNITS

# SHOOTER GRAPH PROPERTIES -> VALID_DATA_NAMES[0]
SHOOTER_BASE_NAME            = "Shooter"
SHOOTER_TITLE                = "%s: Actual vs Expected" % (SHOOTER_BASE_NAME)
SHOOTER_X_AXIS               = TIME_AXIS
SHOOTER_Y_AXIS               = "Velocity (RPM)"
SHOOTER_EXPECTED_DATA_HEADER = "Expected Shooting Speed"
SHOOTER_ACTUAL_DATA_HEADER   = "Actual Shooting Speed"
SHOOTER = SubsystemData(SHOOTER_BASE_NAME, SHOOTER_TITLE, SHOOTER_X_AXIS, SHOOTER_Y_AXIS, 
                        SHOOTER_EXPECTED_DATA_HEADER, SHOOTER_ACTUAL_DATA_HEADER)

# DRIVE TRAIN LEFT GRAPH PROPERTIES -> VALID_DATA_NAMES[1]
DRIVE_TRAIN_LEFT_BASE_NAME            = "Drive Train Left"
DRIVE_TRAIN_LEFT_TITLE                = "%s: Actual vs Expected" % (DRIVE_TRAIN_LEFT_BASE_NAME)
DRIVE_TRAIN_LEFT_X_AXIS               = TIME_AXIS
DRIVE_TRAIN_LEFT_Y_AXIS               = "Position (Feet)"
DRIVE_TRAIN_LEFT_EXPECTED_DATA_HEADER = "Expected Drive Left Position"
DRIVE_TRAIN_LEFT_ACTUAL_DATA_HEADER   = "Actual Drive Left Position"
DRIVE_TRAIN_LEFT = SubsystemData(DRIVE_TRAIN_LEFT_BASE_NAME, DRIVE_TRAIN_LEFT_TITLE, DRIVE_TRAIN_LEFT_X_AXIS, DRIVE_TRAIN_LEFT_Y_AXIS, 
                                 DRIVE_TRAIN_LEFT_EXPECTED_DATA_HEADER, DRIVE_TRAIN_LEFT_ACTUAL_DATA_HEADER)

# DRIVE TRAIN RIGHT GRAPH PROPERTIES -> VALID_DATA_NAMES[2]
DRIVE_TRAIN_RIGHT_BASE_NAME            = "Drive Train Right"
DRIVE_TRAIN_RIGHT_TITLE                = "%s: Actual vs Expected" % (DRIVE_TRAIN_RIGHT_BASE_NAME)
DRIVE_TRAIN_RIGHT_X_AXIS               = TIME_AXIS
DRIVE_TRAIN_RIGHT_Y_AXIS               = "Position (Feet)"
DRIVE_TRAIN_RIGHT_EXPECTED_DATA_HEADER = "Expected Drive Right Position"
DRIVE_TRAIN_RIGHT_ACTUAL_DATA_HEADER   = "Actual Drive Right Position"
DRIVE_TRAIN_RIGHT = SubsystemData(DRIVE_TRAIN_RIGHT_BASE_NAME, DRIVE_TRAIN_RIGHT_TITLE, DRIVE_TRAIN_RIGHT_X_AXIS, DRIVE_TRAIN_RIGHT_Y_AXIS, 
                                  DRIVE_TRAIN_RIGHT_EXPECTED_DATA_HEADER, DRIVE_TRAIN_RIGHT_ACTUAL_DATA_HEADER )

# TURRET GRAPH PROPERTIES -> VALID_DATA_NAMES[3]
TURRET_BASE_NAME            = "Turret"
TURRET_TITLE                = "%s: Actual vs Expected" % (TURRET_BASE_NAME)
TURRET_X_AXIS               = TIME_AXIS
TURRET_Y_AXIS               = "Angle (Degrees)"
TURRET_EXPECTED_DATA_HEADER = "Expected Turret Position"
TURRET_ACTUAL_DATA_HEADER   = "Actual Turret Position"
TURRET = SubsystemData(TURRET_BASE_NAME, TURRET_TITLE, TURRET_X_AXIS, TURRET_Y_AXIS, 
                       TURRET_EXPECTED_DATA_HEADER, TURRET_ACTUAL_DATA_HEADER)

# When you run 'py OutputGrapher.py,' this is the part of the code that gets called then run
def main():
    # Generate the full path from relative + the actual name of the DATA_FILE
    fullPath = (os.path.join(THIS_FOLDER, DATA_FILE))
    print("[GRAPHER] Reading data from:", fullPath)
    # Attempt to read in a .csv file from specified fullPath
    with open(fullPath, 'r') as f:
        # Incoming: <name>, <expected value>, <actual value> EX: shooter, 1000, 996
        inputDataList = list(csv.reader(f))

    # Start parsing .csv data
    for dataEntry in inputDataList:
        # Incase there are accidental spaces in the .csv are introduced
        if dataEntry == []:
            continue
        if len(dataEntry) != 3:
            raise AssertionError ("[GRAPHER] Invalid data line provided! \nExpected: <expected value>, <actual value> EX: shooter, 1000, 996\nFound:",inputDataList)
        # Extract subsystem name
        subsystemName = dataEntry[0]
        # Map subsystem name to SubsystemData object
        subsystem = mapDataToSubsystem(subsystemName)
        # Add the current data line to the subsystem object
        subsystem.addToData(dataEntry[1:])
    # Determine the subsystems that were filled during loading of data then only fill in the sheet
    # with the subsystems that were present
    sheet = SheetProperties(usedSubsystems())
    # NOTE: SHEET WILL NOT GENERATE IF THE FILE IS ALREADY OPEN!
    # This try / except will find this error.
    try:
        # Attempt to generate the excel file with tables and graphs
        sheet.generateFile()
    except xlsxwriter.exceptions.FileCreateError:
        print ("[GRAPHER] CANNOT CREATE FILE WHILE %s IS OPEN!" % SHEET_NAME)
    print("[GRAPHER] Output file created:", SHEET_NAME)

# Maps the string representation 'name' from .csv file data
# and maps it to the related SubsystemData object
def mapDataToSubsystem ( name: str ):
    name = name.lower()
    if name in VALID_DATA_NAMES:
        if name == VALID_DATA_NAMES[0]:
            return SHOOTER
        elif name == VALID_DATA_NAMES[1]:
            return DRIVE_TRAIN_LEFT
        elif name == VALID_DATA_NAMES[2]:
            return DRIVE_TRAIN_RIGHT
        elif name == VALID_DATA_NAMES[3]:
            return TURRET
    raise AssertionError ("[GRAPHER] Unexpected subsystem name provided! Please check VALID_DATA_NAMES for accepted modes.\nFound:", name)

# Checks the subsystems that were in use, provides this to SheetProperties
# to only fill in tables and graphs where the subsystem was found to be present
def usedSubsystems ():
    usedSystems = []
    if SHOOTER.isSubSystemInUse():
        usedSystems.append(SHOOTER)
    if DRIVE_TRAIN_LEFT.isSubSystemInUse():
        usedSystems.append(DRIVE_TRAIN_LEFT)
    if DRIVE_TRAIN_RIGHT.isSubSystemInUse():
        usedSystems.append(DRIVE_TRAIN_RIGHT)
    if TURRET.isSubSystemInUse():
        usedSystems.append(TURRET)
    return usedSystems

# Needed to call main() from console
if __name__ == '__main__':
    main()

# Created by: Rob McCarthy -- 1/25/2020 v1.0
# For use within Ranger Robitics! Team 3015