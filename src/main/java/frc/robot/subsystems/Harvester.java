/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Harvester extends SubsystemBase {
    private VictorSP harvesterMotor;
    private DoubleSolenoid tiltControl;

    /**
     * Creates a new Harvester.
     */
    public Harvester() {
        harvesterMotor = new VictorSP(Constants.HARVESTER_MOTOR);
        tiltControl = new DoubleSolenoid(Constants.HARVESTER_SOLENOID_FORWARD, Constants.HARVESTER_SOLENOID_REVERSE);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    public void harvesterUp() {
        tiltControl.set(DoubleSolenoid.Value.kReverse);
    }

    public void harvesterDown() {
        tiltControl.set(DoubleSolenoid.Value.kForward);
    }

    public void harvesterIn() {
        harvesterMotor.set(-.75);
    }

    public void harvesterStop() {
        harvesterMotor.set(0.0);
    }

    public void harvesterOut() {
        harvesterMotor.set(.5);
    }
}
