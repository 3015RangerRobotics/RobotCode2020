package frc.robot.commands.systems_checks;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotContainer;

public class TestIntake extends CommandBase {
    Timer timer = new Timer();
    boolean motorsChecked = false;
    boolean intakeDownChecked = false;
    boolean intakeUpChecked = false;

    public TestIntake() {
        addRequirements(RobotContainer.harvester);
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
        motorsChecked = false;
        intakeDownChecked = false;
        intakeUpChecked = false;
        Robot.intakeMotor.setBoolean(false);
        Robot.intakeUp.setBoolean(false);
        Robot.intakeDown.setBoolean(false);
    }

    @Override
    public void execute() {
        if (!timer.hasElapsed(0.1)){
            RobotContainer.harvester.harvesterDown();
        }else if(!intakeDownChecked){
            Robot.intakeDown.setBoolean(true);
            intakeDownChecked = true;
        }else if(!timer.hasElapsed(0.6)) {
            RobotContainer.harvester.harvesterSet(-1);
        }else if(!motorsChecked){
            Robot.intakeMotor.setBoolean(Robot.pdp.getCurrent(3) >= 5);
            motorsChecked = true;
            RobotContainer.harvester.harvesterStop();
        }else if(!timer.hasElapsed(.7)){
            RobotContainer.harvester.harvesterUp();
        }else if(!intakeUpChecked){
            Robot.intakeUp.setBoolean(true);
            intakeUpChecked = true;
        }
    }

    @Override
    public void end(boolean interrupted) {
        RobotContainer.harvester.harvesterStop();
    }

    @Override
    public boolean isFinished() {
        return motorsChecked && intakeUpChecked && intakeDownChecked;
    }
}
