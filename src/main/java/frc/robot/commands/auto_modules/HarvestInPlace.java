package frc.robot.commands.auto_modules;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.BallHandlerHarvest;
import frc.robot.commands.HarvesterDown;
import frc.robot.commands.HarvesterSet;
import frc.robot.commands.ShooterSetSpeed;

public class HarvestInPlace extends ParallelDeadlineGroup {
    public HarvestInPlace(double harvester, double time, double shooter){
        super(
                new SequentialCommandGroup(
                        new HarvesterDown(),
                        new HarvesterSet(harvester).withTimeout(time)
                ),
                new BallHandlerHarvest(),
                new ShooterSetSpeed(shooter)
        );
    }
}
