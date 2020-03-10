package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class Shooter extends SubsystemBase {
    public TalonFX shooter;

    private enum State {
        kSetSpeed,
        kAutoSpeed,
        kAutoSpeedFender,
        kOff,
        kTesting
    }

    public Shooter.State state = Shooter.State.kOff;

    private double setSpeed;

    public Shooter() {
        shooter = new TalonFX(Constants.SHOOTER_MOTOR);
        shooter.configFactoryDefault();

        shooter.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 10);
        shooter.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 10);

        shooter.setNeutralMode(NeutralMode.Coast);

        shooter.enableVoltageCompensation(true);
        shooter.configVoltageCompSaturation(12.5);

        shooter.setInverted(false);
        shooter.setSelectedSensorPosition(0);
        shooter.setSensorPhase(false);

        setRampRate(true);

        shooter.config_kP(0, Constants.SHOOTER_P);
        shooter.config_kI(0, Constants.SHOOTER_I);
        shooter.config_kD(0, Constants.SHOOTER_D);
        shooter.config_kF(0, Constants.SHOOTER_F);

        shooter.config_kP(1, Constants.SHOOTER_SHOOT_P);
        shooter.config_kI(1, Constants.SHOOTER_SHOOT_I);
        shooter.config_kD(1, Constants.SHOOTER_SHOOT_D);
        shooter.config_kF(1, Constants.SHOOTER_SHOOT_F);

    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("shooter speed", setSpeed);
        if(getRPM() >= 7500){
            shooter.set(ControlMode.PercentOutput, 0);
        }
        double turretPos = RobotContainer.turret.getPosition() + RobotContainer.limelight.getTargetAngleX();
        double speed;
        switch(state) {
            case kSetSpeed:
                speed = (setSpeed + (2 * Math.abs(turretPos)));
                set(ControlMode.Velocity, speed * Constants.SHOOTER_PULSES_PER_ROTATION / 600);
//                System.out.println("shooter," + speed + "," + getRPM());
                break;
            case kAutoSpeed:
                setSpeed = getAutoSpeed(false);
                speed = (setSpeed + (2 * Math.abs(turretPos)));
                set(ControlMode.Velocity, speed * Constants.SHOOTER_PULSES_PER_ROTATION / 600);
//                System.out.println("shooter," + speed + "," + getRPM());
                break;
            case kAutoSpeedFender:
                if(!RobotContainer.limelight.hasTarget()){
                    RobotContainer.setDriverRumbleLeft(1);
                    RobotContainer.setDriverRumbleRight(1);
                    RobotContainer.setCoDriverRumbleLeft(1);
                    RobotContainer.setCoDriverRumbleRight(1);
                }else{
                    RobotContainer.setDriverRumbleLeft(0);
                    RobotContainer.setDriverRumbleRight(0);
                    RobotContainer.setCoDriverRumbleLeft(0);
                    RobotContainer.setCoDriverRumbleRight(0);
                }
                setSpeed = getAutoSpeed(true);
                set(ControlMode.Velocity, setSpeed * Constants.SHOOTER_PULSES_PER_ROTATION / 600);
                break;
            case kTesting:
                break;
            case kOff:
            default:
                setSpeed = 0;
                set(ControlMode.PercentOutput, 0);
                break;
        }
    }

    /**
     * @return The RPM of the shooter wheel
     */
    public double getRPM() {
        return (shooter.getSelectedSensorVelocity() * 10 * 60 / Constants.SHOOTER_PULSES_PER_ROTATION);
    }

    /**
     * Set the output of the shooter wheel
     * @param mode The control mode to use
     * @param value The value to set
     */
    public void set(ControlMode mode, double value) {
        shooter.set(mode, value);
    }

    public void selectProfileSlot(int id){
        shooter.selectProfileSlot(id, 0);
    }

    public boolean isRunning()
    {
        return shooter.getControlMode() == ControlMode.Velocity;

    }

    public void setRampRate(boolean enabled) {
        shooter.configClosedloopRamp(enabled ? 0 : 0);
    }

    public void setStateSpeed(double speed) {
        setSpeed = speed;
        state = State.kSetSpeed;
    }

    public void setStateTesting() {
        state = State.kTesting;
        set(ControlMode.PercentOutput, 0);
    }

    public void setStateAutoSpeed() {
        state = State.kAutoSpeed;
    }

    public void setStateAutoSpeedFender() {
        state = State.kAutoSpeedFender;
    }

    public void setStateOff() {
        state = State.kOff;
    }

    public double getAutoSpeed(boolean isFender) {
        if(RobotContainer.limelight.hasTarget()) {
            double d = RobotContainer.limelight.getRobotToTargetDistance();
            if(!isFender) {
//           double rpm = 7430.1186 + (-255.07933*d) + (7.2472131*d*d); //Perfect ball
                return 4222.866701 + (110.34724 * d) + (-1.51320429 * d * d); //Average ball
            }else{

                if(d <= 8){
                    return 7000;
                }else if(d <= 10){
                    return 5000;
                }else if(d <= 20){
                    return 3300;
                }else if(d <= 25){
                    return 3100;
                }
                return 3300;
            }
        } else {
            return isFender ? 3300 : 5400;
        }
    }

    public boolean isPrimed() {
        double turretPos = RobotContainer.turret.getPosition() + RobotContainer.limelight.getTargetAngleX();
//        if(state == State.kSetSpeed && badBall) {
//            return Math.abs((setSpeed + (2 * Math.abs(turretPos)) * badBallFactor) - getRPM()) <= Constants.SHOOTER_TOLERANCE;
//        }
        return Math.abs(setSpeed + (2 * Math.abs(turretPos)) - getRPM()) <= Constants.SHOOTER_TOLERANCE;
    }

//    public void setBadBall(boolean badBall) {
//        this.badBall = badBall;
//    }
}

