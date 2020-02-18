package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Limelight;

public class CG_ReadyToFireOuter extends SequentialCommandGroup {

    public CG_ReadyToFireOuter() {
        addCommands(
                new LimelightSwitchLEDMode(Limelight.LEDMode.LED_ON),
                new LimelightSwitchPipeline(0),
                new HarvesterDown(),
                new WaitCommand(.1),
                new ParallelCommandGroup(
                        new ShooterStartAuto(),
                        new TurretTurnToTarget()
                )
        );
    }
}
