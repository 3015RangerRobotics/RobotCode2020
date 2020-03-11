package frc.robot;

public final class Constants {
    public static final double MP_TIME_STEP = 0.01;

    // Drive Constants
    public static final int DRIVE_RIGHT_MASTER = 1; // CAN: TALON FX
    public static final int DRIVE_RIGHT_FOLLOWER = 2; // CAN: TALON FX
    public static final int DRIVE_LEFT_MASTER = 3; // CAN: TALON FX
    public static final int DRIVE_LEFT_FOLLOWER = 4; // CAN: TALON FX
    public static final int DRIVE_PIGEON = 1; // CAN: PIGEON
    public static final double DRIVE_PIGEON_UNITS_PER_DEGREE = 8192.0 / 360.0;
    public static final double DRIVE_P = 0.01;
    public static final double DRIVE_I = 0;
    public static final double DRIVE_D = 0;
    public static final double DRIVE_F = 0;
    public static final double DRIVE_MP_TURN_P = 0.656;
    public static final double DRIVE_MP_TURN_I = 0.0001;
    public static final double DRIVE_MP_TURN_D = 0;
    public static final double DRIVE_MP_TURN_F = 0;
    public static final double DRIVE_KV = 1.0/28500;
    public static final double DRIVE_KA = 0.0000025;
    public static final double DRIVE_TURN_P = 0.3;
    public static final double DRIVE_TURN_I = 0.0035;
    public static final double DRIVE_TURN_D = 0;
    public static final double DRIVE_TURN_F = 0;
    public static final double DRIVE_TURN_I_ZONE = 10 * DRIVE_PIGEON_UNITS_PER_DEGREE;
    public static final double DRIVE_PULSES_PER_FOOT = 14159.2386107;
    public static final double DRIVE_TURN_MAX_VELOCITY = 360 * DRIVE_PIGEON_UNITS_PER_DEGREE / 10;
    public static final double DRIVE_TURN_MAX_ACCELERATION = 90 * DRIVE_PIGEON_UNITS_PER_DEGREE / 10;
    public static final double DRIVE_TURN_ERROR = 0.5 * DRIVE_PIGEON_UNITS_PER_DEGREE;
    public static final double DRIVE_NEUTRAL_DEADBAND = 0.001;

    // Turret Constants
    public static final int TURRET_MOTOR = 6; // CAN: TALON SRX
    public static final int TURRET_LEFT_LIMIT = 1; // DIO: LIMIT SWITCH
    public static final int TURRET_RIGHT_LIMIT = 0; // DIO: LIMIT SWITCH
    public static final double TURRET_P = 0.8;
    public static final double TURRET_I = 0;
    public static final double TURRET_D = 20;
    public static final double TURRET_F = 0;
    public static final double TURRET_DEGREES_PER_PULSE = 1 / (5600.0 / 90.0);
    public static final double TURRET_MAX_SPEED = 0.7;
    public static final double TURRET_MIN_SPEED = 0.1;
    public static final double TURRET_ALLOWABLE_ERROR = Math.round((1 / Constants.TURRET_DEGREES_PER_PULSE) * 1);
    public static final int TURRET_HOMING_POSITION_LEFT = (int) Math.round(-110 / TURRET_DEGREES_PER_PULSE);
    public static final int TURRET_HOMING_POSITION_RIGHT = (int) Math.round(52 / TURRET_DEGREES_PER_PULSE);

    // Shooter Constants
    public static final int SHOOTER_MOTOR = 5; // CAN: TALON FX
    public static final double SHOOTER_P = .5;
    public static final double SHOOTER_I = 0;
    public static final double SHOOTER_D = 10;
    public static final double SHOOTER_F = 0.0468;
    public static final double SHOOTER_PULSES_PER_ROTATION = 2048 * (2.0/3.0);
    public static final double SHOOTER_SHOOT_P = 25;
    public static final double SHOOTER_SHOOT_I = 0;
    public static final double SHOOTER_SHOOT_D = 60;
    public static final double SHOOTER_SHOOT_F = SHOOTER_F;
    public static final double SHOOTER_LAUNCH_ANGLE = 53.28;
    public static final double SHOOTER_TOLERANCE = 25;
    public static final double SHOOTER_BALL_QUALITY_FACTOR = 1.0;

    // Carousel Constants
    public static final int CAROUSEL_MOTOR1 = 2; // PWM: VICTOR SP
    public static final int CAROUSEL_MOTOR2 = 7; // PWM: VICTOR SP
    public static final int CAROUSEL_MOTOR3 = 6; // PWM: VICTOR SP
    public static final int CAROUSEL_MOTOR4 = 5; // PWM: VICTOR SP
    public static final int CAROUSEL_MOTOR5 = 4; // PWM: VICTOR SP
    public static final int CAROUSEL_BALL_SENSOR1 = 2; // DIO: BEAM BREAK
    public static final int CAROUSEL_BALL_SENSOR2 = 3; // DIO: BEAM BREAK
    public static final int CAROUSEL_BALL_SENSOR3 = 4; // DIO: BEAM BREAK
    public static final int CAROUSEL_BALL_SENSOR4 = 5; // DIO: BEAM BREAK
    public static final int CAROUSEL_BALL_SENSOR5 = 6; // DIO: BEAM BREAK
    public static final double CAROUSEL_IN_SPEED_ACTIVE = 0.3;
    public static final double CAROUSEL_IN_SPEED_PASSIVE = 1.0;
    public static final double CAROUSEL_PURGE_SPEED1 = -0.6;
    public static final double CAROUSEL_PURGE_SPEED2 = -0.7;
    public static final double CAROUSEL_PURGE_SPEED3 = -0.8;
    public static final double CAROUSEL_PURGE_SPEED4 = -0.9;
    public static final double CAROUSEL_PURGE_SPEED5 = -1.0;
    public static final double CAROUSEL_SHOOT_SPEED = 1.0;

    // Intake Constants
    public static final int INTAKE_MOTOR = 3; // PWM: VICTOR SP
    public static final int INTAKE_SOLENOID_FORWARD = 0; // SOLENOID
    public static final int INTAKE_SOLENOID_REVERSE = 6; // SOLENOID

    //Hood Constants
    public static final int HOOD_SOLENOID_FORWARD = 1; // SOLENOID
    public static final int HOOD_SOLENOID_REVERSE = 7; // SOLENOID

    //Climber Constants
    public static final int CLIMBER_MOTOR = 7; // CAN: TALON FX
    public static final int CLIMBER_LATCH_RELEASE1 = 2; // SOLENOID
    public static final int CLIMBER_LATCH_RELEASE2 = 5; // SOLENOID
    public static final double CLIMB_UP_SPEED = 1.0;

    // Limelight Constants
    public static final double LL_TARGET_HEIGHT = 7.58;
    public static final double LL_MOUNT_HEIGHT = 1.75; //TODO: Update this if redoing speed calculation, it is currently wrong
    public static final double LL_MOUNT_ANGLE = 20;

    // Compressor Constants
    public static final int PRESSURE_SENSOR = 0; // AIO: POTENTIOMETER
}
