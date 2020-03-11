package frc.robot.commands.systems_checks;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotContainer;

public class TestHood extends CommandBase {
    Timer timer = new Timer();

    public TestHood() {
        addRequirements(RobotContainer.hood);
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
        SystemChecks.hoodUp.setBoolean(false);
        SystemChecks.hoodDown.setBoolean(false);
    }

    @Override
    public void execute() {
        if (!timer.hasElapsed(1)){
            RobotContainer.hood.hoodUp();
            SystemChecks.hoodUp.setBoolean(true);
        }else if(!timer.hasElapsed(2)){
            RobotContainer.hood.hoodDown();
            SystemChecks.hoodDown.setBoolean(true);
        }
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return timer.hasElapsed(2.5);
    }
}
