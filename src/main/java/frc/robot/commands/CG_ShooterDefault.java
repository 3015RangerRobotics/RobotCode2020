package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Limelight;

public class CG_ShooterDefault extends SequentialCommandGroup {

    public CG_ShooterDefault() {
        addCommands(
                new LimelightSwitchLEDMode(Limelight.LEDMode.LED_OFF),
                new HoodDown(),
                new TurretToDefaultPosition(),
                new ShooterStop(),
                new DriverRumbleOff()
        );
    }
}
