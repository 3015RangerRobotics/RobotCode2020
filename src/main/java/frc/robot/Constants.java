/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

public final class Constants {
    public static final int exampleTalon = 5;
    public static final int rightDriveMaster = 1;
    public static final int rightDriveFollower = 2;
    public static final int leftDriveMaster = 3;
    public static final int leftDriveFollower = 4;
    public static final int turretMotor = 1;

    public static final double driveP = 0;
    public static final double driveI = 0;
    public static final double driveD = 0;
    public static final double driveF = 0;
    
    public static final double turretP = 0.3; // 0.1
    public static final double turretI = 0; // 0
    public static final double turretD = 0; // 0.004
    public static final double turretF = 0; // 0

    public static final double degreesPerPulse = 1 / (5600.0 / 90.0);
    public static final int turretSoftLimitForward = 90;
    public static final int turretSoftLimitReverse = -90;
    public static final double turretMaxSpeed = 0.7;
    public static final double turretMinSpeed = 0.15;
    public static final double turretDegreeMargin = 0.1;
    
    public static final int spinningShooter = 1;
}
