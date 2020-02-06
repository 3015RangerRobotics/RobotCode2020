/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motion.BufferedTrajectoryPointStream;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class DriveMotionProfile extends CommandBase {
    /**
     * Creates a new DriveMotionProfile.
     */
//    private BufferedTrajectoryPointStream left;
//    private BufferedTrajectoryPointStream right;
//    private double[][] left2;
//    private double[][] right2;
    private String pathName;
    private double distance = 0;

    /**
     * Create a motion profile command to drive a pre-generated path
     * @param pathName The name of the path to follow
     */
    public DriveMotionProfile(String pathName) {
        addRequirements(RobotContainer.drive);
        distance = 0;
        this.pathName = pathName;
        // Use addRequirements() here to declare subsystem dependencies.
    }

    /**
     * Create a motion profile command to drive a straight line using motion magic
     * @param distance The distance to drive
     */
    public DriveMotionProfile(double distance) {
        addRequirements(RobotContainer.drive);
        this.distance = distance;
    }
    int i = -20;

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        i = -20;
        RobotContainer.drive.resetEncoders();
        BufferedTrajectoryPointStream left = RobotContainer.drive.loadProfileAsBuffer(pathName + "_left", true);
//        left2 = RobotContainer.drive.loadProfile(pathName + "_left");
        BufferedTrajectoryPointStream right = RobotContainer.drive.loadProfileAsBuffer(pathName + "_right", false);
//        right2 = RobotContainer.drive.loadProfile(pathName + "_right");
        System.out.println("======================================");
        if (distance != 0) {
//            System.out.println(distance * Constants.DRIVE_PULSES_PER_FOOT);
            RobotContainer.drive.setMotorOutputs(ControlMode.MotionMagic, distance * Constants.DRIVE_PULSES_PER_FOOT, distance * Constants.DRIVE_PULSES_PER_FOOT);
        } else {
            RobotContainer.drive.startMotionProfile(left, right);
        }
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
//        if(i < left2.length && i >= 0) {
//            System.out.println("drive_train_left," + left2[i][0] / Constants.DRIVE_PULSES_PER_FOOT + "," + RobotContainer.drive.getLeftPosition());
//            System.out.println("drive_train_right," + right2[i][0] / Constants.DRIVE_PULSES_PER_FOOT + "," + RobotContainer.drive.getRightPosition());
//        }
//        i += 2;
//        RobotContainer.drive.setMotorOutputs(ControlMode.MotionMagic, distance * Constants.DRIVE_PULSES_PER_FOOT, distance * Constants.DRIVE_PULSES_PER_FOOT);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        RobotContainer.drive.setMotorOutputs(ControlMode.PercentOutput, 0, 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        if(distance != 0){
            return Math.abs(RobotContainer.drive.getActiveTrajPositionLeft()) >= Math.abs(distance) - 0.1;
        }else{
            return RobotContainer.drive.isMotionProfileFinished();
        }
    }
}
