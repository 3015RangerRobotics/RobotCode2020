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
import frc.robot.RobotContainer;

public class DriveMotionProfile extends CommandBase {
    /**
     * Creates a new DriveMotionProfile.
     */
    private BufferedTrajectoryPointStream left;
    private BufferedTrajectoryPointStream right;
    private double distance = 0;

    public DriveMotionProfile(String pathName) {
        addRequirements(RobotContainer.drive);
        left = RobotContainer.drive.loadProfile(pathName + "_left");
        right = RobotContainer.drive.loadProfile(pathName + "_right");
        distance = 0;
        // Use addRequirements() here to declare subsystem dependencies.
    }

    public DriveMotionProfile(double distance) {
        addRequirements(RobotContainer.drive);
        this.distance = distance;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        RobotContainer.drive.resetEncoders();
        if (distance != 0) {
            RobotContainer.drive.setMotorOutputs(ControlMode.MotionMagic, distance, distance);
        } else {
            RobotContainer.drive.startMotionProfile(left, right);
        }
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
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
            return RobotContainer.drive.isClosedLoopOnTarget();
        }else{
            return RobotContainer.drive.isMotionProfileFinished();
        }
    }
}
