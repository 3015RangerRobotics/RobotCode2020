package frc.robot.commands.auto_modules;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.*;

public class DrivePathAndHarvest extends ParallelDeadlineGroup {
    public DrivePathAndHarvest(String path, double harvester) {
        super(
                new DriveMotionProfile(path),
                new BallHandlerHarvest(),
                new SequentialCommandGroup(
                        new HarvesterDown(),
                        new HarvesterSet(harvester)
                )
        );
    }
}
