package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class DriveTurnInPlace extends CommandBase {
    double angle;
    public DriveTurnInPlace(double angle) {
        addRequirements(RobotContainer.drive);
        this.angle = angle;
    }

    @Override
    public void initialize() {
        RobotContainer.drive.turnInPlaceSetup();
    }

    @Override
    public void execute() {
        RobotContainer.drive.setLeftMotor(ControlMode.MotionMagic, -angle * Constants.DRIVE_PIGEON_UNITS_PER_DEGREE);
    }

    @Override
    public void end(boolean interrupted) {
        RobotContainer.drive.turnInPlaceCleanup();
        RobotContainer.drive.setMotorOutputs(ControlMode.PercentOutput, 0, 0);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(angle) - RobotContainer.drive.getAngle() <= 0.5;
    }
}
