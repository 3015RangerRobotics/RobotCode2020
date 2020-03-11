package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class CG_OhHeck extends SequentialCommandGroup {

    public CG_OhHeck() {
        addCommands(
                new IntakeDown(),
                new TurretToDefaultPosition(),
                new TurretWaitUntilOnTarget(),
                new IntakeUp()
        );
    }
}
