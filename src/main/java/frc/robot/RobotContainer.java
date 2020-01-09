package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {
    public static XboxController driver = new XboxController(0);
    public static XboxController codriver = new XboxController(1);

    private final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();

    private final ExampleCommand exampleCommand = new ExampleCommand(exampleSubsystem);

    public RobotContainer() {
        configureButtonBindings();
    }

    private void configureButtonBindings() {
        // normal button
        new JoystickButton(driver, XboxController.Button.kB.value).whenActive(exampleCommand);
        // trigger button
        new TriggerButton(driver, Hand.kRight).whileActiveContinuous(exampleCommand);
        // double button
        new JoystickButton(driver, XboxController.Button.kA.value)
                .and(new JoystickButton(codriver, XboxController.Button.kA.value)).whenActive(exampleCommand);
    }

    public Command getAutonomousCommand() {
        return null;
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
