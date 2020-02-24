package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.BallHandler;

public class BallHandlerDefault extends CommandBase {
    BallHandler ballHandler = RobotContainer.ballHandler;
    Timer timer = new Timer();

    public BallHandlerDefault() {
        addRequirements(ballHandler);
    }

    @Override
    public void initialize() {
        timer.start();
        timer.reset();
    }

    @Override
    public void execute() {
        BallHandler.State currentState = ballHandler.getState();
        if (currentState == BallHandler.State.kFillTo1 || currentState == BallHandler.State.kFillTo2 ||
                currentState == BallHandler.State.kFillTo3 || currentState == BallHandler.State.kFillTo4 ||
                currentState == BallHandler.State.kFillTo5) {
            if (timer.hasPeriodPassed(1.5)) {
                ballHandler.setPaused(true);
            }
        }
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
