/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * Add your docs here.
 */
public class DriveHelper {
    private static final double kDeadband = 0.05;
    private static double quickStopAccumulator = 0.0;
    private static final double kTurnSensitivity = 1.0;


    /**
     * Tank drive helper
     *
     * @param left Left speed
     * @param right Right speed
     * @return Outputs for left and right motors
     */
    public static DriveSignal tankDrive(double left, double right) {
        return new DriveSignal(left, right);
    }

    /**
     * Arcade Drive
     *
     * @param moveValue Forward/Reverse speed
     * @param rotateValue Turn Speed
     * @param squaredInputs Should the inputs be squared (Increase control at low speeds)
     * @return Outputs for left and right motors
     */
    public static DriveSignal arcadeDrive(double moveValue, double rotateValue, boolean squaredInputs) {
        double leftMotorSpeed;
        double rightMotorSpeed;
        moveValue = handleDeadzone(moveValue, kDeadband);
        rotateValue = -handleDeadzone(rotateValue, kDeadband);
        if (squaredInputs) {
            if (moveValue >= 0.0) {
                moveValue = moveValue * moveValue;
            } else {
                moveValue = -(moveValue * moveValue);
            }
            if (rotateValue >= 0.0) {
                rotateValue = rotateValue * rotateValue;
            } else {
                rotateValue = -(rotateValue * rotateValue);
            }
        }
        if (moveValue > 0.0) {
            if (rotateValue > 0.0) {
                leftMotorSpeed = moveValue - rotateValue;
                rightMotorSpeed = Math.max(moveValue, rotateValue);
            } else {
                leftMotorSpeed = Math.max(moveValue, -rotateValue);
                rightMotorSpeed = moveValue + rotateValue;
            }
        } else {
            if (rotateValue > 0.0) {
                leftMotorSpeed = -Math.max(-moveValue, rotateValue);
                rightMotorSpeed = moveValue + rotateValue;
            } else {
                leftMotorSpeed = moveValue - rotateValue;
                rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
            }
        }
        return new DriveSignal(leftMotorSpeed, rightMotorSpeed);
    }
    public static DriveSignal curvatureDrive(double throttle, double turn, boolean isQuickTurn, boolean squaredInputs) {
        throttle = handleDeadzone(throttle, kDeadband);
        turn = handleDeadzone(turn, kDeadband);

        double overPower;
        double angularPower;

        if (squaredInputs) {
            // square the inputs (while preserving the sign) to increase fine control
            // while permitting full power
            if (throttle >= 0.0) {
                throttle = throttle * throttle;
            } else {
                throttle = -(throttle * throttle);
            }
        }

        if (isQuickTurn) {
            if (Math.abs(throttle) < 0.2) {
                double alpha = 0.1;
                quickStopAccumulator = (1 - alpha) * quickStopAccumulator + alpha * limit(turn, 1.0) * 2;
            }
            overPower = 1.0;
            angularPower = turn;
        } else {
            overPower = 0.0;
            angularPower = Math.abs(throttle) * turn * kTurnSensitivity - quickStopAccumulator;
            if (quickStopAccumulator > 1) {
                quickStopAccumulator -= 1;
            } else if (quickStopAccumulator < -1) {
                quickStopAccumulator += 1;
            } else {
                quickStopAccumulator = 0.0;
            }
        }

        double rightPwm = throttle - angularPower;
        double leftPwm = throttle + angularPower;
        if (leftPwm > 1.0) {
            rightPwm -= overPower * (leftPwm - 1.0);
            leftPwm = 1.0;
        } else if (rightPwm > 1.0) {
            leftPwm -= overPower * (rightPwm - 1.0);
            rightPwm = 1.0;
        } else if (leftPwm < -1.0) {
            rightPwm += overPower * (-1.0 - leftPwm);
            leftPwm = -1.0;
        } else if (rightPwm < -1.0) {
            leftPwm += overPower * (-1.0 - rightPwm);
            rightPwm = -1.0;
        }

        return new DriveSignal(leftPwm, rightPwm);
    }

    /**
     * Handles a deadzone
     *
     * @param value    The value to handle
     * @param deadzone The deadzone
     * @return The handled value
     */
    protected static double handleDeadzone(double value, double deadzone) {
        return (Math.abs(value) > Math.abs(deadzone)) ? limit(value, 1.0) : 0.0;
    }

    /**
     * Limits a number between a given range
     *
     * @param value    The value to limit
     * @param max The maximum value
     * @return The handled value
     */
    protected static double limit(double value, double max) {
        if (value > max) {
            return max;
        }
        if (value < -max) {
            return -max;
        }
        return value;
    }
}
