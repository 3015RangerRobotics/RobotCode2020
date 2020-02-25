package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class TurretToPosition extends CommandBase {
    public double angle;

    /**
     * Command to turn the turret to a specific angle
     * @param angle The angle to turn to
     */
    public TurretToPosition(double angle) {
        addRequirements(RobotContainer.turret);
        this.angle = angle;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
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
