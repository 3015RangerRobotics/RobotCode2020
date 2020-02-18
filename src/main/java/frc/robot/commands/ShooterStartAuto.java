/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class ShooterStartAuto extends CommandBase {
    double rpm = 5400;
    double launch = Math.tan(Math.toRadians(53.2));
    double f2m = 0.305;

    /**
     * Creates a new ShooterStart.
     */
    public ShooterStartAuto() {
        addRequirements(RobotContainer.shooter);
        // Use addRequirements() here to declare subsystem dependencies.
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
//        RobotContainer.shooter.setRampRate(true);
//        double d = RobotContainer.limelight.getArea();
//        double turretPos = RobotContainer.turret.getPosition() + RobotContainer.limelight.getTargetAngleX();
//        rpm = 6494.93513 + (-2502.61834*d) + (1063.20913*d*d) + (1.5 * Math.abs(turretPos));
//        rpm *= 1.02;
//        double a = Math.sqrt(9.81 * d * (launch*launch + 1));
//        double b = Math.sqrt(Math.abs(2 * launch - (2 * 9.81 * (6.19*f2m) / d)));
//        double v = a / b;
//        System.out.println("===================" + d);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
//        RobotContainer.shooter.set(ControlMode.PercentOutput, 0.57);

//        System.out.println("shooter," + rpm + "," + RobotContainer.shooter.getRPM());

//        if (RobotContainer.shooter.getRPM() > 4000){
//            RobotContainer.shooter.setRampRate(false);
//        }
        double d = RobotContainer.limelight.getArea();
        double turretPos = RobotContainer.turret.getPosition() + RobotContainer.limelight.getTargetAngleX();
        rpm = 6494.93513 + (-2502.61834*d) + (1063.20913*d*d) + (1.5 * Math.abs(turretPos));

        RobotContainer.shooter.set(ControlMode.Velocity,rpm /10 /60 * Constants.SHOOTER_PULSES_PER_ROTATION);
        System.out.println("===================" + d);
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
