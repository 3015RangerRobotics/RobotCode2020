package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class CG_ReadyToFireFender extends SequentialCommandGroup {

    public CG_ReadyToFireFender() {
        addCommands(
                new HoodUp(),
                new ShooterSetSpeed(6600)
        );
    }
}
