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
import frc.robot.subsystems.Limelight;

public class TurretTurnToTarget extends CommandBase {
    /**
     * Creates a new TurretTurnToTarget.
     */

    public TurretTurnToTarget() {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(RobotContainer.turret, RobotContainer.limelight);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        RobotContainer.limelight.setPipeline(0);
        RobotContainer.limelight.setStreamingMode(Limelight.StreamingMode.STANDARD);
        RobotContainer.limelight.setLEDMode(Limelight.LEDMode.PIPELINE);
        RobotContainer.limelight.setCameraMode(Limelight.CameraMode.VISION_PROCESSING);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double pos = RobotContainer.turret.getMotorPosition() + RobotContainer.limelight.getTargetAngleX();
        RobotContainer.turret.set(ControlMode.Position, pos / Constants.TURRET_DEGREES_PER_PULSE);
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