package frc.robot.commands.systems_checks;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TestAll extends SequentialCommandGroup {
    public TestAll(boolean testClimber){
        super(
                new ParallelCommandGroup(
                        new TestDrive(),
                        new TestHood(),
                        new TestShooter()
                ),
                new TestIntake(),
                new TestTurret(),
                new TestCarousel()
        );

        if(testClimber){
            this.addCommands(new TestClimber());
        }
    }
}
