package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class CG_ToggleTurretDefaultPosition extends SequentialCommandGroup {

    public CG_ToggleTurretDefaultPosition() {
        addCommands(
                new TurretToggleLeftShot(),
                new TurretToDefaultPosition()
        );
    }
}
