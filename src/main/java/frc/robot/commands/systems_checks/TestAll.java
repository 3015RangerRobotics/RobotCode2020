package frc.robot.commands.systems_checks;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TestAll extends SequentialCommandGroup {
    public TestAll(){
        super(
                new ParallelCommandGroup(
                        new TestDrive(),
                        new TestHood(),
                        new TestShooter()
                ),
                new TestIntake(),
                new TestTurret(),
                new TestCarousel(),
                new TestClimber()
        );
    }
}
