package frc.robot.commands.systems_checks;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Carousel;

public class TestCarousel extends CommandBase {
    Timer timer = new Timer();
    boolean motorsChecked = false;
    boolean sensorsChecked = false;


    public TestCarousel() {
        addRequirements(RobotContainer.carousel, RobotContainer.intake);
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
        RobotContainer.intake.intakeDown();
        RobotContainer.intake.intakeSet(-1);
    }

    @Override
    public void execute() {
        if (!timer.hasElapsed(0.1) && !sensorsChecked){
            RobotContainer.carousel.setState(Carousel.State.kPurgeBall1);
            RobotContainer.carousel.setBallCounter(0);
        }else if(!motorsChecked){
            Robot.carouselMotor1.setBoolean(Robot.pdp.getCurrent(4) >= 2);
            Robot.carouselMotor2.setBoolean(Robot.pdp.getCurrent(11) >= 2);
            Robot.carouselMotor3.setBoolean(Robot.pdp.getCurrent(10) >= 2);
            Robot.carouselMotor4.setBoolean(Robot.pdp.getCurrent(9) >= 2);
            Robot.carouselMotor5.setBoolean(Robot.pdp.getCurrent(8) >= 2);
            motorsChecked = true;
            RobotContainer.carousel.setState(Carousel.State.kFillTo1);
        }else if(!sensorsChecked) {
            if(RobotContainer.carousel.isBall1Present()){
                Robot.carouselBall1.setBoolean(true);
            }else if(RobotContainer.carousel.isBall2Present()){
                Robot.carouselBall2.setBoolean(true);
            }else if(RobotContainer.carousel.isBall3Present()){
                Robot.carouselBall3.setBoolean(true);
            }else if(RobotContainer.carousel.isBall4Present()){
                Robot.carouselBall4.setBoolean(true);
            }else if(RobotContainer.carousel.isBall5Present()){
                Robot.carouselBall5.setBoolean(true);
            }

            if(RobotContainer.carousel.getState() == Carousel.State.kFillTo2){
                sensorsChecked = true;
                timer.reset();
                timer.start();
            }
        }else{
            RobotContainer.intake.intakeUp();
            RobotContainer.intake.intakeStop();
            RobotContainer.carousel.setState(Carousel.State.kPurgeBall1);
        }
    }

    @Override
    public void end(boolean interrupted) {
        RobotContainer.intake.intakeUp();
        RobotContainer.intake.intakeStop();
        RobotContainer.carousel.setState(Carousel.State.kOff);
    }

    @Override
    public boolean isFinished() {
        return motorsChecked && sensorsChecked && timer.hasElapsed(2);
    }
}
