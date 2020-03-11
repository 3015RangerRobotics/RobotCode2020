package frc.robot.commands.systems_checks;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;

public class TestShooter extends CommandBase {
    Timer timer = new Timer();
    boolean motorChecked;
    boolean encoderChecked;
    boolean pidChecked;

    public TestShooter() {
        addRequirements(RobotContainer.shooter);
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
        motorChecked = false;
        encoderChecked = false;
        pidChecked = false;
        Robot.shooterMotor.setBoolean(false);
        Robot.shooterEncoder.setBoolean(false);
        Robot.shooterPID.setBoolean(false);
        RobotContainer.shooter.setStateTesting();
    }

    @Override
    public void execute() {
        if (!timer.hasElapsed(.1)){
            RobotContainer.shooter.set(ControlMode.PercentOutput, 0.4);
        }else if(!motorChecked){
            Robot.shooterMotor.setBoolean(Robot.pdp.getCurrent(12) >= 5);
            motorChecked = true;
        }else if(!timer.hasElapsed(1.1)){
            RobotContainer.shooter.set(ControlMode.PercentOutput, 0.4);
            System.out.println(RobotContainer.shooter.getRPM());
        }else if(!encoderChecked){
            Robot.shooterEncoder.setBoolean(RobotContainer.shooter.getRPM() >= 2500);
            encoderChecked = true;
        }else if(!timer.hasElapsed(3)){
            RobotContainer.shooter.setStateSpeed(3000);
        }else if(!pidChecked){
//            Robot.shooterPID.setBoolean(Math.abs(RobotContainer.shooter.getRPM() - 5000) <= 100);
            Robot.shooterPID.setBoolean(RobotContainer.shooter.isPrimed());
            pidChecked = true;
        }
    }

    @Override
    public void end(boolean interrupted) {
        RobotContainer.shooter.set(ControlMode.PercentOutput, 0);
        RobotContainer.shooter.setStateOff();
    }

    @Override
    public boolean isFinished() {
        return motorChecked && encoderChecked && pidChecked;
    }
}
