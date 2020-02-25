package frc.robot.commands.auto_modules;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.*;

public class DrivePathAndShooterPrime extends SequentialCommandGroup {
    public DrivePathAndShooterPrime(String path, double shooter){
        super(
                new ShooterSetSpeed(shooter),
                new DriveMotionProfile(path)
        );
    }
}
