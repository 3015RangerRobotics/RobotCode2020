package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.commands.TempShooterStart;
import frc.robot.commands.TempShooterStop;
import frc.robot.commands.TurretDefaultPosition;
import frc.robot.commands.TurretTurnToTarget;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.TempShooter;
import frc.robot.subsystems.Turret;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {
    public static XboxController driver = new XboxController(0);
    public static XboxController codriver = new XboxController(1);

    public static Drive drive;
    public static Turret turret;
    public static TempShooter tempShooter;

    public static JoystickButton driverA = new JoystickButton(driver, XboxController.Button.kA.value);
    public static JoystickButton driverB = new JoystickButton(driver, XboxController.Button.kB.value);
    public static JoystickButton driverX = new JoystickButton(driver, XboxController.Button.kX.value);
    public static JoystickButton driverY = new JoystickButton(driver, XboxController.Button.kY.value);

    public RobotContainer() {
        // drive = new Drive();
        turret = new Turret();
        tempShooter = new TempShooter();
        configureButtonBindings();
    }

    private void configureButtonBindings() {
        driverA.whileActiveContinuous(new TurretDefaultPosition());
        driverY.whileActiveContinuous(new TurretTurnToTarget());
        driverB.whenActive(new TempShooterStart());
        driverX.whenActive(new TempShooterStop());
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
