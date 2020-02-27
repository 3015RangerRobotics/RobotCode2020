package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class TurretHomePosition extends CommandBase {
    public TurretHomePosition() {
        addRequirements(RobotContainer.turret, RobotContainer.harvester);
    }

    @Override
    public void initialize() {
        RobotContainer.turret.setStateHoming();
        RobotContainer.harvester.harvesterDown();
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
