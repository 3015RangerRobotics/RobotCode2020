package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class DriverRumbleOscillate extends CommandBase {
    Timer timer;
    double power;
    double halfPeriod;
    boolean leftOn = true;

    /**
     * Creates a new DriverRumbleOscillate.
     *
     * @param power a value from 0 to 1 representing the power of the active rumble
     * @param period The time required to complete one oscillation
     */
    public DriverRumbleOscillate(double power, double period) {
        addRequirements(RobotContainer.driverRumble);
        this.power = power;
        this.halfPeriod = period / 2.0;
        timer = new Timer();
    }

    @Override
    public void initialize() {
        timer.start();
        timer.reset();
    }

    @Override
    public void execute() {
        if (leftOn) {
            //When the left is on the right is off
            RobotContainer.setDriverRumbleLeft(power);
            RobotContainer.setDriverRumbleRight(0.0);
        } else {
            //When the right is on the left is off
            RobotContainer.setDriverRumbleLeft(0.0);
            RobotContainer.setDriverRumbleRight(power);
        }

        //When the timer is greater than or equal to the halfPeriod it will switch the leftOn and then reset the timer
        if (timer.get() >= halfPeriod) {
            //switchs the leftOn to switch the rumble
            leftOn = !leftOn;
            //resets the timer
            timer.reset();
        }
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
