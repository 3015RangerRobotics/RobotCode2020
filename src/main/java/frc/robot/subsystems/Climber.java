/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
/**
 * Add your docs here.
 */
public class Climber extends SubsystemBase {
  private TalonSRX climberMotor;
  private DoubleSolenoid latchRelease;
  private double encoder;

  public Climber() {
    climberMotor = new TalonSRX(Constants.CLIMBER_MOTOR);
    climberMotor.setInverted(true);
    latchRelease = new DoubleSolenoid(Constants.CLIMBER_LATCH_RELEASE1, Constants.CLIMBER_LATCH_RELEASE2);
    closeLatch();
    climberMotor.configVoltageCompSaturation(12.8);
    climberMotor.enableVoltageCompensation(true);
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