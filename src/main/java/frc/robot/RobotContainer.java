package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import frc.robot.subsystems.BallHandler;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {
    public static XboxController driver = new XboxController(0);
    public static XboxController coDriver = new XboxController(1);

    public static BallHandler ballHandler;
    public static Drive drive;
    public static Turret turret;
    public static Limelight limelight;
    public static DriverRumble driverRumble;
    public static Shooter shooter;
    public static OurCompressor ourCompressor;
    public static Harvester harvester;

    public static JoystickButton driverA = new JoystickButton(driver, XboxController.Button.kA.value);
    public static JoystickButton driverB = new JoystickButton(driver, XboxController.Button.kB.value);
    public static JoystickButton driverX = new JoystickButton(driver, XboxController.Button.kX.value);
    public static JoystickButton driverY = new JoystickButton(driver, XboxController.Button.kY.value);
    public static JoystickButton driverLB = new JoystickButton(driver, XboxController.Button.kBumperLeft.value);
    public static JoystickButton driverRB = new JoystickButton(driver, XboxController.Button.kBumperRight.value);
    public static DPadButton driverDUp = new DPadButton(driver, DPadButton.Value.kDPadUp);
    public static DPadButton driverDDown = new DPadButton(driver, DPadButton.Value.kDPadDown);
    public static DPadButton driverDLeft = new DPadButton(driver, DPadButton.Value.kDPadLeft);
    public static DPadButton driverDRight = new DPadButton(driver, DPadButton.Value.kDPadRight);
    public static TriggerButton driverLT = new TriggerButton(driver, Hand.kLeft);
    public static TriggerButton driverRT = new TriggerButton(driver, Hand.kRight);
    public static JoystickButton driverStart = new JoystickButton(driver, XboxController.Button.kStart.value);
    public static JoystickButton driverBack = new JoystickButton(driver, XboxController.Button.kBack.value);

    public static JoystickButton coDriverA = new JoystickButton(coDriver, XboxController.Button.kA.value);
    public static JoystickButton coDriverB = new JoystickButton(coDriver, XboxController.Button.kB.value);
    public static JoystickButton coDriverX = new JoystickButton(coDriver, XboxController.Button.kX.value);
    public static JoystickButton coDriverY = new JoystickButton(coDriver, XboxController.Button.kY.value);
    public static JoystickButton coDriverLB = new JoystickButton(coDriver, XboxController.Button.kBumperLeft.value);
    public static JoystickButton coDriverRB = new JoystickButton(coDriver, XboxController.Button.kBumperRight.value);
    public static DPadButton coDriverDUp = new DPadButton(coDriver, DPadButton.Value.kDPadUp);
    public static DPadButton coDriverDDown = new DPadButton(coDriver, DPadButton.Value.kDPadDown);
    public static DPadButton coDriverDLeft = new DPadButton(coDriver, DPadButton.Value.kDPadLeft);
    public static DPadButton coDriverDRight = new DPadButton(coDriver, DPadButton.Value.kDPadRight);
    public static TriggerButton coDriverLT = new TriggerButton(coDriver, Hand.kLeft);
    public static TriggerButton coDriverRT = new TriggerButton(coDriver, Hand.kRight);
    public static JoystickButton coDriverStart = new JoystickButton(coDriver, XboxController.Button.kStart.value);
    public static JoystickButton coDriverBack = new JoystickButton(coDriver, XboxController.Button.kBack.value);

    public RobotContainer() {
        ourCompressor = new OurCompressor();
        drive = new Drive();
        drive.setDefaultCommand(new DriveWithGamepad());
        turret = new Turret();
        limelight = new Limelight();
        shooter = new Shooter();
        ballHandler = new BallHandler();
        ballHandler.setDefaultCommand(new BallHandlerDefault());
        harvester = new Harvester();
        driverRumble = new DriverRumble();

        configureButtonBindings();
    }


    private void configureButtonBindings() {
        driverA.whileActiveContinuous(new CG_HarvesterOfBalls());
        driverB.whileActiveContinuous(new BallHandlerPurge());
        driverDLeft.whenActive(new CG_HomeTurret());
        driverDUp.whenActive(new CG_OhHeck());
        driverDDown.whenActive(new HarvesterDown());
        driverLB.whileActiveContinuous(new CG_ReadyToFireInner()).whenInactive(new TurretToPosition(0));
        driverLT.whileActiveContinuous(new CG_ReadyToFireOuter()).whenInactive(new TurretToPosition(0));
        driverRT.whileActiveContinuous(new CG_FireZeMissiles());
        driverStart.whenActive(new CompressorStart());
        driverBack.whenActive(new CompressorStop());

        coDriverA.whileActiveContinuous(new CG_HarvesterOfBalls());
        coDriverB.whileActiveContinuous(new BallHandlerPurge());
        coDriverDLeft.whenActive(new CG_HomeTurret());
        coDriverDUp.whenActive(new CG_OhHeck());
        coDriverDDown.whenActive(new HarvesterDown());
        coDriverLB.whileActiveContinuous(new CG_ReadyToFireInner()).whenInactive(new TurretToPosition(0));
        coDriverLT.whileActiveContinuous(new CG_ReadyToFireOuter()).whenInactive(new TurretToPosition(0));
        coDriverRT.whileActiveContinuous(new CG_FireZeMissiles());
        coDriverStart.whenActive(new CompressorStart());
        coDriverBack.whenActive(new CompressorStop());
    }

    public Command getAutonomousCommand() {
        return null;
    }

    /**
     * @return The Y value of the driver's left stick
     */
    public static double getDriverLeftStickY() {
        if (Math.abs(driver.getY(Hand.kLeft)) > 0.05) {
            return -driver.getY(Hand.kLeft);
        } else {
            return 0;
        }
    }

    /**
     * @return The X value of the driver's left stick
     */
    public static double getDriverLeftStickX() {
        if (Math.abs(driver.getX(Hand.kLeft)) > 0.05) {
            return driver.getX(Hand.kLeft);
        } else {
            return 0;
        }
    }

    /**
     * @return The Y value of the driver's right stick
     */
    public static double getDriverRightStickY() {
        if (Math.abs(driver.getY(Hand.kRight)) > 0.05) {
            return -driver.getY(Hand.kRight);
        } else {
            return 0;
        }
    }

    /**
     * @return The X value of the driver's right stick
     */
    public static double getDriverRightStickX() {
        if (Math.abs(driver.getX(Hand.kRight)) > 0.05) {
            return driver.getX(Hand.kRight);
        } else {
            return 0;
        }
    }

    /**
     * Sets the left(soft) rumble on the driver's controller
     *
     * @param left a value from 0 to 1 representing the power
     */
    public static void setDriverRumbleLeft(double left) {
        driver.setRumble(RumbleType.kLeftRumble, left);
    }

    /**
     * Sets the right(hard) rumble on the driver's controller
     *
     * @param right a value from 0 to 1 representing the power
     */
    public static void setDriverRumbleRight(double right) {
        driver.setRumble(RumbleType.kRightRumble, right);
    }

    /**
     * Sets the left(soft) rumble on the drivers controller
     *
     * @param left a value from 0 to 1 representing the power
     */
    public static void setCoDriverRumbleLeft(double left) {
        coDriver.setRumble(RumbleType.kLeftRumble, left);
    }

    /**
     * Sets the right(hard) rumble on the drivers controller
     *
     * @param right a value from 0 to 1 representing the power
     */
    public static void setCoDriverRumbleRight(double right) {
        coDriver.setRumble(RumbleType.kRightRumble, right);
    }


    private static class TriggerButton extends Trigger {
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
