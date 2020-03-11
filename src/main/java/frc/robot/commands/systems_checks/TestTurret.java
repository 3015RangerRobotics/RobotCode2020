package frc.robot.commands.systems_checks;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;

public class TestTurret extends CommandBase {
    Timer timer = new Timer();
    boolean motorChecked = false;
    boolean rightLimitChecked = false;
    boolean leftLimitChecked = false;


    public TestTurret() {
        addRequirements(RobotContainer.turret, RobotContainer.intake);
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
        motorChecked = false;
        rightLimitChecked = false;
        leftLimitChecked = false;
        Robot.turretMotor.setBoolean(false);
        Robot.turretRightLimit.setBoolean(false);
        Robot.turretLeftLimit.setBoolean(false);
        Robot.turretEncoder.setBoolean(false);
        RobotContainer.turret.setStateTesting();
        RobotContainer.intake.intakeDown();
    }

    @Override
    public void execute() {
        if(!rightLimitChecked){
            RobotContainer.turret.set(ControlMode.PercentOutput, 0.40);
            if(RobotContainer.turret.getRightLimit()){
                rightLimitChecked = true;
                Robot.turretRightLimit.setBoolean(true);
                RobotContainer.turret.setEncoder(Constants.TURRET_HOMING_POSITION_RIGHT);
            }
        }else if(!leftLimitChecked){
            RobotContainer.turret.set(ControlMode.PercentOutput, -0.40);
            if(RobotContainer.turret.getLeftLimit()) {
                leftLimitChecked = true;
                Robot.turretLeftLimit.setBoolean(true);
                Robot.turretEncoder.setBoolean(RobotContainer.turret.getPosition() <= -90);
            }
        }

        if (timer.hasElapsed(0.1) && !motorChecked){
            Robot.turretMotor.setBoolean(Robot.pdp.getCurrent(13) >= 2);
            motorChecked = true;
        }
    }

    @Override
    public void end(boolean interrupted) {
        RobotContainer.turret.setStateDefault();
    }

    @Override
    public boolean isFinished() {
        return motorChecked && leftLimitChecked && rightLimitChecked;
    }
}
