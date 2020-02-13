/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

public final class Constants {
    public static final double MP_TIME_STEP = 0.01;

    // Drive Constants
    public static final int DRIVE_RIGHT_MASTER = 1;
    public static final int DRIVE_RIGHT_FOLLOWER = 2;
    public static final int DRIVE_LEFT_MASTER = 3;
    public static final int DRIVE_LEFT_FOLLOWER = 4;
    public static final double DRIVE_P = 0.005;//.005;
    public static final double DRIVE_P_TURN = 0.015;
    public static final double DRIVE_I = 0;
    public static final double DRIVE_D = 13.724;//1;
    public static final double DRIVE_F = 1023.0/22600;
    public static final double DRIVE_PULSES_PER_FOOT = 14159.2386107;
    public static final double DRIVE_MAX_VELOCITY = 8 * DRIVE_PULSES_PER_FOOT;
    public static final double DRIVE_MAX_ACCELERATION = 6 * DRIVE_PULSES_PER_FOOT;
    public static final double DRIVE_MAX_MOTION_ERROR = 0.1 * DRIVE_PULSES_PER_FOOT;

    // Turret Constants
    public static final int TURRET_MOTOR = 6;
    public static final double TURRET_P = 2.5; // 0.3
    public static final double TURRET_I = 0; // 0
    public static final double TURRET_D = 75; // 0
    public static final double TURRET_F = 0; // 0
    public static final double TURRET_DEGREES_PER_PULSE = 1 / (5600.0 / 90.0);
    public static final double TURRET_MAX_SPEED = 0.7;
    public static final double TURRET_MIN_SPEED = 0.15;
    public static final double TURRET_DEGREE_MARGIN = Math.round((1 / Constants.TURRET_DEGREES_PER_PULSE) * 0.15);
    public static final int TURRET_LEFT_LIMIT = 1;
    public static final int TURRET_RIGHT_LIMIT = 0;
    public static final int TURRET_HOMING_POSITION = (int) Math.round(-114 / TURRET_DEGREES_PER_PULSE);

    // Shooter Constants
    public static final int SHOOTER_MOTOR = 5;
    public static final double SHOOTER_P = 3.0;//3.7
    public static final double SHOOTER_I = 0; // 0
    public static final double SHOOTER_D = 5.0; // 46.0
    public static final double SHOOTER_F = 0.047; // 0
    public static final double SHOOTER_PULSES_PER_ROTATION = 1024;

    // Ball Handler Constants
    public static final int HANDLER_MOTOR1 = 2;
    public static final int HANDLER_MOTOR2 = 7;
    public static final int HANDLER_MOTOR3 = 6;
    public static final int HANDLER_MOTOR4 = 5;
    public static final int HANDLER_MOTOR5 = 4;
    public static final int HANDLER_SWITCH1 = 3;
    public static final int HANDLER_SWITCH2 = 2;
    public static final int HANDLER_SWITCH3 = 4;
    public static final int HANDLER_SWITCH4 = 5;
    public static final int HANDLER_SWITCH5 = 6;
    public static final double HANDLER_MOTOR_IN_SPEED1 = 0.5;
    public static final double HANDLER_MOTOR_IN_SPEED2 = 0.68;
    public static final double HANDLER_MOTOR_IN_SPEED3 = 0.62;
    public static final double HANDLER_MOTOR_IN_SPEED4 = 0.55;
    public static final double HANDLER_MOTOR_IN_SPEED5 = 0.75;
    public static final double HANDLER_MOTOR_OUT_SPEED1 = -0.5;
    public static final double HANDLER_MOTOR_OUT_SPEED2 = -0.55;
    public static final double HANDLER_MOTOR_OUT_SPEED3 = -0.62;
    public static final double HANDLER_MOTOR_OUT_SPEED4 = -0.68;
    public static final double HANDLER_MOTOR_OUT_SPEED5 = -0.75;
    public static final double HANDLER_MOTOR_SHOOT_SPEED1 = 1.0;
    public static final double HANDLER_MOTOR_SHOOT_SPEED2 = 0.6;
    public static final double HANDLER_MOTOR_SHOOT_SPEED3 = 0.4;
    public static final double HANDLER_MOTOR_SHOOT_SPEED4 = 0.4;
    public static final double HANDLER_MOTOR_SHOOT_SPEED5 = 0.4;
    public static final double HANDLER_MOTOR_OFF_SPEED = 0.0;

    // Harvester Constants
    public static final int HARVESTER_MOTOR = 3;
    public static final int HARVESTER_SOLENOID_FORWARD = 0;
    public static final int HARVESTER_SOLENOID_REVERSE = 6;

    //Hood Constants
    public static final int HOOD_SOLENOID_FORWARD = 1;
    public static final int HOOD_SOLENOID_REVERSE = 7;
}
