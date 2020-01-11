package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.subsystems.Drive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {
    public static XboxController driver = new XboxController(0);
    public static XboxController codriver = new XboxController(1);

    public static final Drive drive = new Drive();

    public RobotContainer() {
        configureButtonBindings();
    }

    private void configureButtonBindings() {
        // normal button
        // new JoystickButton(driver, XboxController.Button.kB.value).whenActive(exampleCommand);
        // // trigger button
        // new TriggerButton(driver, Hand.kRight).whileActiveContinuous(exampleCommand);
        // // double button
        // new JoystickButton(driver, XboxController.Button.kA.value)
        //         .and(new JoystickButton(codriver, XboxController.Button.kA.value)).whenActive(exampleCommand);
    }

    public Command getAutonomousCommand() {
        return null;
    }
    public static double getDriverLeftStickY()
    {
        if(Math.abs(driver.getY(Hand.kLeft)) > 0.05)
        {
            return -driver.getY(Hand.kLeft);
        }
        else
        {
            return 0;
        }
    }

    public static double getDriverLeftStickX()
    {
        if(Math.abs(driver.getX(Hand.kLeft)) > 0.05)
        {
            return driver.getX(Hand.kLeft);
        }
        else
        {
            return 0;
        }
    }

    private class TriggerButton extends Trigger {
        Hand hand;
        XboxController controller;

        public TriggerButton(XboxController controller, Hand hand) {
            this.hand = hand;
            this.controller = controller;
        }

        @Override
        public boolean get() {
            return controller.getTriggerAxis(hand) >= 50;
        }
    }
}
