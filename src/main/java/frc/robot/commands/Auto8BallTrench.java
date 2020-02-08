package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.*;

public class Auto8BallTrench extends SequentialCommandGroup {

    public Auto8BallTrench() {
        addCommands(
                new DriveResetEncoders(),
                new HarvesterDown(),
                new WaitCommand(0.5),
                new ParallelRaceGroup(
//                        new CG_HomeTurret(),
                        new ShooterStart(5400),
                        new BallHandlerHarvest(),
                        new HarvesterIn(-1.0),
                        new DriveStraightTemp(10.5, 6, 5)
                ),
                new ParallelDeadlineGroup(
                        new WaitCommand(.5),
                        new ShooterStart(5400),
                        new BallHandlerHarvest(),
                        new HarvesterIn(-1.00)
                ),
                new ParallelDeadlineGroup(
                        new WaitCommand(3),
                        new SequentialCommandGroup(
                                new WaitCommand(0.25),
//                                new DriveResetEncoders(),
                                new ParallelCommandGroup(
                                        new TurretTurnToTarget(),
                                        new ShooterStartAuto()
                                )
                        ),
                        new SequentialCommandGroup(
                                new WaitCommand(0.75),
                                new BallHandlerShoot()
                        )
                ),
                new ParallelRaceGroup(
                        new ShooterStart(5400),
                        new TurretToPosition(0),
                        new BallHandlerHarvest(),
                        new HarvesterIn(-.75),
                        new DriveMotionProfile("path")
//                        new DriveStraightTemp(7.8, 7,5)
                ),
//                new DriveResetEncoders(),
//                new ParallelDeadlineGroup(
//                        new WaitCommand(.6),
//                        new BallHandlerHarvest(),
//                        new HarvesterIn(-.6),
//                        new ShooterStart(5400)
//                ),
                new ParallelRaceGroup(
                        new BallHandlerHarvest(),
                        new HarvesterIn(-.75),
                        new ShooterStart(5400),
                        new DriveMotionProfile("path2")
//                        new DriveStraightTemp(-4, 10, 5)
                ),
                new ParallelDeadlineGroup(
                        new WaitCommand(3),
                        new SequentialCommandGroup(
                                new WaitCommand(0.25),
//                                new DriveResetEncoders(),
                                new ParallelCommandGroup(
                                        new TurretTurnToTarget(),
                                        new ShooterStartAuto()
                                )
                        ),
                        new SequentialCommandGroup(
                                new WaitCommand(0.75),
                                new BallHandlerShoot()
                        )
                ),
                new TurretToPosition(0)
        );
    }
}
