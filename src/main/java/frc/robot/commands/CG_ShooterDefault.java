package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class CG_ShooterDefault extends SequentialCommandGroup {

    public CG_ShooterDefault() {
        addCommands(
                new HoodDown(),
                new TurretToDefaultPosition(),
                new ShooterStop()
        );
    }
}
