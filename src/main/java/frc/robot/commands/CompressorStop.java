package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class CompressorStop extends CommandBase {
    public CompressorStop() {
        addRequirements(RobotContainer.ourCompressor);
    }

    @Override
    public void initialize() {
        RobotContainer.ourCompressor.stopCompressor();
    }

    @Override
    public void execute() {
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
