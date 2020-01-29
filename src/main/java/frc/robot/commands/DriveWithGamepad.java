/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class DriveWithGamepad extends CommandBase {
    /**
     * Creates a new DriveWithGamepad.
     */
    public DriveWithGamepad() {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(RobotContainer.drive);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {

    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double driveValue = 0.0;
        double turnValue = 0.0;
        if (Math.abs(RobotContainer.getDriverLeftStickY()) > (Math.abs(RobotContainer.getDriverRightStickY()))) {
            driveValue = RobotContainer.getDriverLeftStickY();
        } else {
            driveValue = -RobotContainer.getDriverRightStickY();
        }

        if (Math.abs(RobotContainer.getDriverLeftStickX()) > (Math.abs(RobotContainer.getDriverRightStickX()))) {
            turnValue = RobotContainer.getDriverLeftStickX() / 1.25;

        } else {
            turnValue = RobotContainer.getDriverRightStickX() / 1.25;
        }

        RobotContainer.drive.arcadeDrive(driveValue, turnValue, true);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        RobotContainer.drive.setMotorOutputs(ControlMode.PercentOutput, 0, 0);

    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
