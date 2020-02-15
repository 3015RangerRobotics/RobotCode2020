/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class OurCompressor extends SubsystemBase {
    Compressor compressor;

    /**
     * Creates a new OurCompressor.
     */
    public OurCompressor() {
        compressor = new Compressor();
        this.startCompressor();
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
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
