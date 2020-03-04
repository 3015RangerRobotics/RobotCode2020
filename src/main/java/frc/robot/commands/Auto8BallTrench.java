package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.commands.auto_modules.DriveStraightAndHarvest;
import frc.robot.subsystems.Limelight;

public class Auto8BallTrench extends SequentialCommandGroup {

    public Auto8BallTrench() {
        super(
                new TurretHomePosition(),
                new LimelightSwitchLEDMode(Limelight.LEDMode.LED_ON),
                new DriveStraightAndHarvest(10, 12, 8, -1),
                new ShooterAutoSpeed(),
                new TurretTurnToTargetHold(),
                new CG_HarvesterOfBalls().withTimeout(0.5),
                new CG_FireZeMissiles(),
                new ShooterAutoSpeed(),
                new TurretTurnToTargetHold(),
//                new ParallelDeadlineGroup(
//                        new DriveMotionProfile(7.9,12,8),
//                        new BallHandlerHarvest(),
//                        new SequentialCommandGroup(
//                                new HarvesterSet(-0.75).withTimeout(1.5)
//                                new HarvesterUp()
//                        )
//                ),
                new DriveStraightAndHarvest(8.5, 10, 6, -1),
//                new CG_HarvesterOfBalls().withTimeout(0.5),
                new DriveStraightAndHarvest(-8, 12, 8, -1),
                new CG_FireZeMissiles(),
                new TurretToPosition(0),
                new ShooterStop(),
                new DriveMotionProfile(11.5, 12,10)
        );
    }
}
