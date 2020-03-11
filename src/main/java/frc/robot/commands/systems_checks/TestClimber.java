package frc.robot.commands.systems_checks;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotContainer;

public class TestClimber extends CommandBase {
    Timer timer = new Timer();
    boolean motorsChecked = false;

    public TestClimber() {
        addRequirements(RobotContainer.climber);
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
        motorsChecked = false;
        SystemChecks.climberMotor.setBoolean(false);
        SystemChecks.climberRelease.setBoolean(false);
        SystemChecks.climberLatch.setBoolean(false);
    }

    @Override
    public void execute() {
        if (!timer.hasElapsed(1.5)){
            RobotContainer.climber.releaseLatch();
            SystemChecks.climberRelease.setBoolean(true);
        }else if(!timer.hasElapsed(2)){
            RobotContainer.climber.closeLatch();
            SystemChecks.climberLatch.setBoolean(true);
        }else if(!timer.hasElapsed(2.2)){
            RobotContainer.climber.climbUp();
        }else if(!motorsChecked){
            SystemChecks.climberMotor.setBoolean(RobotContainer.pdp.getCurrent(2) >= 5);
            motorsChecked = true;
        }

    }

    @Override
    public void end(boolean interrupted) {
        RobotContainer.climber.climbStop();
    }

    @Override
    public boolean isFinished() {
        return motorsChecked;
    }
}
