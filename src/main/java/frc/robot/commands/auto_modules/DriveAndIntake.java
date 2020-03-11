package frc.robot.commands.auto_modules;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.*;

public class DriveAndIntake extends ParallelDeadlineGroup {
    public DriveAndIntake(double distance, double maxV, double maxA, double intake){
        super(
                new DriveMotionProfile(distance, maxV, maxA),
                new CarouselIntake(),
                new SequentialCommandGroup(
                        new IntakeDown(),
                        new IntakeSet(intake)
                )
        );
    }

    public DriveAndIntake(String path, double intake){
        super(
                new DriveMotionProfile(path),
                new CarouselIntake(),
                new SequentialCommandGroup(
                        new IntakeDown(),
                        new IntakeSet(intake)
                )
        );
    }
}
