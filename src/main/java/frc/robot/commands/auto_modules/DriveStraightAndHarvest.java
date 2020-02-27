package frc.robot.commands.auto_modules;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.*;

public class DriveStraightAndHarvest extends ParallelDeadlineGroup {
    public DriveStraightAndHarvest(double distance, double maxV, double maxA, double harvester){
        super(
                new DriveMotionProfile(distance, maxV, maxA),
                new BallHandlerHarvest(),
                new SequentialCommandGroup(
                        new HarvesterDown(),
                        new HarvesterSet(harvester)
                )
        );
    }
}
