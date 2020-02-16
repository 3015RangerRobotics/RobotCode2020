package frc.robot.commands.auto_modules;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.BallHandlerHarvest;
import frc.robot.commands.HarvesterDown;
import frc.robot.commands.HarvesterInForTime;
import frc.robot.commands.ShooterStart;

public class HarvestInPlace extends ParallelDeadlineGroup {
    public HarvestInPlace(double harvester, double time, double shooter){
        super(
                new SequentialCommandGroup(
                        new HarvesterDown(),
                        new HarvesterInForTime(harvester, time)
                ),
                new BallHandlerHarvest(),
                new ShooterStart(shooter)
        );
    }
}
