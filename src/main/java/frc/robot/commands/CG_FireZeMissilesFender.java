package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class CG_FireZeMissilesFender extends SequentialCommandGroup {
    public CG_FireZeMissilesFender() {
        super(
                // new LimelightWaitForTarget(),
                new ShooterWaitUntilPrimed(),
                new BallHandlerShoot()
        );
    }
}
