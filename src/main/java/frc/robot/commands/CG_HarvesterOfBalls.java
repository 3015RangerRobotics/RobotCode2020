package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class CG_HarvesterOfBalls extends SequentialCommandGroup {
    public CG_HarvesterOfBalls() {
        super(
                new HarvesterDown(),
                new ParallelCommandGroup(
                        new BallHandlerHarvest(),
                        new HarvesterSet(-1)
                )
        );
    }
}
