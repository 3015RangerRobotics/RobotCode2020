package frc.robot.commands.auto_modules;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.*;

public class DriveStraightAndHarvest extends SequentialCommandGroup {
    public DriveStraightAndHarvest(double distance, double maxV, double maxA, double shooter, double harvester){
        super(
                new ShooterSetSpeed(shooter),
                new ParallelRaceGroup(
                    new BallHandlerHarvest(),
                    new SequentialCommandGroup(
                            new HarvesterDown(),
                            new HarvesterSet(harvester)
                    ),
                    new DriveMotionProfile(distance, maxV, maxA)
                )
        );
    }
}
