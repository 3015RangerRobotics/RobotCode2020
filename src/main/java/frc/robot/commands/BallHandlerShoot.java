package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.BallHandler;
import frc.robot.subsystems.BallHandler.State;

public class BallHandlerShoot extends CommandBase {
    BallHandler ballHandler = RobotContainer.ballHandler;

    public BallHandlerShoot() {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(ballHandler);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        ballHandler.setPaused(false);
        ballHandler.setState(BallHandler.State.kShootBall1);//start by shooting first ball
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (ballHandler.getState() == State.kShootBall1 && !ballHandler.isSwitch1Pressed()) //If the first ball is no longer on the first switch and currently the kShootball1 state is being executed, fire ball 2.
        {
            ballHandler.setState(BallHandler.State.kShootBall2);
        } else if (ballHandler.getState() == State.kShootBall2 && !ballHandler.isSwitch2Pressed()) {
            //If currently the kShootball2 state is being executed and the second ball is no longer on the second switch, fire ball 3
            ballHandler.setState(BallHandler.State.kShootBall3);
        } else if (ballHandler.getState() == State.kShootBall3 && !ballHandler.isSwitch3Pressed()) {
            //If currently the kShootball3 state is being executed and the third ball is no longer on the third switch, fire ball 4
            ballHandler.setState(BallHandler.State.kShootBall4);
        } else if (ballHandler.getState() == State.kShootBall4 && !ballHandler.isSwitch4Pressed()) {
            //If the currently the kShootball4 state is being executed and the fourth ball is no longer on the fourth switch, fire ball 5
            ballHandler.setState(BallHandler.State.kShootBall5);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {

        ballHandler.setState(BallHandler.State.kOff);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
