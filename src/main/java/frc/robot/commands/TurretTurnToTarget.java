/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;

import frc.robot.RobotContainer;

public class TurretTurnToTarget extends CommandBase {
    /**
     * Creates a new TurretTurnToTarget.
     */

    private double limelight;

    public TurretTurnToTarget() {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(RobotContainer.turret);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        limelight = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double pos = RobotContainer.turret.getMotorPosition() + NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
        RobotContainer.turret.set(ControlMode.Position, pos / Constants.degreesPerPulse);
        System.out.println(("Current Pos: " + RobotContainer.turret.getMotorPosition() + " Turn To: " + pos));
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        RobotContainer.turret.set(ControlMode.PercentOutput, 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}