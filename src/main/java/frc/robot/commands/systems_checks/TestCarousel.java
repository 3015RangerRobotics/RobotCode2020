package frc.robot.commands.systems_checks;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.BallHandler;

public class TestCarousel extends CommandBase {
    Timer timer = new Timer();
    boolean motorsChecked= false;


    public TestCarousel() {
        addRequirements(RobotContainer.ballHandler);
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
        motorsChecked = false;
    }

    @Override
    public void execute() {
        if (!timer.hasElapsed(0.1)){
            RobotContainer.ballHandler.setState(BallHandler.State.kPurgeBall1);
        }else if(!motorsChecked){
            Robot.carouselMotor1.setBoolean(Robot.pdp.getCurrent(14) >= 5);
            Robot.carouselMotor2.setBoolean(Robot.pdp.getCurrent(15) >= 5);
            Robot.carouselMotor3.setBoolean(Robot.pdp.getCurrent(0) >= 5);
            Robot.carouselMotor4.setBoolean(Robot.pdp.getCurrent(1) >= 5);
            Robot.carouselMotor5.setBoolean(Robot.pdp.getCurrent(1) >= 5);
            motorsChecked = true;
        }
    }

    @Override
    public void end(boolean interrupted) {
        RobotContainer.ballHandler.setState(BallHandler.State.kOff);
    }

    @Override
    public boolean isFinished() {
        return motorsChecked;
    }
}
