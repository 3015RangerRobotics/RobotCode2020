package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Limelight;

public class CG_ReadyToFire extends SequentialCommandGroup {
    public CG_ReadyToFire() {
        super(
                new LimelightSwitchLEDMode(Limelight.LEDMode.LED_ON),
                new IntakeDown(),
                new LimelightWaitForTarget(),
                new ShooterAutoSpeed(),
                new TurretTurnToTargetHold()
        );
    }
}
