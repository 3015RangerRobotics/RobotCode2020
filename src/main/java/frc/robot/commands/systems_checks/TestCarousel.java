package frc.robot.commands.systems_checks;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.BallHandler;

public class TestCarousel extends CommandBase {
    Timer timer = new Timer();
    boolean motorsChecked = false;
    boolean sensorsChecked = false;



    public TestCarousel() {
        addRequirements(RobotContainer.ballHandler, RobotContainer.harvester);
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
        motorsChecked = false;
        sensorsChecked = false;
        Robot.carouselMotor1.setBoolean(false);
        Robot.carouselMotor2.setBoolean(false);
        Robot.carouselMotor3.setBoolean(false);
        Robot.carouselMotor4.setBoolean(false);
        Robot.carouselMotor5.setBoolean(false);
        Robot.carouselBall1.setBoolean(false);
        Robot.carouselBall2.setBoolean(false);
        Robot.carouselBall3.setBoolean(false);
        Robot.carouselBall4.setBoolean(false);
        Robot.carouselBall5.setBoolean(false);
        RobotContainer.harvester.harvesterDown();
        RobotContainer.harvester.harvesterSet(-1);
    }

    @Override
    public void execute() {
        if (!timer.hasElapsed(0.1) && !sensorsChecked){
            RobotContainer.ballHandler.setState(BallHandler.State.kPurgeBall1);
        }else if(!motorsChecked){
            Robot.carouselMotor1.setBoolean(Robot.pdp.getCurrent(14) >= 5);
            Robot.carouselMotor2.setBoolean(Robot.pdp.getCurrent(15) >= 5);
            Robot.carouselMotor3.setBoolean(Robot.pdp.getCurrent(0) >= 5);
            Robot.carouselMotor4.setBoolean(Robot.pdp.getCurrent(1) >= 5);
            Robot.carouselMotor5.setBoolean(Robot.pdp.getCurrent(1) >= 5);
            motorsChecked = true;
        }else if(!sensorsChecked) {
            RobotContainer.ballHandler.setState(BallHandler.State.kFillTo1);

            if(RobotContainer.ballHandler.isSwitch1Pressed()){
                Robot.carouselBall1.setBoolean(true);
            }else if(RobotContainer.ballHandler.isSwitch2Pressed()){
                Robot.carouselBall2.setBoolean(true);
            }else if(RobotContainer.ballHandler.isSwitch3Pressed()){
                Robot.carouselBall3.setBoolean(true);
            }else if(RobotContainer.ballHandler.isSwitch4Pressed()){
                Robot.carouselBall4.setBoolean(true);
            }else if(RobotContainer.ballHandler.isSwitch5Pressed()){
                Robot.carouselBall5.setBoolean(true);
            }

            if(RobotContainer.ballHandler.getState() == BallHandler.State.kFillTo2){
                sensorsChecked = true;
                timer.reset();
                timer.start();
            }
        }else if(!timer.hasElapsed(2)){
            RobotContainer.harvester.harvesterUp();
            RobotContainer.harvester.harvesterStop();
            RobotContainer.ballHandler.setState(BallHandler.State.kPurgeBall1);
        }
    }

    @Override
    public void end(boolean interrupted) {
        RobotContainer.harvester.harvesterUp();
        RobotContainer.harvester.harvesterStop();
        RobotContainer.ballHandler.setState(BallHandler.State.kOff);
    }

    @Override
    public boolean isFinished() {
        return motorsChecked && sensorsChecked && timer.hasElapsed(2);
    }
}
