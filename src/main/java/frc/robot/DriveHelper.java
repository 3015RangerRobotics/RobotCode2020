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
    private static final double kDeadband = 0.02;
    private static final double kTurnSensitivity = 1.0;

    private static double quickStopAccumulator = 0.0;

    /**
     * Tank drive helper
     *
     * @param left
     * @param right
     * @return Outputs for left and right motors
     */
    public static DriveSignal tankDrive(double left, double right) {
        return new DriveSignal(left, right);
    }

    /**
     * Arcade Drive
     *
     * @param moveValue
     * @param rotateValue
     * @param squaredInputs
     * @return
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
     * @param deadzone The deadzone
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
