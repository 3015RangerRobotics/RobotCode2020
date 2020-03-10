package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.BallHandler;
import frc.robot.subsystems.BallHandler.State;

public class BallHandlerSettle extends CommandBase {
    BallHandler ballHandler = RobotContainer.ballHandler;
    private boolean quality;

    public BallHandlerSettle() {
        addRequirements(ballHandler);
    }

    public BallHandlerSettle(boolean quality) {
        this.quality = quality;
    }

    @Override
    public void initialize() {
        ballHandler.setState(State.kSettleTo1);
    }

    @Override
    public void execute() {

    }

    @Override
    public void end(boolean interrupted) {
        ballHandler.setState(State.kOff);
    }

    @Override
    public boolean isFinished() {
        return ballHandler.getState() == State.kOff;
    }
}
