package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Climber;

public class ClimberClimbUp extends CommandBase {
    public ClimberClimbUp() {
        addRequirements(RobotContainer.climber);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        RobotContainer.climber.climbUp();
    }

    @Override
    public void end(boolean interrupted) {
        RobotContainer.climber.climbStop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
