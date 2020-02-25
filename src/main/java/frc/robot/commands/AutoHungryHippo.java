package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.auto_modules.*;

public class AutoHungryHippo extends SequentialCommandGroup {

    public AutoHungryHippo() {
        super(
                new ParallelDeadlineGroup(
                        new WaitCommand(1),
                        new TurretToPosition(-90),
                        new HarvesterDown(),
                        new ShooterStart(6500)

                ),
                new ParallelDeadlineGroup(
                        new AimAndShoot(6500).withTimeout(10),
                        new HarvesterSet(-0.75)
                ),
                new CG_HomeTurret()
        );
    }
}
