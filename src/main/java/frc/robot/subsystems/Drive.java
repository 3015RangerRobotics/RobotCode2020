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
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.music.Orchestra;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

    private Orchestra orchestra;
    private PigeonIMU imu;

    public Drive() {
        this.imu = new PigeonIMU(Constants.DRIVE_PIGEON);
        this.rightMaster = new TalonFX(Constants.DRIVE_RIGHT_MASTER);
        this.rightFollower = new TalonFX(Constants.DRIVE_RIGHT_FOLLOWER);
        this.leftMaster = new TalonFX(Constants.DRIVE_LEFT_MASTER);
        this.leftFollower = new TalonFX(Constants.DRIVE_LEFT_FOLLOWER);

        rightMaster.configFactoryDefault();
        leftMaster.configFactoryDefault();
        imu.configFactoryDefault();

        TalonFXConfiguration rightConfig = new TalonFXConfiguration();
        rightConfig.remoteFilter0.remoteSensorDeviceID = imu.getDeviceID();
        rightConfig.remoteFilter0.remoteSensorSource = RemoteSensorSource.Pigeon_Yaw;
        rightConfig.remoteFilter1.remoteSensorDeviceID = leftMaster.getDeviceID();
        rightConfig.remoteFilter1.remoteSensorSource = RemoteSensorSource.TalonFX_SelectedSensor;
        rightConfig.diff0Term = FeedbackDevice.IntegratedSensor;
        rightConfig.diff1Term = FeedbackDevice.RemoteSensor1;
        rightConfig.primaryPID.selectedFeedbackSensor = FeedbackDevice.SensorDifference;
        rightConfig.primaryPID.selectedFeedbackCoefficient = 0.5;
        rightConfig.auxiliaryPID.selectedFeedbackSensor = FeedbackDevice.RemoteSensor0;
        rightConfig.neutralDeadband = Constants.DRIVE_NEUTRAL_DEADBAND;
        rightConfig.slot0.kP = Constants.DRIVE_P;
        rightConfig.slot0.kI = Constants.DRIVE_I;
        rightConfig.slot0.kD = Constants.DRIVE_D;
        rightConfig.slot0.kF = Constants.DRIVE_F;
        rightConfig.slot1.kP = Constants.DRIVE_P;
        rightConfig.slot1.kI = Constants.DRIVE_I;
        rightConfig.slot1.kD = Constants.DRIVE_D;
        rightConfig.slot1.kF = Constants.DRIVE_F;

        rightMaster.configAllSettings(rightConfig);


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
        leftMaster.setStatusFramePeriod(StatusFrame.Status_10_Targets, 10);
        leftMaster.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 10);
        leftMaster.setStatusFramePeriod(StatusFrame.Status_17_Targets1, 10);


        rightMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_1_General, 10);
        rightMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 10);
        rightMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_9_MotProfBuffer, 10);
        rightMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10);
        rightMaster.setStatusFramePeriod(StatusFrame.Status_10_Targets, 10);
        rightMaster.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 10);
        rightMaster.setStatusFramePeriod(StatusFrame.Status_17_Targets1, 10);

        rightMaster.enableVoltageCompensation(true);
        rightMaster.configVoltageCompSaturation(12.5);
        leftMaster.enableVoltageCompensation(true);
        leftMaster.configVoltageCompSaturation(12.5);

        rightMaster.setSensorPhase(true);
        leftMaster.setSensorPhase(false);

        ArrayList<TalonFX> instruments = new ArrayList<>();
        instruments.add(RobotContainer.shooter.shooter);
        instruments.add(leftMaster);
        instruments.add(rightMaster);
//
        orchestra = new Orchestra(instruments);
        orchestra.loadMusic("jeopardy.chrp");

        resetEncoders();
        resetIMU();
        enableBrakeMode();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("gyro", getAngle());
    }

    public void playMusic(){
        orchestra.play();
    }

    public double getAngle(){
        return imu.getFusedHeading();
    }

    public void resetIMU(){
        imu.setYaw(0);
    }

    public void stopMusic(){
        orchestra.stop();
    }


    /**
     * Reset the drive encoders to 0
     */
    public void resetEncoders() {
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

    public void curvatureDrive(double throttle, double turn, boolean isQuickTurn, boolean squaredInputs) {
        DriveSignal ds = DriveHelper.curvatureDrive(throttle, turn, isQuickTurn, squaredInputs);
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
     * @param profile The profile
     */
    public void startMotionProfile(BufferedTrajectoryPointStream profile) {
        leftMaster.follow(rightMaster);
        rightMaster.startMotionProfile(profile, 10, ControlMode.MotionProfile);
    }

    /**
     * Get if the motion profile is finished
     * @return is the motion profile finished
     */
    public boolean isMotionProfileFinished() {
        return rightMaster.isMotionProfileFinished();
    }
}
