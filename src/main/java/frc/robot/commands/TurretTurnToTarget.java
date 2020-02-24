package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;

import frc.robot.RobotContainer;
import frc.robot.subsystems.Limelight;

public class TurretTurnToTarget extends CommandBase {
    private double pos;

    public TurretTurnToTarget() {
        addRequirements(RobotContainer.turret);
    }

    @Override
    public void initialize() {
        pos = RobotContainer.turret.getPosition() + RobotContainer.limelight.getTargetAngleX();
    }

    @Override
    public void execute() {
        RobotContainer.turret.set(ControlMode.Position, pos / Constants.TURRET_DEGREES_PER_PULSE);
    }

    @Override
    public void end(boolean interrupted) {
        RobotContainer.turret.set(ControlMode.PercentOutput, 0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}