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
import frc.robot.commands.DriveWithGamepad;

public class Drive extends SubsystemBase {
    /**
     * Creates a new Drive.
     */
    private TalonFX rightMaster;
    private TalonFX rightFollower;

    private TalonFX leftMaster;
    private TalonFX leftFollower;

    public Drive() {
        this.rightMaster = new TalonFX(Constants.rightDriveMaster);
        this.rightFollower = new TalonFX(Constants.rightDriveFollower);
        this.leftMaster = new TalonFX(Constants.leftDriveMaster);
        this.leftFollower = new TalonFX(Constants.leftDriveFollower);

        rightMaster.configFactoryDefault();
        leftMaster.configFactoryDefault();

        rightFollower.follow(rightMaster);
        leftFollower.follow(leftMaster);

        rightMaster.setInverted(true);
        rightFollower.setInverted(true);//must be explicit, inverting master will not invert follower.
        leftMaster.setInverted(false);
        leftFollower.setInverted(false);

        rightMaster.setNeutralMode(NeutralMode.Coast);
        rightFollower.setNeutralMode(NeutralMode.Coast);
        leftMaster.setNeutralMode(NeutralMode.Coast);
        leftFollower.setNeutralMode(NeutralMode.Coast);

        rightMaster.enableVoltageCompensation(true);
        rightMaster.configVoltageCompSaturation(12.5);
        leftMaster.enableVoltageCompensation(true);
        leftMaster.configVoltageCompSaturation(12.5);

        rightMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        leftMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

        rightMaster.setSensorPhase(true);
        leftMaster.setSensorPhase(false);

        rightMaster.config_kP(0, Constants.driveP);
        rightMaster.config_kI(0, Constants.driveI);
        rightMaster.config_kD(0, Constants.driveD);
        rightMaster.config_kF(0, Constants.driveF);

        leftMaster.config_kP(0, Constants.driveP);
        leftMaster.config_kI(0, Constants.driveI);
        leftMaster.config_kD(0, Constants.driveD);
        leftMaster.config_kF(0, Constants.driveF);

        leftMaster.configMotionCruiseVelocity((int) Math.round(Constants.driveMaxVelocity/10));
        rightMaster.configMotionCruiseVelocity((int) Math.round(Constants.driveMaxVelocity/10));
        leftMaster.configMotionAcceleration((int) Math.round(Constants.driveMaxAcceleration/10));
        rightMaster.configMotionAcceleration((int) Math.round(Constants.driveMaxAcceleration/10));
        leftMaster.configAllowableClosedloopError(0, (int) Math.round(Constants.driveMaxMotionError));
        rightMaster.configAllowableClosedloopError(0, (int) Math.round(Constants.driveMaxMotionError));



        resetEncoders();

        // this.setDefaultCommand(new DriveWithGamepad());
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    public void resetEncoders(){
        leftMaster.setSelectedSensorPosition(0);
        rightMaster.setSelectedSensorPosition(0);
    }

    public ControlMode getControlMode(){
        return leftMaster.getControlMode();
    }

    public void setMotorOutputs(ControlMode mode, double leftMotor, double rightMotor) {
        this.rightMaster.set(mode, rightMotor);
        this.leftMaster.set(mode, leftMotor);
    }

    public void arcadeDrive(double driveValue, double turnValue, boolean squaredInputs) {
        DriveSignal ds = DriveHelper.arcadeDrive(driveValue, turnValue, squaredInputs);
        setMotorOutputs(ControlMode.PercentOutput, ds.leftSignal, ds.rightSignal);
    }

    public BufferedTrajectoryPointStream createBuffer(double[][] profile) {
        BufferedTrajectoryPointStream buffer = new BufferedTrajectoryPointStream();

        for (int i = 0; i < profile.length; i++) {
            TrajectoryPoint point = new TrajectoryPoint();
            double position = profile[i][0];
            double velocity = profile[i][1];

            point.timeDur = Constants.timeStep;
            point.position = position * Constants.drivePulsesPerFoot;
            point.velocity = velocity * Constants.drivePulsesPerFoot / 10;

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
    public boolean isClosedLoopOnTarget () {
        return Math.abs(leftMaster.getClosedLoopError()) <= Constants.driveMaxMotionError 
        && Math.abs(rightMaster.getClosedLoopError()) <= Constants.driveMaxMotionError;
    }

    public void startMotionProfile(BufferedTrajectoryPointStream left, BufferedTrajectoryPointStream right) {
        leftMaster.startMotionProfile(left, 10, ControlMode.MotionProfile);
        rightMaster.startMotionProfile(right, 10, ControlMode.MotionProfile);

    }

    public boolean isMotionProfileFinished() {
        return leftMaster.isMotionProfileFinished() && rightMaster.isMotionProfileFinished();
    }

    public double[][] loadProfile(String profileName) {
        double[][] profile = new double[][] {};
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
                profile[i][0] = points.get(i)[0];
                profile[i][1] = points.get(i)[1];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return profile;
    }
}
