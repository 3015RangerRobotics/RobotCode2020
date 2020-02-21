/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.BallHandler;

public class BallHandlerHarvest extends CommandBase {
    BallHandler ballHandler = RobotContainer.ballHandler;

    public BallHandlerHarvest() {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(ballHandler);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        if (ballHandler.isPaused()) {
            ballHandler.setPaused(false);
        } else {
            ballHandler.setState(BallHandler.State.kFillTo1); //Starts the switch in ball handler, fills the "carousel" with balls
        }
    }


    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (ballHandler.getState() == BallHandler.State.kOff){
            RobotContainer.setDriverRumbleLeft(1);
            RobotContainer.setDriverRumbleRight(1);
        }
    }


    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        RobotContainer.setDriverRumbleLeft(0);
        RobotContainer.setDriverRumbleRight(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
//        return ballHandler.getState() == BallHandler.State.kOff;
    }
}
