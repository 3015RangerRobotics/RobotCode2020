package frc.robot.commands.systems_checks;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import frc.robot.commands.systems_checks.*;

import java.util.Map;

public class SystemCheckLayout {
    public static NetworkTableEntry driveLeftMaster;
    public static NetworkTableEntry driveLeftFollower;
    public static NetworkTableEntry driveRightMaster;
    public static NetworkTableEntry driveRightFollower;
    public static NetworkTableEntry drivePosition;
    public static NetworkTableEntry driveIMU;

    public static NetworkTableEntry intakeMotor;
    public static NetworkTableEntry intakeUp;
    public static NetworkTableEntry intakeDown;

    public static NetworkTableEntry turretMotor;
    public static NetworkTableEntry turretLeftLimit;
    public static NetworkTableEntry turretRightLimit;
    public static NetworkTableEntry turretEncoder;

    public static NetworkTableEntry hoodUp;
    public static NetworkTableEntry hoodDown;

    public static NetworkTableEntry carouselMotor1;
    public static NetworkTableEntry carouselMotor2;
    public static NetworkTableEntry carouselMotor3;
    public static NetworkTableEntry carouselMotor4;
    public static NetworkTableEntry carouselMotor5;
    public static NetworkTableEntry carouselBall1;
    public static NetworkTableEntry carouselBall2;
    public static NetworkTableEntry carouselBall3;
    public static NetworkTableEntry carouselBall4;
    public static NetworkTableEntry carouselBall5;


    public static NetworkTableEntry shooterMotor;
    public static NetworkTableEntry shooterEncoder;
    public static NetworkTableEntry shooterPID;

    public static NetworkTableEntry climberMotor;
    public static NetworkTableEntry climberLatch;
    public static NetworkTableEntry climberRelease;

    public static void init(){
        ShuffleboardLayout testCommands = Shuffleboard.getTab("Systems Check")
                .getLayout("Test Commands", BuiltInLayouts.kList).withSize(1, 3).withPosition(10, 2)
                .withProperties(Map.of("Label position", "HIDDEN"));
        testCommands.add(new TestDrive());
        testCommands.add(new TestIntake());
        testCommands.add(new TestTurret());
        testCommands.add(new TestHood());
        testCommands.add(new TestCarousel());
        testCommands.add(new TestShooter());
        testCommands.add(new TestClimber());

        Shuffleboard.getTab("Systems Check").add(new TestAll()).withSize(1, 1).withPosition(10, 0);
        Shuffleboard.getTab("Systems Check").add(new TestAllWithoutClimber()).withSize(1, 1).withPosition(10, 1);

        ShuffleboardLayout driveValues = Shuffleboard.getTab("Systems Check").getLayout("Drive", BuiltInLayouts.kList)
                .withSize(2, 5).withPosition(0, 0);
        driveLeftMaster = driveValues.add("Left Master", false).getEntry();
        driveLeftFollower = driveValues.add("Left Follower", false).getEntry();
        driveRightMaster = driveValues.add("Right Master", false).getEntry();
        driveRightFollower = driveValues.add("Right Follower", false).getEntry();
        drivePosition= driveValues.add("Position", false).getEntry();
        driveIMU = driveValues.add("IMU", false).getEntry();

        ShuffleboardLayout intakeValues = Shuffleboard.getTab("Systems Check").getLayout("Intake", BuiltInLayouts.kList)
                .withSize(2, 2).withPosition(2, 0);
        intakeMotor = intakeValues.add("Motor", false).getEntry();
        intakeUp = intakeValues.add("Up", false).getEntry();
        intakeDown = intakeValues.add("Down", false).getEntry();

        ShuffleboardLayout turretValues = Shuffleboard.getTab("Systems Check").getLayout("Turret", BuiltInLayouts.kList)
                .withSize(2, 3).withPosition(2, 2);
        turretMotor = turretValues.add("Motor", false).getEntry();
        turretLeftLimit = turretValues.add("Left Limit", false).getEntry();
        turretRightLimit = turretValues.add("Right Limit", false).getEntry();
        turretEncoder = turretValues.add("Encoder", false).getEntry();

        ShuffleboardLayout hoodValues = Shuffleboard.getTab("Systems Check").getLayout("Hood", BuiltInLayouts.kList)
                .withSize(2, 2).withPosition(4, 0);
        hoodUp = hoodValues.add("Hood Up", false).getEntry();
        hoodDown = hoodValues.add("Hood Down", false).getEntry();

        ShuffleboardLayout carouselValues = Shuffleboard.getTab("Systems Check").getLayout("Carousel", BuiltInLayouts.kList)
                .withSize(2, 5).withPosition(6, 0);
        carouselMotor1 = carouselValues.add("Motor 1", false).getEntry();
        carouselMotor2 = carouselValues.add("Motor 2", false).getEntry();
        carouselMotor3 = carouselValues.add("Motor 3", false).getEntry();
        carouselMotor4 = carouselValues.add("Motor 4", false).getEntry();
        carouselMotor5 = carouselValues.add("Motor 5", false).getEntry();
        carouselBall1 = carouselValues.add("Ball 1", false).getEntry();
        carouselBall2 = carouselValues.add("Ball 2",false).getEntry();
        carouselBall3 = carouselValues.add("Ball 3",false).getEntry();
        carouselBall4 = carouselValues.add("Ball 4",false).getEntry();
        carouselBall5 = carouselValues.add("Ball 5",false).getEntry();

        ShuffleboardLayout shooterValues = Shuffleboard.getTab("Systems Check").getLayout("Shooter", BuiltInLayouts.kList)
                .withSize(2, 2).withPosition(4, 2);
        shooterMotor = shooterValues.add("Motor", false).getEntry();
        shooterEncoder = shooterValues.add("Encoder", false).getEntry();
        shooterPID = shooterValues.add("PID", false).getEntry();

        ShuffleboardLayout climberValues = Shuffleboard.getTab("Systems Check").getLayout("Climber", BuiltInLayouts.kList)
                .withSize(2, 3).withPosition(8, 0);
        climberMotor = climberValues.add("Motor", false).getEntry();
        climberRelease = climberValues.add("Release", false).getEntry();
        climberLatch= climberValues.add("Latch", false).getEntry();
    }
}
