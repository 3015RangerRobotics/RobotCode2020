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
public class DriveSignal {

    public double leftSignal;
    public double rightSignal;

    public DriveSignal(double leftSignal, double rightSignal) {
        this.leftSignal = leftSignal;
        this.rightSignal = rightSignal;
    }
}
