package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Limelight;

public class CG_FireZeMissilesFender extends SequentialCommandGroup {
    public CG_FireZeMissilesFender() {
        super(
                // new LimelightWaitForTarget(),
                new ShooterAutoSpeedFender(false),
                new TurretTurnToTarget(),
                new ShooterWaitUntilPrimed(),
                new BallHandlerShoot(0.10, true)
//                new LimelightSwitchLEDMode(Limelight.LEDMode.LED_OFF)
        );
    }
}
