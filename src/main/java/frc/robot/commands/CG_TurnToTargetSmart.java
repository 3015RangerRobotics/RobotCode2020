package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Limelight;

public class CG_TurnToTargetSmart extends SequentialCommandGroup {
    public CG_TurnToTargetSmart() {
        super(
                new LimelightSwitchLEDMode(Limelight.LEDMode.LED_ON),
                new LimelightWaitForTarget(),
                new TurretTurnToTarget(),
                new LimelightSwitchPipelineSmart(),
                new WaitCommand(0.1),
                new TurretTurnToTargetHold()
        );
    }
}
