package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.BallHandler;

public class BallHandlerPurge extends CommandBase {
    BallHandler ballHandler = RobotContainer.ballHandler;

    public BallHandlerPurge() {
        addRequirements(ballHandler);
    }

    @Override
    public void initialize() {
        ballHandler.setState(BallHandler.State.kPurge);
        ballHandler.setPaused(false);
    }

    @Override
    public void execute() {
    }

    @Override
    public void end(boolean interrupted) {
        ballHandler.setState(BallHandler.State.kOff);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
