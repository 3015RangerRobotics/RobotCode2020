package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.auto_modules.DriveAndIntake;
import frc.robot.subsystems.Limelight;

public class Auto10BallTrench extends SequentialCommandGroup {

    public Auto10BallTrench() {
        super(
                new LimelightSwitchLEDMode(Limelight.LEDMode.LED_ON),
                new IntakeDown(),
                new TurretHomePosition(),
                new ParallelDeadlineGroup(
                        new SequentialCommandGroup(
                                new DriveMotionProfile("10_line_to_center"),
                                new ShooterAutoSpeed(),
                                new TurretTurnToTargetHold(),
                                new DriveMotionProfile("10_center_to_shoot")
                        ),
                        new CG_IntakeBalls(-0.8)
                ),
                new CG_FireZeMissiles(),
                new ShooterAutoSpeed(),
                new DriveAndIntake("10_five_more2", -1),
                new TurretTurnToTargetHold(),
                new DriveAndIntake("10_trench_to_shoot", -1),
                new CG_FireZeMissiles(),
                new CG_ShooterDefault()
        );
    }
}
