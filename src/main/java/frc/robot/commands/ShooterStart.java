package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class ShooterStart extends CommandBase {
    double rpm;

    public ShooterStart(double rpm) {
        addRequirements(RobotContainer.shooter);
        this.rpm = rpm;
    }

    @Override
    public void initialize() {
        RobotContainer.shooter.setRampRate(true);
    }

    @Override
    public void execute() {
        RobotContainer.shooter.set(ControlMode.Velocity, rpm / 10 / 60 * Constants.SHOOTER_PULSES_PER_ROTATION);
//        System.out.println("shooter," + rpm + "," + RobotContainer.shooter.getRPM());
    }

    @Override
    public void end(boolean interrupted) {
        RobotContainer.shooter.set(ControlMode.PercentOutput, 0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
