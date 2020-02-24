package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class TurretHoming extends CommandBase {
    public TurretHoming() {
        addRequirements(RobotContainer.turret);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        RobotContainer.turret.set(ControlMode.PercentOutput, -0.25);
    }

    @Override
    public void end(boolean interrupted) {
//        if this is interrupted do not reset the encoder
        if(!interrupted) {
            RobotContainer.turret.setEncoder(Constants.TURRET_HOMING_POSITION);
        }
        RobotContainer.turret.set(ControlMode.PercentOutput, 0);
    }

    @Override
    public boolean isFinished() {
        return RobotContainer.turret.getLeftLimit();

    }
}
