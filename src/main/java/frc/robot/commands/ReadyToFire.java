package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class ReadyToFire extends ParallelCommandGroup {

    public ReadyToFire(){
        addCommands(
                new ShooterStart()
        );
    }
}
