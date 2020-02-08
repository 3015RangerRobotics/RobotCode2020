/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import com.ctre.phoenix.motion.BufferedTrajectoryPointStream;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.music.Orchestra;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.DriveHelper;
import frc.robot.DriveSignal;
import frc.robot.RobotContainer;

public class Drive extends SubsystemBase {
    /**
     * Creates a new Drive.
     */
    private TalonFX rightMaster;
    private TalonFX rightFollower;

    private TalonFX leftMaster;
    private TalonFX leftFollower;

//    private double leftEncoderOffset = 0;
//    private double rightEncoderOffset = 0;

    private Orchestra orchestra;
    private AHRS imu;

    public Drive() {
        this.imu = new AHRS(SerialPort.Port.kMXP);
        this.rightMaster = new TalonFX(Constants.DRIVE_RIGHT_MASTER);
        this.rightFollower = new TalonFX(Constants.DRIVE_RIGHT_FOLLOWER);
        this.leftMaster = new TalonFX(Constants.DRIVE_LEFT_MASTER);
        this.leftFollower = new TalonFX(Constants.DRIVE_LEFT_FOLLOWER);

        rightMaster.configFactoryDefault();
        leftMaster.configFactoryDefault();

        rightFollower.follow(rightMaster);
        leftFollower.follow(leftMaster);

        rightMaster.setInverted(true);
        rightFollower.setInverted(true);//must be explicit, inverting master will not invert follower.
        leftMaster.setInverted(false);
        leftFollower.setInverted(false);

        leftMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_1_General, 10);
        leftMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 10);
        leftMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_9_MotProfBuffer, 10);
        leftMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10);

        leftFollower.setStatusFramePeriod(StatusFrameEnhanced.Status_1_General, 10);
        leftFollower.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 10);
        leftFollower.setStatusFramePeriod(StatusFrameEnhanced.Status_9_MotProfBuffer, 10);
        leftFollower.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10);

        rightMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_1_General, 10);
        rightMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 10);
        rightMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_9_MotProfBuffer, 10);
        rightMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10);

        rightFollower.setStatusFramePeriod(StatusFrameEnhanced.Status_1_General, 10);
        rightFollower.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 10);
        rightFollower.setStatusFramePeriod(StatusFrameEnhanced.Status_9_MotProfBuffer, 10);
        rightFollower.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10);

        enableBrakeMode();

        rightMaster.enableVoltageCompensation(true);
        rightMaster.configVoltageCompSaturation(12.5);
        leftMaster.enableVoltageCompensation(true);
        leftMaster.configVoltageCompSaturation(12.5);

        rightMaster.setSensorPhase(true);
        leftMaster.setSensorPhase(false);

        rightMaster.config_kP(0, Constants.DRIVE_P);
        rightMaster.config_kI(0, Constants.DRIVE_I);
        rightMaster.config_kD(0, Constants.DRIVE_D);
        rightMaster.config_kF(0, Constants.DRIVE_F);

        leftMaster.config_kP(0, Constants.DRIVE_P);
        leftMaster.config_kI(0, Constants.DRIVE_I);
        leftMaster.config_kD(0, Constants.DRIVE_D);
        leftMaster.config_kF(0, Constants.DRIVE_F);

        leftMaster.configMotionCruiseVelocity((int) Math.round(Constants.DRIVE_MAX_VELOCITY / 10));
        rightMaster.configMotionCruiseVelocity((int) Math.round(Constants.DRIVE_MAX_VELOCITY / 10));
        leftMaster.configMotionAcceleration((int) Math.round(Constants.DRIVE_MAX_ACCELERATION / 10));
        rightMaster.configMotionAcceleration((int) Math.round(Constants.DRIVE_MAX_ACCELERATION / 10));
        leftMaster.configAllowableClosedloopError(0, (int) Math.round(Constants.DRIVE_MAX_MOTION_ERROR));
        rightMaster.configAllowableClosedloopError(0, (int) Math.round(Constants.DRIVE_MAX_MOTION_ERROR));

        ArrayList<TalonFX> instruments = new ArrayList<>();
        instruments.add(RobotContainer.shooter.shooter);
        instruments.add(leftMaster);
//        instruments.add(leftFollower);
        instruments.add(rightMaster);
