package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.*;

public class CG_FireZeMissiles extends SequentialCommandGroup {
    public CG_FireZeMissiles() {
        super(
                new ShooterAutoSpeed(),
                new TurretTurnToTarget(),
                new TurretWaitUntilOnTarget(),
                new ShooterAutoSpeed(false),
                new ShooterWaitUntilPrimed(),
                new ParallelDeadlineGroup(
                        new CarouselShoot(),
                        new IntakeSet(-1)
                )
        );
    }
}
