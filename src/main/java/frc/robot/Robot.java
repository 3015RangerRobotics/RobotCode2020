package frc.robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.systems_checks.*;
import frc.robot.subsystems.Limelight;

import java.util.Map;

public class Robot extends TimedRobot {
    private Command autoCommand;
    public static PowerDistributionPanel pdp = new PowerDistributionPanel(0);

    public static NetworkTableEntry driveLeftMaster;
    public static NetworkTableEntry driveLeftFollower;
    public static NetworkTableEntry driveRightMaster;
    public static NetworkTableEntry driveRightFollower;
    public static NetworkTableEntry drivePosition;
    public static NetworkTableEntry driveIMU;

    public static NetworkTableEntry intakeMotor;
    public static NetworkTableEntry intakeUp;
    public static NetworkTableEntry intakeDown;

    public static NetworkTableEntry turretMotor;
    public static NetworkTableEntry turretLeftLimit;
    public static NetworkTableEntry turretRightLimit;
    public static NetworkTableEntry turretEncoder;

    public static NetworkTableEntry hoodUp;
    public static NetworkTableEntry hoodDown;

    public static NetworkTableEntry carouselMotor1;
    public static NetworkTableEntry carouselMotor2;
    public static NetworkTableEntry carouselMotor3;
    public static NetworkTableEntry carouselMotor4;
    public static NetworkTableEntry carouselMotor5;



    private RobotContainer robotContainer;
//    Interlink402 test = new Interlink402();

    @Override
    public void robotInit() {
        robotContainer = new RobotContainer();
        SmartDashboard.putData("PDP", pdp);
//        UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
//        camera.setResolution(320, 240);
//        camera.setFPS(24);

        ShuffleboardLayout testCommands = Shuffleboard.getTab("Systems Check")
                .getLayout("Test Commands", BuiltInLayouts.kList).withSize(2, 2).withPosition(9, 1)
                .withProperties(Map.of("Label position", "HIDDEN"));
        testCommands.add(new TestDrive());
        testCommands.add(new TestIntake());
        testCommands.add(new TestTurret());
        testCommands.add(new TestHood());
        testCommands.add(new TestCarousel());

        Shuffleboard.getTab("Systems Check").add(new TestAll()).withSize(2, 1).withPosition(9, 0);

        ShuffleboardLayout driveValues = Shuffleboard.getTab("Systems Check").getLayout("Drive", BuiltInLayouts.kList)
                .withSize(2, 6).withPosition(0, 0);
        driveLeftMaster = driveValues.add("Left Master", false).getEntry();
        driveLeftFollower = driveValues.add("Left Follower", false).getEntry();
        driveRightMaster = driveValues.add("Right Master", false).getEntry();
        driveRightFollower = driveValues.add("Right Follower", false).getEntry();
        drivePosition= driveValues.add("Position", false).getEntry();
        driveIMU = driveValues.add("IMU", false).getEntry();

        ShuffleboardLayout intakeValues = Shuffleboard.getTab("Systems Check").getLayout("Intake", BuiltInLayouts.kList)
                .withSize(2, 3).withPosition(2, 0);
        intakeMotor = intakeValues.add("Motor", false).getEntry();
        intakeUp = intakeValues.add("Up", false).getEntry();
        intakeDown = intakeValues.add("Down", false).getEntry();

        ShuffleboardLayout turretValues = Shuffleboard.getTab("Systems Check").getLayout("Turret", BuiltInLayouts.kList)
                .withSize(2, 4).withPosition(4, 0);
        turretMotor = turretValues.add("Motor", false).getEntry();
        turretLeftLimit = turretValues.add("Left Limit", false).getEntry();
        turretRightLimit = turretValues.add("Right Limit", false).getEntry();
        turretEncoder = turretValues.add("Encoder", false).getEntry();

        ShuffleboardLayout hoodValues = Shuffleboard.getTab("Systems Check").getLayout("Hood", BuiltInLayouts.kList)
                .withSize(2, 2).withPosition(2, 3);
        hoodUp = hoodValues.add("Hood Up", false).getEntry();
        hoodDown = hoodValues.add("Hood Down", false).getEntry();

        ShuffleboardLayout carouselValues = Shuffleboard.getTab("Systems Check").getLayout("Carousel", BuiltInLayouts.kList)
                .withSize(2, 5).withPosition(6, 0);
        carouselMotor1= carouselValues.add("Motor 1", false).getEntry();
        carouselMotor2 = carouselValues.add("Motor 2", false).getEntry();
        carouselMotor3= carouselValues.add("Motor 3", false).getEntry();
        carouselMotor4 = carouselValues.add("Motor 4", false).getEntry();
        carouselMotor5= carouselValues.add("Motor 5", false).getEntry();
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
        String alliance = "white";
        if(DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Red){
            alliance = "red";
        }else if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue){
            alliance = "blue";
        }
        SmartDashboard.putString("alliance", alliance);
        SmartDashboard.putNumber("time", (int) DriverStation.getInstance().getMatchTime());
    }

    @Override
    public void disabledInit() {
        RobotContainer.drive.enableCoastMode();
        RobotContainer.shooter.setStateOff();
        RobotContainer.turret.setStateDefault();
        RobotContainer.limelight.setLEDMode(Limelight.LEDMode.LED_OFF);
    }

    @Override
    public void disabledPeriodic() {

    }

    @Override
    public void autonomousInit() {
        RobotContainer.drive.enableBrakeMode();

        autoCommand = robotContainer.getAutonomousCommand();

        if (autoCommand != null) {
            autoCommand.schedule();
        }
    }

    @Override
    public void autonomousPeriodic() {

    }

    @Override
    public void teleopInit() {
        RobotContainer.drive.enableBrakeMode();

        if (autoCommand != null) {
            autoCommand.cancel();
        }
    }

    @Override
    public void teleopPeriodic() {

    }

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {

    }
}