//        instruments.add(rightFollower);
//
        orchestra = new Orchestra(instruments);
        orchestra.loadMusic("jeopardy.chrp");

        resetEncoders();
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
//        System.out.println(rightMaster.getSelectedSensorVelocity());
    }

    public void playMusic(){
        orchestra.play();
    }

    public double getAngle(){
        return imu.getAngle();
    }

    public void resetIMU(){
        imu.reset();
    }

    public void stopMusic(){
        orchestra.stop();
    }

    public double getLeftPositionRaw(){
        return leftMaster.getSelectedSensorPosition();
    }

    public double getRightPositionRaw(){
        return rightMaster.getSelectedSensorPosition();
    }

    public double getActiveTrajPositionLeft(){
        return leftMaster.getActiveTrajectoryPosition() / Constants.DRIVE_PULSES_PER_FOOT;
    }

    public double getActiveTrajPositionRight(){
        return rightMaster.getActiveTrajectoryPosition() / Constants.DRIVE_PULSES_PER_FOOT;
    }

//    public double getLeftPosition(){
//        return leftMaster.getSelectedSensorPosition() / Constants.DRIVE_PULSES_PER_FOOT;
//    }
//
//    public double getRightPosition(){
//        return rightMaster.getSelectedSensorPosition() / Constants.DRIVE_PULSES_PER_FOOT;
//    }
//
//    public double getLeftPositionRaw(){
//        return leftMaster.getSelectedSensorPosition();
//    }
//
//    public double getRightPositionRaw(){
//        return rightMaster.getSelectedSensorPosition();
//    }
//
//    public double getLeftError(){
//        return leftMaster.getClosedLoopError() / Constants.DRIVE_PULSES_PER_FOOT;
//    }
//
//    public double getRightError(){
//        return rightMaster.getClosedLoopError() / Constants.DRIVE_PULSES_PER_FOOT;
//    }


    /**
     * Reset the drive encoders to 0
     */
    public void resetEncoders() {
//        leftEncoderOffset = leftMaster.getSelectedSensorPosition();
//        rightEncoderOffset = rightMaster.getSelectedSensorPosition();
        leftMaster.setSelectedSensorPosition(0);
        rightMaster.setSelectedSensorPosition(0);
    }

    public void enableCoastMode(){
        rightMaster.setNeutralMode(NeutralMode.Coast);
        rightFollower.setNeutralMode(NeutralMode.Coast);
        leftMaster.setNeutralMode(NeutralMode.Coast);
        leftFollower.setNeutralMode(NeutralMode.Coast);
    }

    public void enableBrakeMode(){
        rightMaster.setNeutralMode(NeutralMode.Brake);
        rightFollower.setNeutralMode(NeutralMode.Brake);
        leftMaster.setNeutralMode(NeutralMode.Brake);
        leftFollower.setNeutralMode(NeutralMode.Brake);
    }

    /**
     * Get the current control mode of the drive motors
     * @return The current control mode
     */
    public ControlMode getControlMode() {
        return leftMaster.getControlMode();
    }

    /**
     * Set the output to the drive motors
     * @param mode The control mode to use
     * @param leftMotor Output value of the left motor
     * @param rightMotor Output value of the right motor
     */
    public void setMotorOutputs(ControlMode mode, double leftMotor, double rightMotor) {
        this.rightMaster.set(mode, rightMotor);
        this.leftMaster.set(mode, leftMotor);
    }

    /**
     * Set the motors using arcade drive
     * @param driveValue Forward/Reverse value
     * @param turnValue Turn value
     * @param squaredInputs Should the inputs be squared (increases control at low speeds)
     */
    public void arcadeDrive(double driveValue, double turnValue, boolean squaredInputs) {
        DriveSignal ds = DriveHelper.arcadeDrive(driveValue, turnValue, squaredInputs);
        setMotorOutputs(ControlMode.PercentOutput, ds.leftSignal, ds.rightSignal);
    }

    /**
     * Get if the closed loop control is on target
     * @return if the closed loop control is on target
     */
    public boolean isClosedLoopOnTarget() {
        return Math.abs(leftMaster.getClosedLoopError()) <= Constants.DRIVE_MAX_MOTION_ERROR
                && Math.abs(rightMaster.getClosedLoopError()) <= Constants.DRIVE_MAX_MOTION_ERROR;
    }

    /**
     * Start following a motion profile
     * @param left The profile for the left motors
     * @param right The profile for the right motors
     */
    public void startMotionProfile(BufferedTrajectoryPointStream left, BufferedTrajectoryPointStream right) {
        leftMaster.startMotionProfile(left, 10, ControlMode.MotionProfile);
        rightMaster.startMotionProfile(right, 10, ControlMode.MotionProfile);
    }

    /**
     * Get if the motion profile is finished
     * @return is the motion profile finished
     */
    public boolean isMotionProfileFinished() {
        return leftMaster.isMotionProfileFinished() && rightMaster.isMotionProfileFinished();
    }
}
