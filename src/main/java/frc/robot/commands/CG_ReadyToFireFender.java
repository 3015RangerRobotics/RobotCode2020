package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Limelight;

public class CG_ReadyToFireFender extends SequentialCommandGroup {

    public CG_ReadyToFireFender() {
        addCommands(
                new LimelightSwitchLEDMode(Limelight.LEDMode.LED_ON),
                new HoodUp(),
                new TurretTurnToTargetHold(),
                new ShooterAutoSpeedFender()
        );
    }
}
