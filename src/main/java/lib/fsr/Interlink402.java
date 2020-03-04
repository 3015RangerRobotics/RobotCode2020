package lib.fsr;

import edu.wpi.first.wpilibj.SerialPort;

public class Interlink402 implements Runnable {
    private volatile SerialPort serialPort;
    private volatile int fsrVoltage = 0;
    private volatile String test = "";

    /**
     * Driver for reading data from an Interlink 402 FSR (Force Sensitive Resistor).
     *
     * <p>Requires an arduino with associated driver code connected to a roboRIO USB port.</p>
     */
    public Interlink402(){
        serialPort = new SerialPort(9600, SerialPort.Port.kUSB);
        serialPort.enableTermination();
        Thread thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    public void test(){
        System.out.println(test);
    }

    /**
     * Get the voltage readout from the sensor
     * @return The voltage in Volts
     */
    public double getVoltage(){
        return fsrVoltage / 1000.0;
    }

    /**
     * Get the estimated force applied to the sensor
     * @return The estimated force in Newtons
     */
    public double getForce(){
        if(fsrVoltage == 0){
            return 0;
        }

        double resistance = ((5000.0 - fsrVoltage) * 10000.0) / fsrVoltage;
        double conductance = 1000000 / resistance;

        // Using the FSR guide graph to approximate the force
        if(conductance <= 1000) {
            return conductance / 80.0;
        } else {
            return (conductance - 1000) / 30.0;
        }
    }

    @Override
    public void run() {
        while(true){
            // Voltage message from the arduino will always be 10 bytes.
            // May need to be changed to 11 if the NUL character matters
            if(serialPort.getBytesReceived() >= 10){
                String update = serialPort.readString();
                test = update;
//                int i = update.lastIndexOf("fsrV=");
//                if(i >= 0){
//                    // Voltage sent as an int representing millivolts with leading zeros (0000 - 5000)
//                    String voltage = update.substring(i + 5, i + 9);
//                    fsrVoltage = Integer.parseInt(voltage);
//                }
//                System.out.println(update);
            }
            test = "nope";

            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}