package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class ClimberLatch extends CommandBase {
    public ClimberLatch() {
        addRequirements(RobotContainer.climber);
    }

    @Override
    public void initialize() {
        RobotContainer.climber.closeLatch();
    }

    @Override
    public void execute() {

    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
