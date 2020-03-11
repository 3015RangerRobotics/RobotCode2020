package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class CG_IntakeBalls extends SequentialCommandGroup {
    public CG_IntakeBalls() {
        super(
                new IntakeDown(),
                new ParallelCommandGroup(
                        new CarouselIntake(),
                        new IntakeSet(-1)
                )
        );
    }

    public CG_IntakeBalls(double speed) {
        super(
                new IntakeDown(),
                new ParallelCommandGroup(
                        new CarouselIntake(),
                        new IntakeSet(speed)
                )
        );
    }
}
