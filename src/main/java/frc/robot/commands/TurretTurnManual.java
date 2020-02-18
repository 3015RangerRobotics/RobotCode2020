package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class TurretTurnManual extends CommandBase {
    double speed;
    public TurretTurnManual(double speed) {
        if(speed > 0.5){
            this.speed = 0.5;
        }else if(speed < -5){
            this.speed = -0.5;
        }else {
            this.speed = speed;
        }
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(RobotContainer.turret);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        RobotContainer.turret.set(ControlMode.PercentOutput, speed);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
//        if this is interrupted do not reset the encoder
        RobotContainer.turret.set(ControlMode.PercentOutput, 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return true;

    }
}
