package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class TurretToDefaultPosition extends CommandBase {

    public TurretToDefaultPosition() {
        addRequirements(RobotContainer.turret);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        double angle = RobotContainer.turret.isLeftShot() ? -90 : 0;
        RobotContainer.turret.set(ControlMode.Position, (angle / Constants.TURRET_DEGREES_PER_PULSE));
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
