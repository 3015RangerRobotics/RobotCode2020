/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Turret extends SubsystemBase {
    /**
     * Creates a new Turret.
     */

    private TalonSRX turretMotor;

    public Turret() {
        this.turretMotor = new TalonSRX(Constants.TURRET_MOTOR);

        turretMotor.configFactoryDefault();

        turretMotor.setNeutralMode(NeutralMode.Brake);

        turretMotor.enableVoltageCompensation(true);
        turretMotor.configVoltageCompSaturation(12.5);

        turretMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

        turretMotor.configForwardSoftLimitEnable(true);
        turretMotor.configForwardSoftLimitThreshold((int) Math.round(Constants.TURRET_SOFT_LIMIT_FORWARD / Constants.TURRET_DEGREES_PER_PULSE));

        turretMotor.configReverseSoftLimitEnable(true);
        turretMotor.configReverseSoftLimitThreshold((int) Math.round(Constants.TURRET_SOFT_LIMIT_REVERSE / Constants.TURRET_DEGREES_PER_PULSE));

        turretMotor.setInverted(true);
        turretMotor.setSelectedSensorPosition(0);
        turretMotor.setSensorPhase(true);

        turretMotor.configPeakOutputForward(Constants.TURRET_MAX_SPEED);
        turretMotor.configPeakOutputReverse(-Constants.TURRET_MAX_SPEED);
        turretMotor.configNominalOutputForward(Constants.TURRET_MIN_SPEED);
        turretMotor.configNominalOutputReverse(-Constants.TURRET_MIN_SPEED);

        turretMotor.configAllowableClosedloopError(0, (int) Constants.TURRET_DEGREE_MARGIN);

        turretMotor.config_kP(0, Constants.TURRET_P);
        turretMotor.config_kI(0, Constants.TURRET_I);
        turretMotor.config_kD(0, Constants.TURRET_D);
        turretMotor.config_kF(0, Constants.TURRET_F);

    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        // System.out.println("Turret Angle: "  + getMotorPosition() + " Motor Speed: " + turretMotor.getMotorOutputPercent());
    }

    public double getMotorPosition() {
        return (turretMotor.getSelectedSensorPosition() * Constants.TURRET_DEGREES_PER_PULSE);
    }

    public void set(ControlMode mode, double value) { 
        turretMotor.set(mode, value);
        // System.out.println(value);
    }

    public void resetEncoder() {
        turretMotor.setSelectedSensorPosition(0);
    }
}
