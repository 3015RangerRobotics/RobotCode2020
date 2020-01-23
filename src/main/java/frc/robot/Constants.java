/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

public final class Constants {
    //CTRE CAN Motor Cotnroller ID's
    public static final int rightDriveMaster = 1;
    public static final int rightDriveFollower = 2;
    public static final int leftDriveMaster = 3;
    public static final int leftDriveFollower = 4;
    public static final int turretMotor = 5;

    //PWM Motor Controllers
    public static final int BALLHANDLER_MOTOR1 = 0; // Motor closet to turret
    public static final int BALLHANDLER_MOTOR2 = 1; // Secound motor closet to turret
    public static final int BALLHANDLER_MOTOR3 = 2; // third motor closet to turret
    public static final int BALLHANDLER_MOTOR4 = 3; // fourth motor closet to turret
    public static final int BALLHANDLER_MOTOR5 = 4; // fifth motor closet to turret

    //DIO channels
    public static final int BALLHANDLER_SWITCH1 = 0; // Switch closet to turret
    public static final int BALLHANDLER_SWITCH2 = 1; // Second switch closet to turret
    public static final int BALLHANDLER_SWITCH3 = 2; // Third switch closet to turret
    public static final int BALLHANDLER_SWITCH4 = 3; // Fourth switch closet to turret
    public static final int BALLHANDLER_SWITCH5 = 4; // Fifth switch closet to turret

    //Solenoid channels
    public static final int BALLHANDLER_SOLENOID_FWD = 0;
    public static final int BALLHANDLER_SOLENOID_REV = 1;

    public static final double BALLHANDLER_MOTOR_IN_SPEED1 = .75; //.75
    public static final double BALLHANDLER_MOTOR_IN_SPEED2 = .68;  //.68
    public static final double BALLHANDLER_MOTOR_IN_SPEED3 = .62;  //.62
    public static final double BALLHANDLER_MOTOR_IN_SPEED4 = .55;  //.55
    public static final double BALLHANDLER_MOTOR_IN_SPEED5 = .5;   //.5

    public static final double BALLHANDLER_MOTOR_OUT_SPEED1 = -.5; 
    public static final double BALLHANDLER_MOTOR_OUT_SPEED2 = -.55;
    public static final double BALLHANDLER_MOTOR_OUT_SPEED3 = -.62;
    public static final double BALLHANDLER_MOTOR_OUT_SPEED4 = -.68;
    public static final double BALLHANDLER_MOTOR_OUT_SPEED5 = -.75;

    public static final double BALLHANDLER_MOTOR_SHOOT_SPEED = 1.0;
    public static final double BALLHANDLER_MOTOR_OFF_SPEED = 0.0;

    public static final double driveP = 0;
    public static final double driveI = 0;
    public static final double driveD = 0;
    public static final double driveF = 0;
    public static final double drivePulsesPerFoot = 1;
    public static final double driveMaxVelocity = 12;
    public static final double driveMaxAcceleration = 8;
    public static final double driveMaxMotionError = 0.1 * drivePulsesPerFoot;
    public static final int timeStep = 10;
    
    public static final double turretP = 2.5; // 0.3
    public static final double turretI = 0; // 0
    public static final double turretD = 75; // 0
    public static final double turretF = 0; // 0

    public static final double degreesPerPulse = 1 / (5600.0 / 90.0);
    public static final int turretSoftLimitForward = 90;
    public static final int turretSoftLimitReverse = -90;
    public static final double turretMaxSpeed = 0.7;
    public static final double turretMinSpeed = 0.15;
    public static final double turretDegreeMargin = Math.round((1 / Constants.degreesPerPulse) * 0.25);
    
    public static final int spinningShooter = 5;

    public static final double shooterP = 0;
    public static final double shooterI = 0; // 0
    public static final double shooterD = 0; // 0
    public static final double shooterF = 0; // 0

    public static final double shooterPulsesPerRotation = 2048;
}
