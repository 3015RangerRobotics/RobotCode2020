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
                        new HarvesterIn(),
                        new DriveMotionProfile(11)
                ),
                new ParallelDeadlineGroup(
                        new WaitCommand(3),
                        new ShooterStart(5400),
                        new TurretTurnToTarget(),
                        new SequentialCommandGroup(
                                new WaitCommand(0.75),
                                new BallHandlerShoot()
                        )
                ),
                new TurretToPosition(0)
        );
    }
}
