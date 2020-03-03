package lib.fsr;

import edu.wpi.first.wpilibj.I2C;

public class ArduinoI2C {
    private static ArduinoI2C instance = null;
    private static final int MAX_BYTES = 32;
    private I2C i2c;

    private ArduinoI2C(){
        i2c = new I2C(I2C.Port.kOnboard, 9);
    }

    public static ArduinoI2C getInstance() {
        if (instance == null){
            instance = new ArduinoI2C();
        }
        return instance;
    }

    public String read(){
        byte[] buffer = new byte[MAX_BYTES];
        i2c.read(9, MAX_BYTES, buffer);
        String output = new String(buffer);
        int pt = output.indexOf((char)255);
        return (String) output.subSequence(0, Math.max(pt, 0));
    }
}
