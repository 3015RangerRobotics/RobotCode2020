package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {
    public static XboxController driver = new XboxController(0);
    public static XboxController codriver = new XboxController(1);

    public static BallHandler ballHandler;
    public static Drive drive;
    public static Turret turret;
    public static Limelight limelight;
    public static Shooter shooter;


    public static JoystickButton driverA = new JoystickButton(driver, XboxController.Button.kA.value);
    public static JoystickButton driverB = new JoystickButton(driver, XboxController.Button.kB.value);
    public static JoystickButton driverX = new JoystickButton(driver, XboxController.Button.kX.value);
    public static JoystickButton driverY = new JoystickButton(driver, XboxController.Button.kY.value);
    public static JoystickButton driverLB = new JoystickButton(driver, XboxController.Button.kBumperLeft.value);
    public static JoystickButton driverRB = new JoystickButton(driver, XboxController.Button.kBumperRight.value);
    public static JoystickButton driverST = new JoystickButton(driver, XboxController.Button.kStart.value);
    public static JoystickButton driverBK = new JoystickButton(driver, XboxController.Button.kBack.value);
    public static TriggerButton driverLT;
    public static TriggerButton driverRT;

    public RobotContainer() {
        // drive = new Drive();
        turret = new Turret();
        limelight = new Limelight();
        shooter = new Shooter();
        ballHandler = new BallHandler();
        driverLT = new TriggerButton(driver, Hand.kLeft);
        driverRT = new TriggerButton(driver, Hand.kRight);
        configureButtonBindings();
    }

    private void configureButtonBindings() {
        driverA.whileActiveContinuous(new TurretToPosition(0));
        driverY.whileActiveContinuous(new TurretTurnToTarget());
        driverB.whileActiveOnce(new BallHandlerHarvest());
        driverX.whileActiveOnce(new BallHandlerShoot());
        driverLB.whileActiveContinuous(new TurretToPosition(-45));
        driverRB.whileActiveContinuous(new TurretToPosition(45));
        driverST.whenActive(new ShooterStart());
        driverBK.whenActive(new ShooterStop());
        driverLT.whileActiveContinuous(new TurretTurnToInner());
        // normal button
        // new JoystickButton(driver,
        // XboxController.Button.kB.value).whenActive(exampleCommand);
        // // trigger button
        // new TriggerButton(driver, Hand.kRight).whileActiveContinuous(exampleCommand);
        // // double button
        // new JoystickButton(driver, XboxController.Button.kA.value)
        // .and(new JoystickButton(codriver,
        // XboxController.Button.kA.value)).whenActive(exampleCommand);
    }

    public Command getAutonomousCommand() {
        return null;
    }

    public static double getDriverLeftStickY() {
        if (Math.abs(driver.getY(Hand.kLeft)) > 0.05) {
            return -driver.getY(Hand.kLeft);
        } else {
            return 0;
        }
    }

    public static double getDriverLeftStickX() {
        if (Math.abs(driver.getX(Hand.kLeft)) > 0.05) {
            return driver.getX(Hand.kLeft);
        } else {
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
            return controller.getTriggerAxis(hand) >= 0.5;
        }
    }
}
