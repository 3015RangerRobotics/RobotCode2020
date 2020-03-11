package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.commands.auto_modules.DriveAndIntake;
import frc.robot.subsystems.Limelight;

public class Auto8BallTrench extends SequentialCommandGroup {

    public Auto8BallTrench() {
        super(
                new TurretHomePosition(),
                new LimelightSwitchLEDMode(Limelight.LEDMode.LED_ON),
                new DriveAndIntake(10, 12, 8, -1),
                new ShooterAutoSpeed(),
                new TurretTurnToTargetHold(),
                new CG_IntakeBalls().withTimeout(0.5),
                new CG_FireZeMissiles(),
                new ShooterAutoSpeed(),
                new TurretTurnToTargetHold(),
                new DriveAndIntake(8.5, 10, 6, -1),
                new DriveAndIntake(-8, 12, 8, -1),
                new CG_FireZeMissiles(),
                new TurretToPosition(0),
                new CG_ShooterDefault(),
                new DriveMotionProfile(11.5, 12,10)
        );
    }
}
