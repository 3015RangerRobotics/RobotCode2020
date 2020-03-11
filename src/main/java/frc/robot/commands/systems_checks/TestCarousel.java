package frc.robot.commands.systems_checks;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Carousel;

public class TestCarousel extends CommandBase {
    Timer timer = new Timer();
    boolean motorsChecked = false;
    boolean sensorsChecked = false;

    double max1 = 0;
    double max2 = 0;
    double max3 = 0;
    double max4 = 0;
    double max5 = 0;

    public TestCarousel() {
        addRequirements(RobotContainer.carousel, RobotContainer.intake);
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
        motorsChecked = false;
        sensorsChecked = false;
        max1 = 0;
        max2 = 0;
        max3 = 0;
        max4 = 0;
        max5 = 0;
        SystemChecks.carouselMotor1.setBoolean(false);
        SystemChecks.carouselMotor2.setBoolean(false);
        SystemChecks.carouselMotor3.setBoolean(false);
        SystemChecks.carouselMotor4.setBoolean(false);
        SystemChecks.carouselMotor5.setBoolean(false);
        SystemChecks.carouselBall1.setBoolean(false);
        SystemChecks.carouselBall2.setBoolean(false);
        SystemChecks.carouselBall3.setBoolean(false);
        SystemChecks.carouselBall4.setBoolean(false);
        SystemChecks.carouselBall5.setBoolean(false);
    }

    @Override
    public void execute() {
        if (!timer.hasElapsed(0.2) && !sensorsChecked){
            RobotContainer.carousel.setState(Carousel.State.kPurgeBall1);
            RobotContainer.carousel.setBallCounter(0);
            if(RobotContainer.pdp.getCurrent(4) > max1) max1 = RobotContainer.pdp.getCurrent(4);
            if(RobotContainer.pdp.getCurrent(11) > max1) max2 = RobotContainer.pdp.getCurrent(11);
            if(RobotContainer.pdp.getCurrent(10) > max1) max3 = RobotContainer.pdp.getCurrent(10);
            if(RobotContainer.pdp.getCurrent(9) > max1) max4 = RobotContainer.pdp.getCurrent(9);
            if(RobotContainer.pdp.getCurrent(8) > max1) max5 = RobotContainer.pdp.getCurrent(8);
        }else if(!motorsChecked){
            SystemChecks.carouselMotor1.setBoolean(max1 >= 2);
            SystemChecks.carouselMotor2.setBoolean(max2 >= 2);
            SystemChecks.carouselMotor3.setBoolean(max3 >= 2);
            SystemChecks.carouselMotor4.setBoolean(max4 >= 2);
            SystemChecks.carouselMotor5.setBoolean(max5 >= 2);
            motorsChecked = true;
            RobotContainer.carousel.setState(Carousel.State.kFillTo1);
            RobotContainer.intake.intakeDown();
            RobotContainer.intake.intakeSet(-1);
        }else if(!sensorsChecked) {
            if(RobotContainer.carousel.isBall1Present()){
                SystemChecks.carouselBall1.setBoolean(true);
            }else if(RobotContainer.carousel.isBall2Present()){
                SystemChecks.carouselBall2.setBoolean(true);
            }else if(RobotContainer.carousel.isBall3Present()){
                SystemChecks.carouselBall3.setBoolean(true);
            }else if(RobotContainer.carousel.isBall4Present()){
                SystemChecks.carouselBall4.setBoolean(true);
            }else if(RobotContainer.carousel.isBall5Present()){
                SystemChecks.carouselBall5.setBoolean(true);
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
