/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Hood extends SubsystemBase {
    private DoubleSolenoid hoodControl;

    public Hood() {
        hoodControl = new DoubleSolenoid(Constants.HOOD_SOLENOID_FORWARD, Constants.HOOD_SOLENOID_REVERSE);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
    public void hoodUp() {
        hoodControl.set(DoubleSolenoid.Value.kForward);
    }

    public void hoodDown() {
        hoodControl.set(DoubleSolenoid.Value.kReverse);
    }
}
