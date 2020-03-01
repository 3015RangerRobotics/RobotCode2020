package frc.robot.commands.auto_modules;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.*;

public class DrivePathAndHarvest extends ParallelRaceGroup {
    public DrivePathAndHarvest(String path, double harvester) {
        super(
                new SequentialCommandGroup(
                        new HarvesterDown(),
                        new ParallelCommandGroup(
                                new HarvesterSet(harvester),
                                new BallHandlerHarvest()
                        )
                ),
                new DriveMotionProfile(path)
        );
    }
}
