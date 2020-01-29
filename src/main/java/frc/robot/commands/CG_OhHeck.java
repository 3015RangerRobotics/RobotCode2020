package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class CG_OhHeck extends ParallelCommandGroup {

    public CG_OhHeck() {
        addCommands(
                new TurretToPosition(0),
                new SequentialCommandGroup(
                        new WaitCommand(.5),
                        new HarvesterUp()
                )
        );
    }
}
