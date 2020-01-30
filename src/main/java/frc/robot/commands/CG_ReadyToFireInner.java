package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class CG_ReadyToFireInner extends SequentialCommandGroup {

    public CG_ReadyToFireInner() {
        addCommands(
                new LimelightSwitchPipeline(1),
                new HarvesterDown(),
                new WaitCommand(.1),
                new ParallelCommandGroup(
                        new ShooterStart(),
                        new TurretTurnToTarget()
                )
        );
    }
}
