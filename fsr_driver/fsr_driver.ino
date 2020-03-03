int fsrPin = 0;
int fsrReading;
int fsrVoltage;

void setup() {
    Serial.begin(9600);
}

void loop() {
    fsrReading = analogRead(fsrPin);

    // Analog input ranges from 0 to 1023, (0V to 5V)
    fsrVoltage = map(fsrReading, 0, 1023, 0, 5000);
    
    printVoltage();
    
    delay(10);
}

void printVoltage(){
    Serial.println(fsrVoltage);
}
