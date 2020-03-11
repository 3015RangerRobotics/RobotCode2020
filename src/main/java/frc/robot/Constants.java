/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

public final class Constants {
    public static final double MP_TIME_STEP = 0.01;

    public static final double LIMELIGHT_INNER_DISTANCE = 0;
    public static final double LIMELIGHT_ZOOM_DISTANCE = 50;

    // Drive Constants
    public static final int DRIVE_RIGHT_MASTER = 1;
    public static final int DRIVE_RIGHT_FOLLOWER = 2;
    public static final int DRIVE_LEFT_MASTER = 3;
    public static final int DRIVE_LEFT_FOLLOWER = 4;
    public static final double DRIVE_PIGEON_UNITS_PER_DEGREE = 8192.0 / 360.0;
    public static final double DRIVE_P = 0.01;//.005;
    public static final double DRIVE_I = 0;//0
    public static final double DRIVE_D = 0;//13.724;
    public static final double DRIVE_F = 0;//0
    public static final double DRIVE_TURN_P = 0.3; //0.3
    public static final double DRIVE_TURN_I = 0.0035; // 0.004
    public static final double DRIVE_TURN_D = 0;
    public static final double DRIVE_TURN_F = 0;//1023.0 / (50 * DRIVE_PIGEON_UNITS_PER_DEGREE);
    public static final double DRIVE_TURN_I_ZONE = 10 * DRIVE_PIGEON_UNITS_PER_DEGREE;
    public static final double DRIVE_MP_TURN_P = 0.656; // 2.4 // 1.1 // 0.937 * 0.784
    public static final double DRIVE_MP_TURN_I = 0.0001;
    public static final double DRIVE_MP_TURN_D = 0; // 5
    public static final double DRIVE_MP_TURN_F = 0;
    public static final double DRIVE_KV = 1.0/28500;//22600
    public static final double DRIVE_KA = 0.0000025;
    public static final double DRIVE_PULSES_PER_FOOT = 14159.2386107;
    public static final double DRIVE_MAX_VELOCITY = 12 * DRIVE_PULSES_PER_FOOT;
    public static final double DRIVE_MAX_ACCELERATION = 8 * DRIVE_PULSES_PER_FOOT;
    public static final double DRIVE_MAX_MOTION_ERROR = 0.1 * DRIVE_PULSES_PER_FOOT;
    public static final double DRIVE_TURN_MAX_VELOCITY = 360 * DRIVE_PIGEON_UNITS_PER_DEGREE / 10;
    public static final double DRIVE_TURN_MAX_ACCELLERTAION = 90 * DRIVE_PIGEON_UNITS_PER_DEGREE / 10;
    public static final double DRIVE_TURN_ERROR = 0.5 * DRIVE_PIGEON_UNITS_PER_DEGREE;
    public static final double DRIVE_MIN_OUTPUT = 0.15;
    public static final int DRIVE_PIGEON = 1;
    public static final double DRIVE_NEUTRAL_DEADBAND = 0.001;

    // Turret Constants
    public static final int TURRET_MOTOR = 6;
    public static final double TURRET_P = 0.8; // 1.5  2
    public static final double TURRET_I = 0; // 0
    public static final double TURRET_D = 20; // 70  190
    public static final double TURRET_F = 0; // 0
    public static final double TURRET_DEGREES_PER_PULSE = 1 / (5600.0 / 90.0);
    public static final double TURRET_MAX_SPEED = 0.7;
    public static final double TURRET_MIN_SPEED = 0.1;
    public static final double TURRET_DEGREE_MARGIN = Math.round((1 / Constants.TURRET_DEGREES_PER_PULSE) * 1);
    public static final int TURRET_LEFT_LIMIT = 1;
    public static final int TURRET_RIGHT_LIMIT = 0;
//    public static final int TURRET_HOMING_POSITION = (int) Math.round(-110 / TURRET_DEGREES_PER_PULSE); // Left Limit
    public static final int TURRET_HOMING_POSITION = (int) Math.round(52 / TURRET_DEGREES_PER_PULSE); // Right Limit
    // Shooter Constants
    public static final int SHOOTER_MOTOR = 5;
    public static final double SHOOTER_P = .5;//3.7;//3.7
    public static final double SHOOTER_I = 0; // 0
    public static final double SHOOTER_D = 10; // 46.0
    public static final double SHOOTER_F = 0.0468; //0.0472;
    public static final double SHOOTER_PULSES_PER_ROTATION = 2048 * (2.0/3.0);
    public static final double SHOOTER_SHOOT_P = 25;//20
    public static final double SHOOTER_SHOOT_I = 0;
    public static final double SHOOTER_SHOOT_D = 60;//50 //70
    public static final double SHOOTER_SHOOT_F = SHOOTER_F * 1;
    public static final double SHOOTER_LAUNCH_ANGLE = 53.28;
    public static final double SHOOTER_TOLERANCE = 25;

