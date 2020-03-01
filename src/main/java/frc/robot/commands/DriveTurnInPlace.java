package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;

public class DriveTurnInPlace extends CommandBase {
    double angle;
    int counter = 0;
    public DriveTurnInPlace(double angle) {
        addRequirements(RobotContainer.drive);
        this.angle = angle;
        }

    @Override
    public void initialize() {
        counter = 0;
        RobotContainer.drive.resetIMU();
        RobotContainer.drive.turnInPlaceSetup();
    }

    @Override
    public void execute() {
        RobotContainer.drive.turnInPlace(angle * Constants.DRIVE_PIGEON_UNITS_PER_DEGREE);
        System.out.println(angle * Constants.DRIVE_PIGEON_UNITS_PER_DEGREE + "," + RobotContainer.drive.getLeftSensorPosition());
        if(RobotContainer.drive.isLeftOnTarget()) {
            counter++;
        } else {
            counter = 0;
        }
//        RobotContainer.drive.setMotorOutputs(ControlMode.MotionMagic, angle * Constants.DRIVE_PIGEON_UNITS_PER_DEGREE,
//                angle * Constants.DRIVE_PIGEON_UNITS_PER_DEGREE);
    }

    @Override
    public void end(boolean interrupted) {
        RobotContainer.drive.turnInPlaceCleanup();
        RobotContainer.drive.setMotorOutputs(ControlMode.PercentOutput, 0, 0);
    }

    @Override
    public boolean isFinished() {
        return counter >= 5;
    }
}
