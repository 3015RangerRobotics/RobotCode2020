package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class HarvesterSet extends CommandBase {
    private double speed;

    public HarvesterSet(double speed) {
        addRequirements(RobotContainer.harvester);
        this.speed = speed;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        RobotContainer.harvester.harvesterSet(speed);
    }

    @Override
    public void end(boolean interrupted) {
        RobotContainer.harvester.harvesterStop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
