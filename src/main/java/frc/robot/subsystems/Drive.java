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
import frc.robot.DriveHelper;
import frc.robot.DriveSignal;
import frc.robot.commands.DriveWithGamepad;

public class Drive extends SubsystemBase {
  /**
   * Creates a new Drive.
   */
  private TalonFX rightMaster;
  private TalonFX rightFollower;

  private TalonFX leftMaster;
  private TalonFX leftFollower;

  public Drive() {
    this.rightMaster = new TalonFX(Constants.rightDriveMaster);
    this.rightFollower = new TalonFX(Constants.rightDriveFollower);
    this.leftMaster = new TalonFX(Constants.leftDriveMaster);
    this.leftFollower = new TalonFX(Constants.leftDriveFollower);

    rightMaster.configFactoryDefault();
    leftMaster.configFactoryDefault();

    rightFollower.follow(rightMaster);
    leftFollower.follow(leftMaster);

    rightMaster.setInverted(true);
    leftMaster.setInverted(false);

    rightMaster.setNeutralMode(NeutralMode.Brake);
    leftMaster.setNeutralMode(NeutralMode.Brake);

    rightMaster.enableVoltageCompensation(true);
    rightMaster.configVoltageCompSaturation(12.5);
    leftMaster.enableVoltageCompensation(true);
    leftMaster.configVoltageCompSaturation(12.5);

    rightMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    leftMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

    rightMaster.setSensorPhase(true);
    leftMaster.setSensorPhase(false);

    rightMaster.config_kP(0, Constants.driveP);
    rightMaster.config_kI(0, Constants.driveI);
    rightMaster.config_kD(0, Constants.driveD);
    rightMaster.config_kF(0, Constants.driveF);

    leftMaster.config_kP(0, Constants.driveP);
    leftMaster.config_kI(0, Constants.driveI);
    leftMaster.config_kD(0, Constants.driveD);
    leftMaster.config_kF(0, Constants.driveF);

    
    this.setDefaultCommand(new DriveWithGamepad());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setMotorOutputs(ControlMode mode, double leftMotor, double rightMotor){
    this.rightMaster.set(mode, rightMotor);
    this.leftMaster.set(mode, leftMotor);
  }

  public void arcadeDrive(double driveValue, double turnValue, boolean squaredInputs)
  {
    DriveSignal ds = DriveHelper.arcadeDrive(driveValue, turnValue, squaredInputs);
    setMotorOutputs(ControlMode.PercentOutput, ds.leftSignal, ds.rightSignal);
  }

}
