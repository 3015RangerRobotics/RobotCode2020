package frc.robot.subsystems;

import java.util.ArrayList;
import com.ctre.phoenix.motion.BufferedTrajectoryPointStream;
import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.music.Orchestra;
import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.DriveHelper;
import frc.robot.DriveSignal;
import frc.robot.RobotContainer;

public class Drive extends SubsystemBase {
    public TalonFX rightMaster;
    private TalonFX rightFollower;

    private TalonFX leftMaster;
    private TalonFX leftFollower;

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
        rightConfig.diff1Term = FeedbackDevice.IntegratedSensor;
        rightConfig.diff0Term = FeedbackDevice.RemoteSensor1;
        rightConfig.primaryPID.selectedFeedbackSensor = FeedbackDevice.SensorDifference;
        rightConfig.primaryPID.selectedFeedbackCoefficient = 0.5;
        rightConfig.auxiliaryPID.selectedFeedbackSensor = FeedbackDevice.RemoteSensor0;
        rightConfig.neutralDeadband = Constants.DRIVE_NEUTRAL_DEADBAND;
        rightConfig.slot0.kP = Constants.DRIVE_P;
        rightConfig.slot0.kI = Constants.DRIVE_I;
        rightConfig.slot0.kD = Constants.DRIVE_D;
        rightConfig.slot0.kF = Constants.DRIVE_F;
        rightConfig.slot1.kP = Constants.DRIVE_MP_TURN_P;
        rightConfig.slot1.kI = Constants.DRIVE_MP_TURN_I;
        rightConfig.slot1.kD = Constants.DRIVE_MP_TURN_D;
        rightConfig.slot1.kF = Constants.DRIVE_MP_TURN_F;

        rightMaster.configAllSettings(rightConfig);

        TalonFXConfiguration leftConfig = new TalonFXConfiguration();
        leftConfig.remoteFilter0.remoteSensorDeviceID = imu.getDeviceID();
        leftConfig.remoteFilter0.remoteSensorSource = RemoteSensorSource.Pigeon_Yaw;
        leftConfig.neutralDeadband = Constants.DRIVE_NEUTRAL_DEADBAND;
        leftConfig.slot0.kP = Constants.DRIVE_TURN_P;
        leftConfig.slot0.kI = Constants.DRIVE_TURN_I;
        leftConfig.slot0.kD = Constants.DRIVE_TURN_D;
        leftConfig.slot0.kF = Constants.DRIVE_TURN_F;
        leftConfig.slot0.integralZone = (int) Math.round(Constants.DRIVE_TURN_I_ZONE);
        leftConfig.motionCruiseVelocity = (int) Math.round(Constants.DRIVE_TURN_MAX_VELOCITY);
        leftConfig.motionAcceleration = (int) Math.round(Constants.DRIVE_TURN_MAX_ACCELERATION);

        leftMaster.configAllSettings(leftConfig);
        leftMaster.configAllowableClosedloopError(0, (int) Math.round(Constants.DRIVE_TURN_ERROR));

        rightFollower.follow(rightMaster);
        leftFollower.follow(leftMaster);

        rightMaster.setInverted(true);
        rightFollower.setInverted(true);
        leftMaster.setInverted(false);
        leftFollower.setInverted(false);

