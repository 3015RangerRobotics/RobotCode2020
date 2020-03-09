package frc.robot.commands.systems_checks;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TestAll extends SequentialCommandGroup {
    public TestAll(){
        super(
                new TestDrive(),
                new TestIntake(),
                new TestTurret(),
                new TestHood()
        );
    }
}
