package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
    public TalonFX shooter;

    public Shooter() {
        shooter = new TalonFX(Constants.SHOOTER_MOTOR);
        shooter.configFactoryDefault();

        shooter.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 10);
        shooter.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 10);

        shooter.setNeutralMode(NeutralMode.Coast);

        shooter.enableVoltageCompensation(true);
        shooter.configVoltageCompSaturation(12.5);

        shooter.setInverted(false);
        shooter.setSelectedSensorPosition(0);
        shooter.setSensorPhase(false);

        setRampRate(true);

        shooter.config_kP(0, Constants.SHOOTER_P);
        shooter.config_kI(0, Constants.SHOOTER_I);
        shooter.config_kD(0, Constants.SHOOTER_D);
        shooter.config_kF(0, Constants.SHOOTER_F);

        shooter.config_kP(1, Constants.SHOOTER_SHOOT_P);
        shooter.config_kI(1, Constants.SHOOTER_SHOOT_I);
        shooter.config_kD(1, Constants.SHOOTER_SHOOT_D);
        shooter.config_kF(1, Constants.SHOOTER_SHOOT_F);

    }

    @Override
    public void periodic() {
        if(getRPM() >= 7500){
            shooter.set(ControlMode.PercentOutput, 0);
        }
    }

    /**
     * @return The RPM of the shooter wheel
     */
    public double getRPM() {
        return (shooter.getSelectedSensorVelocity() * 10 * 60 / Constants.SHOOTER_PULSES_PER_ROTATION);
    }

    /**
     * Set the output of the shooter wheel
     * @param mode The control mode to use
     * @param value The value to set
     */
    public void set(ControlMode mode, double value) {
        shooter.set(mode, value);
    }

    public void selectProfileSlot(int id){
        shooter.selectProfileSlot(id, 0);
    }

    public boolean isRunning()
    {
        return shooter.getControlMode() == ControlMode.Velocity;

    }

    public void setRampRate(boolean enabled) {
        shooter.configClosedloopRamp(enabled ? 0 : 0);
    }

}

