package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Harvester;
import frc.robot.subsystems.Hood;

public class HoodDown extends CommandBase {
    Hood hood = RobotContainer.hood;

    public HoodDown() {
        addRequirements(hood);
    }

    @Override
    public void initialize() {
        hood.hoodDown();
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
