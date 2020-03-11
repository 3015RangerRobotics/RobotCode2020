package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.auto_modules.DriveAndIntake;
import frc.robot.subsystems.Limelight;

public class Auto8BallPickpocket extends SequentialCommandGroup {

    public Auto8BallPickpocket() {
        super(
                new LimelightSwitchLEDMode(Limelight.LEDMode.LED_ON),
                new TurretHomePosition(),
                new DriveAndIntake(7.7, 12,10, -1.0),
                new ShooterAutoSpeed(),
                new TurretTurnToTargetHold(),
                new ParallelCommandGroup(
                        new DriveMotionProfile("pp_trench_to_shoot"),
                        new CG_IntakeBalls().withTimeout(1)
                ),
                new CG_FireZeMissiles(),
                new DriveAndIntake(3.4,12,6, -1.0),
                new ShooterAutoSpeed(),
                new TurretTurnToTargetHold(),
                new DriveAndIntake("pp_scoot", -1.0),
                new DriveAndIntake(1.7, 12, 10, -1.0),
                new DriveAndIntake(-2, 12, 10, -1.0),
                new CG_FireZeMissiles(),
                new CG_ShooterDefault()
        );
    }
}
