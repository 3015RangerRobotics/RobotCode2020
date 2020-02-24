package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Limelight;

public class ShooterStop extends CommandBase {
    public ShooterStop() {
        addRequirements(RobotContainer.shooter);
    }

    @Override
    public void initialize() {
        RobotContainer.limelight.setLEDMode(Limelight.LEDMode.LED_OFF);
        RobotContainer.shooter.set(ControlMode.PercentOutput, 0);
    }

    @Override
    public void execute() {
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
