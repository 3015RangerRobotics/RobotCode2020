package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class OurCompressor extends SubsystemBase {
    Compressor compressor;

    public OurCompressor() {
        compressor = new Compressor();
        this.startCompressor();
    }

    @Override
    public void periodic() {

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
}