    // Ball Handler Constants
    public static final int HANDLER_MOTOR1 = 2;
    public static final int HANDLER_MOTOR2 = 7;
    public static final int HANDLER_MOTOR3 = 6;
    public static final int HANDLER_MOTOR4 = 5;
    public static final int HANDLER_MOTOR5 = 4;
    public static final int HANDLER_SWITCH1 = 2;
    public static final int HANDLER_SWITCH2 = 3;
    public static final int HANDLER_SWITCH3 = 4;
    public static final int HANDLER_SWITCH4 = 5;
    public static final int HANDLER_SWITCH5 = 6;
    public static final int BALL_PRESSURE_SENSOR = 0; // Placeholder
    public static final double HANDLER_MOTOR_IN_SPEED1 = 0.3;
    public static final double HANDLER_MOTOR_IN_SPEED2 = 0.8;
    public static final double HANDLER_MOTOR_IN_SPEED3 = 0.7;
    public static final double HANDLER_MOTOR_IN_SPEED4 = 0.6;
    public static final double HANDLER_MOTOR_IN_SPEED5 = 0.5;//.75
    public static final double HANDLER_MOTOR_OUT_SPEED1 = -0.5;
    public static final double HANDLER_MOTOR_OUT_SPEED2 = -0.55;
    public static final double HANDLER_MOTOR_OUT_SPEED3 = -0.62;
    public static final double HANDLER_MOTOR_OUT_SPEED4 = -0.68;
    public static final double HANDLER_MOTOR_OUT_SPEED5 = -0.75;
    public static final double HANDLER_MOTOR_SHOOT_SPEED1 = 1.0;
    public static final double HANDLER_MOTOR_SHOOT_SPEED2 = 1;//.6 //.8
    public static final double HANDLER_MOTOR_SHOOT_SPEED3 = 1;//.4 //.6
    public static final double HANDLER_MOTOR_SHOOT_SPEED4 = 1;//.4
    public static final double HANDLER_MOTOR_SHOOT_SPEED5 = 1;//.4
    public static final double HANDLER_MOTOR_OFF_SPEED = 0.0;

    // Harvester Constants
    public static final int HARVESTER_MOTOR = 3;
    public static final int HARVESTER_SOLENOID_FORWARD = 0;
    public static final int HARVESTER_SOLENOID_REVERSE = 6;

    //Hood Constants
    public static final int HOOD_SOLENOID_FORWARD = 1;
    public static final int HOOD_SOLENOID_REVERSE = 7;

    //Climber Constants
    public static final int CLIMBER_MOTOR = 7;
    public static final int CLIMBER_LATCH_RELEASE1 = 2;
    public static final int CLIMBER_LATCH_RELEASE2 = 5;
    public static final double CLIMB_UP_SPEED = 1;

    // Limelight Constants
    public static final double LL_TARGET_HEIGHT = 7.58;
    public static final double LL_MOUNT_HEIGHT = 1.75; //TODO: Update this if redoing speed calculation, it is currently wrong
    public static final double LL_MOUNT_ANGLE = 20;

    public static final int PRESSURE_SENSOR = 0;
}
