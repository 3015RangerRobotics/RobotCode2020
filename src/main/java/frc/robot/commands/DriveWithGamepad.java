package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class DriveWithGamepad extends CommandBase {
    public DriveWithGamepad() {
        addRequirements(RobotContainer.drive);
    }

    @Override
    public void initialize() {
        RobotContainer.drive.enableCoastMode();
    }

    @Override
    public void execute() {
        double driveValue = -RobotContainer.getDriverLeftStickY();
        double turnValue;

        if(Math.abs(RobotContainer.getDriverRightStickX()) > Math.abs(RobotContainer.getDriverLeftStickX())){
            turnValue = RobotContainer.getDriverRightStickX();
        }else{
            turnValue = RobotContainer.getDriverLeftStickX();
        }

        RobotContainer.drive.arcadeDrive(driveValue, turnValue, true);
    }

    @Override
    public void end(boolean interrupted) {
        RobotContainer.drive.setMotorOutputs(ControlMode.PercentOutput, 0, 0);
        RobotContainer.drive.enableBrakeMode();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
