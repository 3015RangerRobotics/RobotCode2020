package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class DriverRumbleOff extends CommandBase {
    public DriverRumbleOff() {
        addRequirements(RobotContainer.driverRumble);
    }

    @Override
    public void initialize() {
        RobotContainer.setDriverRumbleLeft(0.0);
        RobotContainer.setDriverRumbleRight(0.0);
        RobotContainer.setCoDriverRumbleLeft(0.0);
        RobotContainer.setCoDriverRumbleRight(0.0);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
