package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.BallHandler;
import frc.robot.subsystems.BallHandler.State;

public class BallHandlerShoot extends CommandBase {
    private double delay;
    BallHandler ballHandler = RobotContainer.ballHandler;
    Timer timer = new Timer();

    public BallHandlerShoot() {
        addRequirements(ballHandler);
        this.delay = 0;
    }

    public BallHandlerShoot(double delay) {
        addRequirements(ballHandler);
        this.delay = delay;
    }

    @Override
    public void initialize() {
        ballHandler.setPaused(false);
        ballHandler.setState(BallHandler.State.kShootBall1);//start by shooting first ball
        RobotContainer.shooter.selectProfileSlot(1);
        timer.reset();
        timer.start();
    }

    @Override
    public void execute() {
        if (ballHandler.getState() == State.kShootBall1 && !ballHandler.isSwitch1Pressed() && timer.hasPeriodPassed(delay)) {
            //If the first ball is no longer on the first switch and currently the kShootball1 state is being executed, fire ball 2.
            ballHandler.setState(BallHandler.State.kShootBall2);
            timer.reset();
            timer.start();
        } else if (ballHandler.getState() == State.kShootBall2 && !ballHandler.isSwitch2Pressed() && timer.hasPeriodPassed(delay)) {
            //If currently the kShootball2 state is being executed and the second ball is no longer on the second switch, fire ball 3
            ballHandler.setState(BallHandler.State.kShootBall3);
            timer.reset();
            timer.start();
        } else if (ballHandler.getState() == State.kShootBall3 && !ballHandler.isSwitch3Pressed() && timer.hasPeriodPassed(delay)) {
            //If currently the kShootball3 state is being executed and the third ball is no longer on the third switch, fire ball 4
            ballHandler.setState(BallHandler.State.kShootBall4);
            timer.reset();
            timer.start();
        } else if (ballHandler.getState() == State.kShootBall4 && !ballHandler.isSwitch4Pressed() && timer.hasPeriodPassed(delay)) {
            //If the currently the kShootball4 state is being executed and the fourth ball is no longer on the fourth switch, fire ball 5
            ballHandler.setState(BallHandler.State.kShootBall5);
        }
    }

    @Override
    public void end(boolean interrupted) {
        ballHandler.setState(BallHandler.State.kOff);
        RobotContainer.shooter.selectProfileSlot(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
