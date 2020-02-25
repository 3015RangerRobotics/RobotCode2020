package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {
    private TalonSRX climberMotor;
    private DoubleSolenoid latchRelease;

    public Climber() {
        climberMotor = new TalonSRX(Constants.CLIMBER_MOTOR);
        climberMotor.setInverted(true);
        latchRelease = new DoubleSolenoid(Constants.CLIMBER_LATCH_RELEASE1, Constants.CLIMBER_LATCH_RELEASE2);
        climberMotor.configVoltageCompSaturation(12.5);
        climberMotor.enableVoltageCompensation(true);

        closeLatch();
    }

    public void releaseLatch() {
        latchRelease.set(DoubleSolenoid.Value.kForward);
    }

    public void closeLatch() {
        latchRelease.set(DoubleSolenoid.Value.kReverse);
    }

    public void climbUp() {
        climberMotor.set(ControlMode.PercentOutput, Constants.CLIMB_UP_SPEED);
    }

    public void climbStop() {
        climberMotor.set(ControlMode.PercentOutput, 0);
    }
}
