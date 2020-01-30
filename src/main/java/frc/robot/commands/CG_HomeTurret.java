package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class CG_HomeTurret extends SequentialCommandGroup {

    public CG_HomeTurret() {
        addCommands(
                new TurretHoming(),
                new TurretToPosition(0)
        );
    }
}
