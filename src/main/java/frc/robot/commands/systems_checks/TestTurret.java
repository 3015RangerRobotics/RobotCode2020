package frc.robot.commands.systems_checks;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotContainer;

public class TestTurret extends CommandBase {
    Timer timer = new Timer();
    boolean motorsChecked = false;


    public TestTurret() {
        addRequirements(RobotContainer.harvester);
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
        motorsChecked = false;
        Robot.intakeMotor.setBoolean(false);
        Robot.intakeUp.setBoolean(false);
        Robot.intakeDown.setBoolean(false);
    }

    @Override
    public void execute() {
//        if (!timer.hasElapsed(0.1)){
//            RobotContainer.turret.();
//        }else if(
//        }
    }

    @Override
    public void end(boolean interrupted) {
        RobotContainer.harvester.harvesterStop();
    }

    @Override
    public boolean isFinished() {
        return motorsChecked;
    }
}
