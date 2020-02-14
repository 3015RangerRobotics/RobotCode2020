package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class CG_ReadyToFireBatter extends SequentialCommandGroup {

    public CG_ReadyToFireBatter() {
        addCommands(
                new HoodUp(),
                new ShooterStart(6000)
        );
    }
}
