package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.*;

public class CG_FireZeMissiles extends SequentialCommandGroup {
    public CG_FireZeMissiles() {
        super(
                // new LimelightWaitForTarget(),
                new ShooterAutoSpeed(),
                new ParallelRaceGroup(
                        new TurretTurnToTarget(),
                        new SequentialCommandGroup(
                                new TurretWaitUntilOnTarget(),
                                new WaitCommand(0.2)
                        )
                ),
                new ShooterAutoSpeed(false),
                new BallHandlerShoot()
        );
    }

    public CG_FireZeMissiles(double shooter) {
        super(
                // new LimelightWaitForTarget(),
                new ShooterSetSpeed(shooter),
                new ParallelRaceGroup(
                        new TurretTurnToTarget(),
                        new SequentialCommandGroup(
                                new TurretWaitUntilOnTarget(),
                                new WaitCommand(0.2)
                        )
                ),
                new ShooterSetSpeed(shooter),
                new BallHandlerShoot()
        );
    }
}
