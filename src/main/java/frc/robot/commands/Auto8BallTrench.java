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
                        new HarvesterIn(-0.75),
                        new DriveStraightTemp(10.5, 8, 5)
                ),
                new ParallelDeadlineGroup(
                        new WaitCommand(.5),
                        new ShooterStart(5400),
                        new BallHandlerHarvest(),
                        new HarvesterIn(-0.75)
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
                new ParallelDeadlineGroup(
                        new DriveStraightTemp(7.30,8,5),
                        new ShooterStart(5400),
                        new TurretToPosition(0),
                        new BallHandlerHarvest(),
//                        new HarvesterIn(-.75),
                        new SequentialCommandGroup(
                                new HarvesterInForTime(-0.75, 1.4),
                                new HarvesterUp()
                        )

//                        new DriveStraightTemp(7.8, 7,5)
                ),
//                new DriveResetEncoders(),
                new ParallelDeadlineGroup(
                        new SequentialCommandGroup(
//                                new WaitCommand(1),
                                new HarvesterDown(),
                                new HarvesterInForTime(-0.75, 0.5)
                        ),
                        new BallHandlerHarvest(),
                        new ShooterStart(5400)
                ),
                new ParallelRaceGroup(
                        new BallHandlerHarvest(),
                        new HarvesterIn(-.75),
                        new ShooterStart(5400),
                        new DriveStraightTemp(-4, 8, 5)
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
