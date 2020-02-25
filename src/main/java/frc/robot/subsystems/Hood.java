package frc.robot.subsystems;

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

    }
    public void hoodUp() {
        hoodControl.set(DoubleSolenoid.Value.kForward);
    }

    public void hoodDown() {
        hoodControl.set(DoubleSolenoid.Value.kReverse);
    }
}
