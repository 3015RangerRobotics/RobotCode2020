package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.auto_modules.*;
import frc.robot.subsystems.Limelight;

public class AutoPickpocket extends SequentialCommandGroup {

    public AutoPickpocket() {
        super(
                new LimelightSwitchLEDMode(Limelight.LEDMode.LED_ON),
                new DriveMotionProfile(8.15, 12,10),
                new CG_HarvesterOfBalls().withTimeout(1),
                new ShooterAutoSpeed(),
                new TurretTurnToTargetHold(),
                new ParallelCommandGroup(
                        new DriveMotionProfile("pp_trench_to_shoot"),
                        new CG_HarvesterOfBalls().withTimeout(1)
                ),
                new CG_FireZeMissiles(),
                new ShooterAutoSpeed(),
                new TurretTurnToTargetHold(),
                new DrivePathAndHarvest("pp_shoot_to_balls", -0.75),
                new CG_HarvesterOfBalls().withTimeout(1),
//                new DriveStraightAndHarvest(-2, 12,8, -0.75),
                new CG_FireZeMissiles(),
                new TurretToDefaultPosition(),
                new ShooterStop()
        );
    }
}
