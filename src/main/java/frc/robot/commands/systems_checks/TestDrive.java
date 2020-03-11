package frc.robot.commands.systems_checks;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotContainer;

public class TestDrive extends CommandBase {
    Timer timer = new Timer();
    boolean motorsChecked = false;
    boolean gyroChecked = false;
    boolean turnedBack = false;

    public TestDrive() {
        addRequirements(RobotContainer.drive);
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
        RobotContainer.drive.resetIMU();
        RobotContainer.drive.resetEncoders();
        motorsChecked = false;
        gyroChecked = false;
        turnedBack = false;
        Robot.driveLeftMaster.setBoolean(false);
        Robot.driveLeftFollower.setBoolean(false);
        Robot.driveRightMaster.setBoolean(false);
        Robot.driveRightFollower.setBoolean(false);
        Robot.drivePosition.setBoolean(false);
        Robot.driveIMU.setBoolean(false);
    }

    @Override
    public void execute() {
        if (!timer.hasElapsed(0.2)){
            RobotContainer.drive.setMotorOutputs(ControlMode.PercentOutput, 0.2, 0.2);
        }else if(!motorsChecked){
            Robot.drivePosition.setBoolean(RobotContainer.drive.getPosition() >= .5);
            Robot.driveLeftMaster.setBoolean(Robot.pdp.getCurrent(14) >= 5);
            Robot.driveLeftFollower.setBoolean(Robot.pdp.getCurrent(15) >= 5);
            Robot.driveRightMaster.setBoolean(Robot.pdp.getCurrent(0) >= 5);
            Robot.driveRightFollower.setBoolean(Robot.pdp.getCurrent(1) >= 5);
            motorsChecked = true;
        }else if(!timer.hasElapsed(.7)){
            RobotContainer.drive.setMotorOutputs(ControlMode.PercentOutput, 0, 0);
        }else if(!timer.hasElapsed(0.95)) {
            RobotContainer.drive.setMotorOutputs(ControlMode.PercentOutput, 0.20, -0.20);
        }else if(!gyroChecked){
            Robot.driveIMU.setBoolean(RobotContainer.drive.getAngle() >= 2);
            gyroChecked = true;
        }else if(!timer.hasElapsed(1.5)) {
            RobotContainer.drive.setMotorOutputs(ControlMode.PercentOutput, 0, 0);
        }else if(!timer.hasElapsed(1.75)) {
            RobotContainer.drive.setMotorOutputs(ControlMode.PercentOutput, -0.20, 0.20);
        }else {
            turnedBack = true;
        }
    }

    @Override
    public void end(boolean interrupted) {
        RobotContainer.drive.setMotorOutputs(ControlMode.PercentOutput, 0, 0);
    }

    @Override
    public boolean isFinished() {
        return motorsChecked && gyroChecked && turnedBack;
    }
}
