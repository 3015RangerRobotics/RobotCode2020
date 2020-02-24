package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Hood;

public class HoodUp extends CommandBase {
    Hood hood = RobotContainer.hood;

    public HoodUp() {
        addRequirements(hood);
    }

    @Override
    public void initialize() {
        hood.hoodUp();
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
