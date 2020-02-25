package frc.robot.commands.auto_modules;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.*;

public class DrivePathAndHarvest extends ParallelRaceGroup {
    public DrivePathAndHarvest(String path, double shooter, double harvester){
        super(
                new ShooterStart(shooter),
                new BallHandlerHarvest(),
                new SequentialCommandGroup(
                        new HarvesterDown(),
                        new HarvesterSet(harvester)
                ),
                new DriveMotionProfile(path)
        );
    }
}
