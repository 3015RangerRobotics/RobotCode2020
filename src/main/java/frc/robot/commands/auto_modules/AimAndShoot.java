package frc.robot.commands.auto_modules;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.*;
import frc.robot.subsystems.Limelight;

public class AimAndShoot extends SequentialCommandGroup {
    public AimAndShoot(){
        super(
                new LimelightSwitchLEDMode(Limelight.LEDMode.LED_ON),
                new WaitCommand(0.25),
                new CG_FireZeMissiles()
        );
    }

    public AimAndShoot(double shooter){
        super(
                new LimelightSwitchLEDMode(Limelight.LEDMode.LED_ON),
                new WaitCommand(0.25),
                new CG_FireZeMissiles(shooter)
        );
    }
}
