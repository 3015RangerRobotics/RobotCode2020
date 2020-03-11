package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Hood;

public class HoodUp extends CommandBase {
    public HoodUp() {
        addRequirements( RobotContainer.hood);
    }

    @Override
    public void initialize() {
        RobotContainer.hood.hoodUp();
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
