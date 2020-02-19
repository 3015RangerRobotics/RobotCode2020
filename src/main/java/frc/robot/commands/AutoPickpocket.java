package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.auto_modules.*;

public class AutoPickpocket extends SequentialCommandGroup {

    public AutoPickpocket() {
        super(
                new DriveStraightAndHarvest(9.4, 10, 6, 5400, -0.75),
                new HarvestInPlace(-0.75, .5, 5400),
                new DrivePathAndShooterPrime("pp_trench_to_goal", 5400),
                new AimAndShoot(3),
                new DrivePathAndHarvest("pp_goal_to_our_trench", 5400, -0.75)
        );
    }
}
