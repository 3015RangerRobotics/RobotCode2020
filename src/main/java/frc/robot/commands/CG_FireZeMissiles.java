package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.*;

public class CG_FireZeMissiles extends SequentialCommandGroup {
    public CG_FireZeMissiles() {
        super(
                // new LimelightWaitForTarget(),
                new ShooterAutoSpeed(),
                new TurretTurnToTarget(),
                new TurretWaitUntilOnTarget(),
                new WaitCommand(0.2),
                new ShooterAutoSpeed(false),
                new ShooterWaitUntilPrimed(),
                new ParallelDeadlineGroup(
                        new BallHandlerShoot(0.05,false),
                        new HarvesterSet(-0.75)
                )
        );
    }

    public CG_FireZeMissiles(double shooter) {
        super(
                // new LimelightWaitForTarget(),
                new ShooterSetSpeed(shooter),
                new TurretTurnToTarget(),
                new TurretWaitUntilOnTarget(),
                new WaitCommand(0.2),
                new ShooterSetSpeed(shooter),
                new ShooterWaitUntilPrimed(),
                new BallHandlerShoot()
        );
    }
}
