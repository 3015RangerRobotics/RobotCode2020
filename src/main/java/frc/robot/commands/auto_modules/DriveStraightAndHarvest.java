package frc.robot.commands.auto_modules;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.*;

public class DriveStraightAndHarvest extends ParallelRaceGroup {
    public DriveStraightAndHarvest(double distance, double maxV, double maxA, double shooter, double harvester){
        super(
                new ShooterStart(shooter),
                new BallHandlerHarvest(),
                new SequentialCommandGroup(
                        new HarvesterDown(),
                        new HarvesterIn(harvester)
                ),
                new DriveStraightTemp(distance, maxV, maxA)
        );
    }
}
