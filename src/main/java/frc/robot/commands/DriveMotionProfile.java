/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import lib.motion_profiles.DriveProfile;

public class DriveMotionProfile extends CommandBase {
    /**
     * Creates a new DriveMotionProfile.
     */
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

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        RobotContainer.drive.resetEncoders();
        RobotContainer.drive.resetIMU();
        RobotContainer.drive.startMotionProfile(profile.getProfileAsCTREBuffer(Constants.DRIVE_KV, Constants.DRIVE_KA));
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        System.out.println("drive_train_right," + RobotContainer.drive.getExpectedPosition(0) + "," + RobotContainer.drive.getPosition());
        System.out.println("drive_train_left," + -RobotContainer.drive.getExpectedPosition(1) / Constants.DRIVE_PIGEON_UNITS_PER_DEGREE + "," + RobotContainer.drive.getAngle());
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        RobotContainer.drive.setMotorOutputs(ControlMode.PercentOutput, 0, 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return RobotContainer.drive.isMotionProfileFinished();
    }
}
