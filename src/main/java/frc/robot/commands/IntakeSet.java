package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class IntakeSet extends CommandBase {
    private double speed;

    public IntakeSet(double speed) {
        addRequirements(RobotContainer.intake);
        this.speed = speed;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        RobotContainer.intake.intakeSet(speed);
    }

    @Override
    public void end(boolean interrupted) {
        RobotContainer.intake.intakeStop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
