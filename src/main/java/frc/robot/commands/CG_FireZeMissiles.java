package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.*;

public class CG_FireZeMissiles extends SequentialCommandGroup {
    public CG_FireZeMissiles() {
        super(
                // new LimelightWaitForTarget(),
                new ParallelRaceGroup(
                        new ShooterStartAuto(),
                        new TurretTurnToTarget(),
                        new SequentialCommandGroup(
                                new TurretWaitUntilOnTarget(),
                                new WaitCommand(0.2)
                        )
                ),
                new ParallelCommandGroup(
                        new ShooterStartAuto(false),
                        new BallHandlerShoot()
                )
        );
    }

    public CG_FireZeMissiles(double shooter) {
        super(
                // new LimelightWaitForTarget(),
                new ParallelRaceGroup(
                        new ShooterStart(shooter),
                        new TurretTurnToTarget(),
                        new SequentialCommandGroup(
                                new TurretWaitUntilOnTarget(),
                                new WaitCommand(0.2)
                        )
                ),
                new ParallelCommandGroup(
                        new ShooterStart(shooter),
                        new BallHandlerShoot()
                )
        );
    }
}
