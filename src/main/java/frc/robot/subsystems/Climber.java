/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
/**
 * Add your docs here.
 */
public class Climber extends SubsystemBase {
  private TalonSRX climberMotor;
  private Solenoid latchRelease;
  public Climber() {
    climberMotor = new TalonSRX(Constants.CLIMBER_MOTOR);
    latchRelease = new Solenoid(Constants.CLIMBER_LATCH_RELEASE);
    climberMotor.configVoltageCompSaturation(12.8);
    climberMotor.enableVoltageCompensation(true);
  }
  public void releaseLatch() {
    latchRelease.set(true);
  }
  public void closeLatch() {
    latchRelease.set(false);
  }
  public void climbUp() {
    climberMotor.set(ControlMode.PercentOutput, Constants.CLIMB_UP_SPEED);
  }
  public void climbDown() {
    climberMotor.set(ControlMode.PercentOutput, Constants.CLIMB_DOWN_SPEED);
  }
  public void climbStop() {
    climberMotor.set(ControlMode.PercentOutput, Constants.CLIMB_STOP_SPEED);
  }
}
