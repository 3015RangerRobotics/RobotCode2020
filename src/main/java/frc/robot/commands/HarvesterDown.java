package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Harvester;

public class HarvesterDown extends CommandBase {
    Harvester harvester = RobotContainer.harvester;

    public HarvesterDown() {
        addRequirements(harvester);
    }

    @Override
    public void initialize() {
        harvester.harvesterDown();

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
