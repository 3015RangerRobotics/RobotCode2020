/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class DriverRumbleOff extends CommandBase {
    /**
     * Creates a new DriverRumbleOff.
     */
    public DriverRumbleOff() {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(RobotContainer.driverRumble);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        RobotContainer.setDriverRumbleLeft(0.0);
        RobotContainer.setDriverRumbleRight(0.0);
    }


    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return true;
    }
}
