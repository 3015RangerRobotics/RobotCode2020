package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class CompressorStart extends CommandBase {
    public CompressorStart() {
        addRequirements(RobotContainer.ourCompressor);
    }

    @Override
    public void initialize() {
        RobotContainer.ourCompressor.startCompressor();
    }

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