        leftMaster.setStatusFramePeriod(StatusFrame.Status_1_General, 10);
        leftMaster.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 10);
        leftMaster.setStatusFramePeriod(StatusFrame.Status_9_MotProfBuffer, 10);
        leftMaster.setStatusFramePeriod(StatusFrame.Status_10_MotionMagic, 10);
        leftMaster.setStatusFramePeriod(StatusFrame.Status_10_Targets, 10);
        leftMaster.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 10);
        leftMaster.setStatusFramePeriod(StatusFrame.Status_17_Targets1, 10);


        rightMaster.setStatusFramePeriod(StatusFrame.Status_1_General, 10);
        rightMaster.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 10);
        rightMaster.setStatusFramePeriod(StatusFrame.Status_9_MotProfBuffer, 10);
        rightMaster.setStatusFramePeriod(StatusFrame.Status_10_MotionMagic, 10);
        rightMaster.setStatusFramePeriod(StatusFrame.Status_10_Targets, 10);
        rightMaster.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 10);
        rightMaster.setStatusFramePeriod(StatusFrame.Status_17_Targets1, 10);

        rightMaster.enableVoltageCompensation(true);
        rightMaster.configVoltageCompSaturation(12.5);
        leftMaster.enableVoltageCompensation(true);
        leftMaster.configVoltageCompSaturation(12.5);

        rightMaster.setSensorPhase(true);
        leftMaster.setSensorPhase(false);

        resetEncoders();
        resetIMU();
        enableBrakeMode();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("gyro", getAngle());
        SmartDashboard.putNumber("right", rightMaster.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("left", leftMaster.getSelectedSensorPosition(0));

        SmartDashboard.putNumber("IMU Uptime", imu.getUpTime());
    }

    /**
     * Get the angle from the IMU
     * @return The angle of the robot in degrees
     */
    public double getAngle(){
        return -rightMaster.getSelectedSensorPosition(1) / Constants.DRIVE_PIGEON_UNITS_PER_DEGREE;
    }

    /**
     * Reset the IMU
     */
    public void resetIMU(){
        imu.setYaw(0);
        leftMaster.configSelectedFeedbackSensor(FeedbackDevice.RemoteSensor0, 0, 20);
        leftMaster.setSelectedSensorPosition(0, 0, 20);
        leftMaster.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 20);
        rightMaster.setSelectedSensorPosition(0, 1, 20);
    }

    /**
     * Reset the drive encoders
     */
    public void resetEncoders() {
        leftMaster.getSensorCollection().setIntegratedSensorPosition(0, 20);
        rightMaster.getSensorCollection().setIntegratedSensorPosition(0, 20);
    }

    /**
     * Enable coast mode
     */
    public void enableCoastMode(){
        rightMaster.setNeutralMode(NeutralMode.Coast);
        rightFollower.setNeutralMode(NeutralMode.Coast);
        leftMaster.setNeutralMode(NeutralMode.Coast);
        leftFollower.setNeutralMode(NeutralMode.Coast);
    }

    /**
     * Enable brake mode
     */
    public void enableBrakeMode(){
        rightMaster.setNeutralMode(NeutralMode.Brake);
        rightFollower.setNeutralMode(NeutralMode.Brake);
        leftMaster.setNeutralMode(NeutralMode.Brake);
        leftFollower.setNeutralMode(NeutralMode.Brake);
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
     * Start following a motion profile
     * @param profile The profile
     */
    public void startMotionProfile(BufferedTrajectoryPointStream profile) {
        leftMaster.follow(rightMaster, FollowerType.AuxOutput1);
        rightMaster.startMotionProfile(profile, 10, ControlMode.MotionProfileArc);
    }

    /**
     * Get if the motion profile is finished
     * @return is the motion profile finished
     */
    public boolean isMotionProfileFinished() {
        return rightMaster.isMotionProfileFinished();
    }

    public double getPosition(){
        return rightMaster.getSelectedSensorPosition();
    }

    public double getExpectedPosition(int slot){
        return rightMaster.getActiveTrajectoryPosition(slot);
    }

    public void turnInPlaceSetup() {
        leftMaster.configSelectedFeedbackSensor(FeedbackDevice.RemoteSensor0, 0, 20);
        leftMaster.setSelectedSensorPosition(0, 0, 20);
        leftMaster.setSensorPhase(true);
        rightMaster.setInverted(false);
        rightFollower.setInverted(false);
        rightMaster.follow(leftMaster);
    }

    public void turnInPlaceCleanup() {
        leftMaster.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 20);
        leftMaster.setSensorPhase(false);
        rightMaster.setInverted(true);
        rightFollower.setInverted(true);
    }

    public void turnInPlace(double angle) {
        leftMaster.set(ControlMode.Position, angle);
    }

    public boolean isLeftOnTarget() {
        return Math.abs(leftMaster.getClosedLoopError()) <= Constants.DRIVE_PIGEON_UNITS_PER_DEGREE;
    }

    public double getLeftSensorPosition() {
        return leftMaster.getSelectedSensorPosition();
    }
}
