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
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.DriveHelper;
import frc.robot.DriveSignal;

public class Drive extends SubsystemBase {
    /**
     * Creates a new Drive.
     */
    private TalonFX rightMaster;
    private TalonFX rightFollower;

    private TalonFX leftMaster;
    private TalonFX leftFollower;

    private double leftEncoderOffset = 0;
    private double rightEncoderOffset = 0;

    public Drive() {
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

//        rightMaster.setNeutralMode(NeutralMode.Coast);
//        rightFollower.setNeutralMode(NeutralMode.Coast);
//        leftMaster.setNeutralMode(NeutralMode.Coast);
//        leftFollower.setNeutralMode(NeutralMode.Coast);\

        enableBrakeMode();

        rightMaster.enableVoltageCompensation(true);
        rightMaster.configVoltageCompSaturation(12.5);
        leftMaster.enableVoltageCompensation(true);
        leftMaster.configVoltageCompSaturation(12.5);

        // rightMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        // leftMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

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


        resetEncoders();
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
//        System.out.println(rightMaster.getSelectedSensorVelocity());
    }

    public double getActiveTrajPositionLeft(){
        return (leftMaster.getActiveTrajectoryPosition() - leftEncoderOffset) / Constants.DRIVE_PULSES_PER_FOOT;
    }

    public double getActiveTrajPositionRight(){
        return (rightMaster.getActiveTrajectoryPosition() - rightEncoderOffset) / Constants.DRIVE_PULSES_PER_FOOT;
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
        leftEncoderOffset = leftMaster.getSelectedSensorPosition();
        rightEncoderOffset = rightMaster.getSelectedSensorPosition();
//        leftMaster.setSelectedSensorPosition(0);
//        rightMaster.setSelectedSensorPosition(0);
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
        if(mode == ControlMode.MotionMagic){
            this.rightMaster.set(mode, rightMotor + rightEncoderOffset);
            this.leftMaster.set(mode, leftMotor + leftEncoderOffset);
        }else{
            this.rightMaster.set(mode, rightMotor);
            this.leftMaster.set(mode, leftMotor);
        }
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
     * Create a BufferedTrajectoryPointStream for the drive motors to follow
     * @param pathName The profile to load
     * @return The given profile as a BufferedTrajectoryPointStream
     */
    public BufferedTrajectoryPointStream loadProfileAsBuffer(String pathName, boolean isLeft) {
        BufferedTrajectoryPointStream buffer = new BufferedTrajectoryPointStream();
        double[][] profile = loadProfile(pathName);

        for (int i = 0; i < profile.length; i++) {
            TrajectoryPoint point = new TrajectoryPoint();
            double position = profile[i][0] + (isLeft ? leftEncoderOffset : rightEncoderOffset);
            double velocity = profile[i][1];

            point.timeDur = Constants.DRIVE_TIME_STEP;
            point.position = position;
            point.velocity = velocity;

            point.auxiliaryPos = 0;
            point.auxiliaryVel = 0;
            point.profileSlotSelect0 = 0;
            point.profileSlotSelect1 = 1;
            point.zeroPos = (i == 0);
            point.isLastPoint = ((i + 1) == profile.length);
            point.arbFeedFwd = 0;

            buffer.Write(point);

        }
        return buffer;
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

    /**
     * Load a motion profile from a csv file
     * @param profileName The name of the profile to load
     * @return The profile as a BufferedTrajectoryPointStream
     */
    public double[][] loadProfile(String profileName) {
        double[][] profile = new double[][]{};
        try (BufferedReader br = new BufferedReader(
                new FileReader(new File(Filesystem.getDeployDirectory(), "paths/" + profileName + ".csv")))) {
            ArrayList<double[]> points = new ArrayList<double[]>();

            String line = "";
            while ((line = br.readLine()) != null) {
                String[] pointString = line.split(",");
                double[] point = new double[2];
                for (int i = 0; i < 2; i++) {
                    point[i] = Double.parseDouble(pointString[i]);
                }
                points.add(point);
            }
            profile = new double[points.size()][2];
            for (int i = 0; i < points.size(); i++) {
                profile[i][0] = points.get(i)[0] * Constants.DRIVE_PULSES_PER_FOOT;
                profile[i][1] = points.get(i)[1] * Constants.DRIVE_PULSES_PER_FOOT / 10;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return profile;
    }
}
