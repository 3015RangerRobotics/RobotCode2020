/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;


public class DriverRumbleSet extends CommandBase {
    double leftPower;
    double rightPower;

    /**
     * Creates a new DriverRumbleSet.
     *
     * @param leftPower  a value from 0 to 1 representing the power of the leftRumble
     * @param rightPower a value from 0 to 1 representing the power of the rightRumble
     */
    public DriverRumbleSet(double leftPower, double rightPower) {
        this.leftPower = leftPower;
        this.rightPower = rightPower;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(RobotContainer.driverRumble);

    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        RobotContainer.setDriverRumbleLeft(leftPower);
        RobotContainer.setDriverRumbleRight(rightPower);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        RobotContainer.setDriverRumbleLeft(0.0);
        RobotContainer.setDriverRumbleRight(0.0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
