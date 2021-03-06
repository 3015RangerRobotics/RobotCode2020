package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class CG_FireZeMissilesFender extends SequentialCommandGroup {
    public CG_FireZeMissilesFender() {
        super(
                new ShooterAutoSpeedFender(false),
                new TurretTurnToTarget(),
                new ShooterWaitUntilPrimed(),
                new CarouselShoot(0.1, true)
        );
    }
}
