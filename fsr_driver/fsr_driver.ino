#include <stdlib.h>
#include <Wire.h>

int fsrPin = 0;
int fsrReading;
int fsrVoltage;
unsigned long fsrResistance;
unsigned long fsrConductance;
long fsrForce;

void setup() {
  Serial.begin(9600);
    Wire.begin(9);
    Wire.onRequest(sendData);
}

void loop() {
    fsrReading = analogRead(fsrPin);

    // Analog input ranges from 0 to 1023, (0V to 5V)
    fsrVoltage = map(fsrReading, 0, 1023, 0, 5000);
    
    if(fsrVoltage == 0){
        fsrForce = 0;
    }else{
        fsrResistance = ((5000 - fsrVoltage) * 10000) / fsrVoltage;
        fsrConductance = 1000000 / fsrResistance;

        if(fsrConductance <= 1000){
            fsrForce = fsrConductance / 80;
        }else{
            fsrForce = (fsrConductance - 1000) / 30;
        }
    }
    Serial.println(fsrVoltage);
    delay(10);
}

void sendData(){
    byte* data;
    byte* fsr1 = (byte*) &fsrForce;

    data[0] = fsr1[0];
    data[1] = fsr1[1];
    data[2] = fsr1[2];
    data[3] = fsr1[3];

    Wire.write(data, 4);
}
