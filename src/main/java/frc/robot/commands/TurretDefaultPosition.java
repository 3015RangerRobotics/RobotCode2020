/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class TurretDefaultPosition extends CommandBase {
    /**
     * Creates a new TurretDefaultPosition.
     */
    private PIDController pidController;

    public TurretDefaultPosition() {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(RobotContainer.turret);
        pidController = new PIDController(Constants.turretP, Constants.turretI, Constants.turretD);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      double percent = pidController.calculate(RobotContainer.turret.getMotorPosition(), 0);
      
      if(Math.abs(percent) >= Constants.turretMaxSpeedZero) {
          if(percent >= 0) {
            RobotContainer.turret.set(ControlMode.PercentOutput, Constants.turretMaxSpeedZero);
          }
          else {
              RobotContainer.turret.set(ControlMode.PercentOutput, -Constants.turretMaxSpeedZero);
          }
      }
      else {
          RobotContainer.turret.set(ControlMode.PercentOutput, percent);
      }
  }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        RobotContainer.turret.set(ControlMode.PercentOutput, 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
