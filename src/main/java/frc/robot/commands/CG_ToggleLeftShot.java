package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class CG_ToggleLeftShot extends SequentialCommandGroup {

    public CG_ToggleLeftShot() {
        addCommands(
                new IntakeDown(),
                new WaitCommand(0.1),
                new TurretToggleLeftShot()
        );
    }
}
