package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.BallHandler;

public class BallHandlerHarvest extends CommandBase {
    BallHandler ballHandler = RobotContainer.ballHandler;

    public BallHandlerHarvest() {
        addRequirements(ballHandler);
    }

    @Override
    public void initialize() {
        if (ballHandler.isPaused()) {
            ballHandler.setPaused(false);
        } else {
            ballHandler.setState(BallHandler.State.kFillTo1); //Starts the switch in ball handler, fills the "carousel" with balls
        }
    }

    @Override
    public void execute() {
        if (ballHandler.getState() == BallHandler.State.kOff){
            RobotContainer.setDriverRumbleLeft(1);
            RobotContainer.setDriverRumbleRight(1);
            RobotContainer.setCoDriverRumbleLeft(1);
            RobotContainer.setCoDriverRumbleRight(1);
        }
    }


    @Override
    public void end(boolean interrupted) {
        RobotContainer.setDriverRumbleLeft(0);
        RobotContainer.setDriverRumbleRight(0);
        RobotContainer.setCoDriverRumbleLeft(0);
        RobotContainer.setCoDriverRumbleRight(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
