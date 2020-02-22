package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.commands.auto_modules.DriveStraightAndHarvest;
import frc.robot.commands.auto_modules.AimAndShoot;
import frc.robot.commands.auto_modules.HarvestInPlace;

public class Auto8BallTrench extends SequentialCommandGroup {

    public Auto8BallTrench() {
        addCommands(
                new ParallelDeadlineGroup(
                        new DriveStraightAndHarvest(10.5, 10, 8, 5400, -0.75)
//                        new CG_HomeTurret()
                ),
                new HarvestInPlace(-.75, 0.5, 5400),
                new AimAndShoot(3),
                new ParallelDeadlineGroup(
                        new DriveMotionProfile(6.6,10,8),
                        new ShooterStart(5400),
                        new TurretToPosition(0),
                        new BallHandlerHarvest(),
                        new SequentialCommandGroup(
                                new HarvesterInForTime(-0.75, 1.0),
                                new HarvesterUp()
                        )
                ),
                new HarvestInPlace(-0.75, 0.5, 5400),
                new DriveStraightAndHarvest(-4, 10, 8,5400, -0.75),
                new AimAndShoot(3),
                new TurretToPosition(0)
        );
    }
}
