package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class DriverRumbleSet extends CommandBase {
    double leftPower;
    double rightPower;

    /**
     * Creates a new DriverRumbleSet.
     *
     * @param leftPower  a value from 0 to 1 representing the power of the leftRumble
     * @param rightPower a value from 0 to 1 representing the power of the rightRumble
     */
    public DriverRumbleSet(double leftPower, double rightPower) {
        addRequirements(RobotContainer.driverRumble);
        this.leftPower = leftPower;
        this.rightPower = rightPower;
    }

    @Override
    public void execute() {
        RobotContainer.setDriverRumbleLeft(leftPower);
        RobotContainer.setDriverRumbleRight(rightPower);
    }

    @Override
    public void end(boolean interrupted) {
        RobotContainer.setDriverRumbleLeft(0.0);
        RobotContainer.setDriverRumbleRight(0.0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
