package frc.robot.commands.auto_modules;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.*;

public class DrivePathAndHarvest extends SequentialCommandGroup {
    public DrivePathAndHarvest(String path, double shooter, double harvester){
        super(
                new ShooterSetSpeed(shooter),
                new ParallelRaceGroup(
                    new BallHandlerHarvest(),
                    new SequentialCommandGroup(
                            new HarvesterDown(),
                            new HarvesterSet(harvester)
                    ),
                    new DriveMotionProfile(path)
            )
        );
    }
}
