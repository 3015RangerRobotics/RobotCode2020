package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import lib.motion_profiles.DriveProfile;

public class DriveMotionProfile extends CommandBase {
    private DriveProfile profile;

    /**
     * Create a motion profile command to drive a pre-generated path
     * @param pathName The name of the path to follow
     */
    public DriveMotionProfile(String pathName) {
        addRequirements(RobotContainer.drive);
        this.profile = new DriveProfile(pathName);
    }

    /**
     * Create a motion profile command to drive a straight line using motion magic
     * @param distance The distance to drive
     * @param maxV The max velocity
     * @param maxA The max acceleration
     */
    public DriveMotionProfile(double distance, double maxV, double maxA) {
        addRequirements(RobotContainer.drive);
        this.profile = new DriveProfile(distance, maxV, maxA);
    }

    @Override
    public void initialize() {
        RobotContainer.drive.resetEncoders();
        RobotContainer.drive.resetIMU();
        RobotContainer.drive.startMotionProfile(profile.getProfileAsCTREBuffer(Constants.DRIVE_KV, Constants.DRIVE_KA));
    }

    @Override
    public void execute() {
//        System.out.println("drive_train_right," + RobotContainer.drive.getExpectedPosition(0) + "," + RobotContainer.drive.getPosition());
//        System.out.println("drive_train_left," + -RobotContainer.drive.getExpectedPosition(1) / Constants.DRIVE_PIGEON_UNITS_PER_DEGREE + "," + RobotContainer.drive.getAngle());
    }

    @Override
    public void end(boolean interrupted) {
        RobotContainer.drive.setMotorOutputs(ControlMode.PercentOutput, 0, 0);
    }

    @Override
    public boolean isFinished() {
        return RobotContainer.drive.isMotionProfileFinished();
    }
}
