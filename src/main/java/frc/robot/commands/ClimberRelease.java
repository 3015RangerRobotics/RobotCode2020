package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Climber;

public class ClimberRelease extends CommandBase {
    public ClimberRelease() {
        addRequirements(RobotContainer.climber);
    }

    @Override
    public void initialize() {
        RobotContainer.climber.releaseLatch();
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
