package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class CompressorAuto extends CommandBase {
    public CompressorAuto() {
        addRequirements(RobotContainer.ourCompressor);
    }

    @Override
    public void initialize() {
        RobotContainer.ourCompressor.startCompressor();
    }

    @Override
    public void execute(){
        if(DriverStation.getInstance().isAutonomous() || RobotContainer.shooter.isRunning() || RobotController.getBatteryVoltage() <= 8.5) {
            RobotContainer.ourCompressor.stopCompressor();
        }
        else {
            RobotContainer.ourCompressor.startCompressor();
        }
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
