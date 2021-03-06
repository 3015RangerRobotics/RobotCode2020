package frc.robot.commands.systems_checks;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
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
        SystemCheckLayout.turretMotor.setBoolean(false);
        SystemCheckLayout.turretRightLimit.setBoolean(false);
        SystemCheckLayout.turretLeftLimit.setBoolean(false);
        SystemCheckLayout.turretEncoder.setBoolean(false);
        RobotContainer.turret.setStateTesting();
        RobotContainer.intake.intakeDown();
    }

    @Override
    public void execute() {
        if(!rightLimitChecked){
            RobotContainer.turret.set(ControlMode.PercentOutput, 0.40);
            if(RobotContainer.turret.getRightLimit()){
                rightLimitChecked = true;
                SystemCheckLayout.turretRightLimit.setBoolean(true);
                RobotContainer.turret.setEncoder(Constants.TURRET_HOMING_POSITION_RIGHT);
            }
        }else if(!leftLimitChecked){
            RobotContainer.turret.set(ControlMode.PercentOutput, -0.40);
            if(RobotContainer.turret.getLeftLimit()) {
                leftLimitChecked = true;
                SystemCheckLayout.turretLeftLimit.setBoolean(true);
                SystemCheckLayout.turretEncoder.setBoolean(RobotContainer.turret.getPosition() <= -90);
            }
        }

        if (timer.hasElapsed(0.1) && !motorChecked){
            SystemCheckLayout.turretMotor.setBoolean(RobotContainer.pdp.getCurrent(13) >= 2);
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
