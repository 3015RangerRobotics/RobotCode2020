package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;

public class Turret extends SubsystemBase {
    private TalonSRX turretMotor;
    private DigitalInput leftLimit;
    private DigitalInput rightLimit;
    private boolean isLeftShot = false;

    private enum State {
        kToPosition,
        kTurnHold,
        kHoming,
        kDefault
    }

    State state = State.kDefault;

    double toPosition;

    public Turret() {
        this.turretMotor = new TalonSRX(Constants.TURRET_MOTOR);
        this.leftLimit = new DigitalInput(Constants.TURRET_LEFT_LIMIT);
        this.rightLimit = new DigitalInput(Constants.TURRET_RIGHT_LIMIT);

        turretMotor.configFactoryDefault();

        turretMotor.setNeutralMode(NeutralMode.Brake);

        turretMotor.enableVoltageCompensation(true);
        turretMotor.configVoltageCompSaturation(12.5);

        turretMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

        //TODO: add this back in case hardware switch breaks (+1 degree from switch)

        // turretMotor.configForwardSoftLimitEnable(true);
        // turretMotor.configForwardSoftLimitThreshold((int) Math.round(Constants.TURRET_SOFT_LIMIT_FORWARD / Constants.TURRET_DEGREES_PER_PULSE));

        // turretMotor.configReverseSoftLimitEnable(true);
        // turretMotor.configReverseSoftLimitThreshold((int) Math.round(Constants.TURRET_SOFT_LIMIT_REVERSE / Constants.TURRET_DEGREES_PER_PULSE));

        turretMotor.setInverted(false);
        turretMotor.setSelectedSensorPosition(0);
        turretMotor.setSensorPhase(false);

        turretMotor.configPeakOutputForward(Constants.TURRET_MAX_SPEED);
        turretMotor.configPeakOutputReverse(-Constants.TURRET_MAX_SPEED);
        turretMotor.configNominalOutputForward(Constants.TURRET_MIN_SPEED);
        turretMotor.configNominalOutputReverse(-Constants.TURRET_MIN_SPEED);

        turretMotor.configAllowableClosedloopError(0, (int) Constants.TURRET_DEGREE_MARGIN);

        turretMotor.config_kP(0, Constants.TURRET_P);
        turretMotor.config_kI(0, Constants.TURRET_I);
        turretMotor.config_kD(0, Constants.TURRET_D);
        turretMotor.config_kF(0, Constants.TURRET_F);

    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Turret Position", getPosition());
        SmartDashboard.putBoolean("Turret Left Limit", getLeftLimit());
        SmartDashboard.putBoolean("Turret Right Limit", getRightLimit());

        switch(state) {
            case kToPosition:
                set(ControlMode.Position, toPosition / Constants.TURRET_DEGREES_PER_PULSE);
                break;
            case kTurnHold:
                toPosition = getPosition() + RobotContainer.limelight.getTargetAngleX();
                set(ControlMode.Position, toPosition / Constants.TURRET_DEGREES_PER_PULSE);
                break;
            case kHoming:
                set(ControlMode.PercentOutput, -0.25);
                if(getLeftLimit()) {
                    setEncoder(Constants.TURRET_HOMING_POSITION);
                    state = State.kDefault;
                }
            case kDefault:
            default:
                toPosition = isLeftShot() ? -90 : 0;
                set(ControlMode.Position, (toPosition / Constants.TURRET_DEGREES_PER_PULSE));
                break;
        }
    }

    public boolean isLeftShot() {
        return isLeftShot;
    }

    public void toggleLeftShot() {
        isLeftShot = !isLeftShot;
    }

    /**
     * @return The position of the turret in degrees
     */
    public double getPosition() {
        return (turretMotor.getSelectedSensorPosition() * Constants.TURRET_DEGREES_PER_PULSE);
    }

    /**
     * Set the output of the turret motor
     *
     * @param mode  The control mode to use
     * @param value The value to output
     */
    public void set(ControlMode mode, double value) {
        if (getLeftLimit()) {
            if (mode == ControlMode.PercentOutput && value < 0) {
                turretMotor.set(ControlMode.PercentOutput, 0);
                return;
            } else if (mode == ControlMode.Position && value < turretMotor.getSelectedSensorPosition()) {
                turretMotor.set(ControlMode.PercentOutput, 0);
                return;
            }
        }
        if (getRightLimit()) {
            if (mode == ControlMode.PercentOutput && value > 0) {
                turretMotor.set(ControlMode.PercentOutput, 0);
                return;
            } else if (mode == ControlMode.Position && value > turretMotor.getSelectedSensorPosition()) {
                turretMotor.set(ControlMode.PercentOutput, 0);
                return;
            }
        }

        turretMotor.set(mode, value);
    }

    /**
     * Set the position of the encoder
     *
     * @param value The position to set
     */
    public void setEncoder(int value) {
        turretMotor.setSelectedSensorPosition(value);
    }

    /**
     * @return is the left limit switch pressed
     */
    public boolean getLeftLimit() {
        return !leftLimit.get();
    }

    /**
     * @return is the right limit switch pressed
     */
    public boolean getRightLimit() {
        return !rightLimit.get();
    }

    public boolean isOnTarget() {
        return Math.abs(turretMotor.getClosedLoopError()) <= Constants.TURRET_DEGREE_MARGIN;
    }

    public void setStateDefault() {
        state = State.kDefault;
    }

    public void setStateToPosition(double position) {
        toPosition = position;
        state = State.kToPosition;
    }

    public void setStateTurnHold() {
        state = State.kTurnHold;
    }

    public void setStateHoming() {
        state = State.kHoming;
    }
}
