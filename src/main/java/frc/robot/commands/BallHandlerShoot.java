package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.BallHandler;
import frc.robot.subsystems.BallHandler.State;
import frc.robot.subsystems.BallQuality;

public class BallHandlerShoot extends CommandBase {
    private double delay;
    private boolean fender;
    private boolean doneShooting;
    private int ballsToShoot;
    BallHandler ballHandler = RobotContainer.ballHandler;
    BallQuality ballQuality = RobotContainer.ballQuality;
    Timer timer = new Timer();

    public BallHandlerShoot() {
        addRequirements(ballHandler);
        this.delay = 0;
        this.fender = false;
        this.ballsToShoot = 5;
    }

    public BallHandlerShoot(double delay, boolean fender) {
        addRequirements(ballHandler);
        this.delay = delay;
        this.fender = fender;
        this.ballsToShoot = 5;
    }

    public BallHandlerShoot(double delay, int ballsToShoot) {
        addRequirements(ballHandler);
        this.delay = delay;
        this.fender = false;
        this.ballsToShoot = ballsToShoot;
    }

    @Override
    public void initialize() {
        ballHandler.setPaused(false);
        ballHandler.setState(BallHandler.State.kShootBall1Fast);//start by shooting first ball
        if(!fender) RobotContainer.shooter.selectProfileSlot(1);
        timer.reset();
        timer.start();
        ballHandler.setBallCounter(0);
    }

    @Override
    public void execute() {
        if (ballHandler.getState() == State.kShootBall1Fast && !ballHandler.isSwitch1Pressed() && timer.hasElapsed(delay) && ballsToShoot >= 2) {
            //If the first ball is no longer on the first switch and currently the kShootball1 state is being executed, fire ball 2.
            ballHandler.setState(BallHandler.State.kShootBall2Fast);
            timer.reset();
            timer.start();
        } else if (ballHandler.getState() == State.kShootBall2Fast && !ballHandler.isSwitch2Pressed() && timer.hasElapsed(delay) && ballsToShoot >= 3) {
            //If currently the kShootball2 state is being executed and the second ball is no longer on the second switch, fire ball 3
            ballHandler.setState(BallHandler.State.kShootBall3Fast);
            timer.reset();
            timer.start();
        } else if (ballHandler.getState() == State.kShootBall3Fast && !ballHandler.isSwitch3Pressed() && timer.hasElapsed(delay) && ballsToShoot >= 4) {
            //If currently the kShootball3 state is being executed and the third ball is no longer on the third switch, fire ball 4
            ballHandler.setState(BallHandler.State.kShootBall4Fast);
            timer.reset();
            timer.start();
        } else if (ballHandler.getState() == State.kShootBall4Fast && !ballHandler.isSwitch4Pressed() && timer.hasElapsed(delay) && ballsToShoot >= 5) {
            //If the currently the kShootball4 state is being executed and the fourth ball is no longer on the fourth switch, fire ball 5
            ballHandler.setState(BallHandler.State.kShootBall5Fast);
            timer.reset();
            timer.start();
        } else {
            doneShooting = true;
            timer.reset();
            timer.start();
        }
    }

    @Override
    public void end(boolean interrupted) {
        ballHandler.setState(BallHandler.State.kOff);
        RobotContainer.shooter.selectProfileSlot(0);
        ballQuality.resetBallCounter();
    }

    @Override
    public boolean isFinished() {
        return doneShooting && timer.hasElapsed(0.5);
    }
}
