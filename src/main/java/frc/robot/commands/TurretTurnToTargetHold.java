package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;

import frc.robot.RobotContainer;

public class TurretTurnToTargetHold extends CommandBase {
    public TurretTurnToTargetHold() {
        addRequirements(RobotContainer.turret);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        double pos = RobotContainer.turret.getPosition() + RobotContainer.limelight.getTargetAngleX();
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