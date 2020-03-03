package lib.fsr;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SerialPort;

public class Interlink402{
    private int id;

    /**
     * Driver for reading data from an Interlink 402 FSR (Force Sensitive Resistor).
     *
     * <p>Requires an arduino with associated driver code connected to an I2C port</p>
     *
     * @param id The id of the force sensor
     */
    public Interlink402(int id){
        this.id = id;
    }

    /**
     * Get the estimated force applied to the sensor
     * @return The estimated force in Newtons
     */
    public double getForce(){
        String data = ArduinoI2C.getInstance().read();
        String[] values = data.split(",");
        return Double.parseDouble(values[id]);
    }
}
