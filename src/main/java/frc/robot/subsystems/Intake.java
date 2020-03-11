package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
    private VictorSP intakeMotor;
    private DoubleSolenoid tiltControl;

    public Intake() {
        tiltControl = new DoubleSolenoid(Constants.INTAKE_SOLENOID_FORWARD, Constants.INTAKE_SOLENOID_REVERSE);
        intakeMotor = new VictorSP(Constants.INTAKE_MOTOR);
        intakeMotor.setInverted(true);
    }

    @Override
    public void periodic() {

    }

    /**
     * Pull the intake up
     */
    public void intakeUp() {
        tiltControl.set(DoubleSolenoid.Value.kReverse);
    }

    /**
     * Push the intake down
     */
    public void intakeDown() {
        tiltControl.set(DoubleSolenoid.Value.kForward);
    }

    /**
     * Set the intake motor
     */
    public void intakeSet(double speed) {
        intakeMotor.set(speed);
    }

    /**
     * Set the intake motor to stop
     */
    public void intakeStop() {
        intakeMotor.set(0.0);
    }
}
