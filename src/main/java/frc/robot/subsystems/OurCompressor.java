package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class OurCompressor extends SubsystemBase {
    Compressor compressor;
    private AnalogPotentiometer pressureSensor;

    public OurCompressor() {
        compressor = new Compressor();
        pressureSensor = new AnalogPotentiometer(Constants.PRESSURE_SENSOR, 250, -25);
        this.startCompressor();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Pressure", Math.round(getPressure()));
    }

    /**
     * Start the compressor
     */
    public void startCompressor() {
        compressor.start();
    }

    /**
     * Stop the compressor
     */
    public void stopCompressor() {
        compressor.stop();
    }

    /**
     * Get the reading from the pressure sensor
     * @return The pressure in our air tanks in PSI
     */
    public double getPressure() {
        return pressureSensor.get();
    }
}
