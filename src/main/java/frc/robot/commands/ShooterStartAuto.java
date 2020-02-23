/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class ShooterStartAuto extends CommandBase {
    double rpm = 5400;
    boolean constantUpdate = true;

    /**
     * Creates a new ShooterStart.
     */
    public ShooterStartAuto() {
        addRequirements(RobotContainer.shooter);
        // Use addRequirements() here to declare subsystem dependencies.
        constantUpdate = true;
    }

    public ShooterStartAuto(boolean constantUpdate) {
        addRequirements(RobotContainer.shooter);
        // Use addRequirements() here to declare subsystem dependencies.
        this.constantUpdate = constantUpdate;
    }
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        double d = RobotContainer.limelight.getRobotToTargetDistance();
        double turretPos = RobotContainer.turret.getPosition() + RobotContainer.limelight.getTargetAngleX();
        rpm = 4222.866701 + (110.34724*d) + (-1.51320429*d*d) + (1.5 * Math.abs(turretPos)); //Average ball
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double d = RobotContainer.limelight.getRobotToTargetDistance();
        double turretPos = RobotContainer.turret.getPosition() + RobotContainer.limelight.getTargetAngleX();
//        rpm = 7430.1186 + (-255.07933*d) + (7.2472131*d*d) + (1.5 * Math.abs(turretPos)); //Perfect ball
       if (constantUpdate){
           rpm = 4222.866701 + (110.34724*d) + (-1.51320429*d*d) + (1.5 * Math.abs(turretPos)); //Average ball
       }
//        System.out.println("shooter," + rpm + "," + RobotContainer.shooter.getRPM());
        RobotContainer.shooter.set(ControlMode.Velocity,rpm /10 /60 * Constants.SHOOTER_PULSES_PER_ROTATION);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        RobotContainer.shooter.set(ControlMode.PercentOutput, 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return RobotContainer.shooter.getRPM() >= 7500;
    }
}
