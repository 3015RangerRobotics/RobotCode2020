/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

public final class Constants {

    public static final int DRIVE_RIGHT_MASTER = 1;
    public static final int DRIVE_RIGHT_FOLLOWER = 2;
    public static final int DRIVE_LEFT_MASTER = 3;
    public static final int DRIVE_LEFT_FOLLOWER = 4;
    public static final double DRIVE_P = 0;
    public static final double DRIVE_I = 0;
    public static final double DRIVE_D = 0;
    public static final double DRIVE_F = 0;
    public static final double DRIVE_PULSES_PER_FOOT = 14159.2386107;
    public static final double DRIVE_MAX_VELOCITY = 12;
    public static final double DRIVE_MAX_ACCELERATION = 8;
    public static final double DRIVE_MAX_MOTION_ERROR = 0.1 * DRIVE_PULSES_PER_FOOT;
    public static final int DRIVE_TIME_STEP = 10;

    public static final int TURRET_MOTOR = 6;
    public static final double TURRET_P = 2.5; // 0.3
    public static final double TURRET_I = 0; // 0
    public static final double TURRET_D = 75; // 0
    public static final double TURRET_F = 0; // 0
    public static final double TURRET_DEGREES_PER_PULSE = 1 / (5600.0 / 90.0);
    public static final int TURRET_SOFT_LIMIT_FORWARD = 90;
    public static final int TURRET_SOFT_LIMIT_REVERSE = -90;
    public static final double TURRET_MAX_SPEED = 0.7;
    public static final double TURRET_MIN_SPEED = 0.15;
    public static final double TURRET_DEGREE_MARGIN = Math.round((1 / Constants.TURRET_DEGREES_PER_PULSE) * 0.25);
    public static final int TURRET_LEFT_LIMIT = 0;
    public static final int TURRET_RIGHT_LIMIT = 1;
    public static final int TURRET_HOMING_POSITION = (int) Math.round(-90 / TURRET_DEGREES_PER_PULSE);
    
    public static final int SHOOTER_MOTOR = 5;
    public static final double SHOOTER_P = 0;
    public static final double SHOOTER_I = 0; // 0
    public static final double SHOOTER_D = 0; // 0
    public static final double SHOOTER_F = 0.06; // 0
    public static final double SHOOTER_PULSES_PER_ROTATION = 3864.3712;
}
