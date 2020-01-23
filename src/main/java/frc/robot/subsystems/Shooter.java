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
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
    private TalonFX shooter;

    /**
     * Creates a new Shooter.
     */
    public Shooter() {
        shooter = new TalonFX(Constants.spinningShooter);
        shooter.configFactoryDefault();

        shooter.setNeutralMode(NeutralMode.Coast);

        shooter.enableVoltageCompensation(true);
        shooter.configVoltageCompSaturation(12.5);

        shooter.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

        shooter.setInverted(false);
        shooter.setSelectedSensorPosition(0);
        shooter.setSensorPhase(false);

        shooter.config_kP(0, Constants.shooterP);
        shooter.config_kI(0, Constants.shooterI);
        shooter.config_kD(0, Constants.shooterD);
        shooter.config_kF(0, Constants.shooterF);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    public double getRPM() {
        return (shooter.getSelectedSensorVelocity() * 10 * 60 / Constants.shooterPulsesPerRotation);
    }

    public void set(ControlMode mode, double value) { 
        shooter.set(mode, value);
        // System.out.println(value);
    }
}
