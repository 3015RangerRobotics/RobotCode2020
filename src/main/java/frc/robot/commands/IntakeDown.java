package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class IntakeDown extends CommandBase {

    public IntakeDown() {
        addRequirements(RobotContainer.intake);
    }

    @Override
    public void initialize() {
        RobotContainer.intake.intakeDown();
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
