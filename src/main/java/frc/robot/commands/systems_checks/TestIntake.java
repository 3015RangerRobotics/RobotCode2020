package frc.robot.commands.systems_checks;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class TestIntake extends CommandBase {
    Timer timer = new Timer();
    boolean motorsChecked = false;
    boolean intakeDownChecked = false;
    boolean intakeUpChecked = false;

    public TestIntake() {
        addRequirements(RobotContainer.intake);
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
        motorsChecked = false;
        intakeDownChecked = false;
        intakeUpChecked = false;
        SystemCheckLayout.intakeMotor.setBoolean(false);
        SystemCheckLayout.intakeUp.setBoolean(false);
        SystemCheckLayout.intakeDown.setBoolean(false);
    }

    @Override
    public void execute() {
        if (!timer.hasElapsed(0.75)){
            RobotContainer.intake.intakeDown();
        }else if(!intakeDownChecked){
            SystemCheckLayout.intakeDown.setBoolean(true);
            intakeDownChecked = true;
        }else if(!timer.hasElapsed(1.5)) {
            RobotContainer.intake.intakeSet(-1);
        }else if(!motorsChecked){
            SystemCheckLayout.intakeMotor.setBoolean(RobotContainer.pdp.getCurrent(3) >= 5);
            motorsChecked = true;
            RobotContainer.intake.intakeStop();
        }else if(!timer.hasElapsed(2.25)){
            RobotContainer.intake.intakeUp();
        }else if(!intakeUpChecked){
            SystemCheckLayout.intakeUp.setBoolean(true);
            intakeUpChecked = true;
        }
    }

    @Override
    public void end(boolean interrupted) {
        RobotContainer.intake.intakeStop();
    }

    @Override
    public boolean isFinished() {
        return motorsChecked && intakeUpChecked && intakeDownChecked;
    }
}
