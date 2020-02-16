package frc.robot.commands.auto_modules;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import frc.robot.commands.*;

public class DrivePathAndShooterPrime extends ParallelRaceGroup {
    public DrivePathAndShooterPrime(String path, double shooter){
        super(
                new ShooterStart(shooter),
                new DriveMotionProfile(path)
        );
    }
}
