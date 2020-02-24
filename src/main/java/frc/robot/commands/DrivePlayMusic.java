package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class DrivePlayMusic extends CommandBase {
    public DrivePlayMusic() {
        addRequirements(RobotContainer.drive);
    }

    @Override
    public void initialize() {
        RobotContainer.drive.playMusic();
    }

    @Override
    public void execute() {
    }

    @Override
    public void end(boolean interrupted) {
        RobotContainer.drive.stopMusic();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
