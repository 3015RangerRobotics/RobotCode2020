package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class CG_PurgeBalls extends SequentialCommandGroup {

    public CG_PurgeBalls() {
        addCommands(
                new HarvesterUp(),
                new WaitCommand(0.25),
                new BallHandlerPurge()
        );
    }
}
