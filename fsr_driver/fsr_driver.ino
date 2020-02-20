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
    Serial.print("fsrV=");

    if(fsrVoltage < 1000){
        Serial.print("0");
    }
    
    if(fsrVoltage < 100){
        Serial.print("0");
    }
    
    if(fsrVoltage < 10){
        Serial.print("0");
    }

    Serial.println(fsrVoltage);
}
