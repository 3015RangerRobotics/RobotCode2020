package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.commands.auto_modules.DrivePathAndShooterPrime;
import frc.robot.commands.auto_modules.DriveStraightAndHarvest;
import frc.robot.commands.auto_modules.AimAndShoot;
import frc.robot.commands.auto_modules.HarvestInPlace;

public class Auto8BallTrench extends SequentialCommandGroup {

    public Auto8BallTrench() {
        super(
                new ParallelDeadlineGroup(
                        new DriveStraightAndHarvest(9.25, 12, 8, 5400, -0.75)
//                        new CG_HomeTurret()
                ),
                new HarvestInPlace(-.75, 0.5, 5400),
//                new DrivePathAndShooterPrime("trench1_to_shoot", 5400),
                new AimAndShoot().withTimeout(3),
                new ParallelDeadlineGroup(
                        new DriveMotionProfile(9.3,12,8),
                        new ShooterSetSpeed(5400),
                        new TurretToPosition(0),
                        new BallHandlerHarvest(),
                        new SequentialCommandGroup(
                                new HarvesterSet(-0.75).withTimeout(1.5),
                                new HarvesterUp()
                        )
                ),
                new HarvestInPlace(-0.75, 0.5, 5600),
                new DriveStraightAndHarvest(-4, 12, 8,5400, -0.75),
                new AimAndShoot().withTimeout(3),
                new TurretToPosition(0)
        );
    }
}
