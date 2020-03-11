package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class IntakeUp extends CommandBase {
    public IntakeUp() {
        addRequirements(RobotContainer.intake);
    }

    @Override
    public void initialize() {
        RobotContainer.intake.intakeUp();
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
