package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.auto_modules.DrivePathAndHarvest;
import frc.robot.commands.auto_modules.DriveStraightAndHarvest;
import frc.robot.subsystems.Limelight;

public class Auto10Ball extends SequentialCommandGroup {

    public Auto10Ball() {
        super(
                new LimelightSwitchLEDMode(Limelight.LEDMode.LED_ON),
                new DriveMotionProfile("10_line_to_center"),
                new CG_HarvesterOfBalls().withTimeout(1),
                new TurretTurnToTargetHold(),
                new ShooterAutoSpeed(),
                new DrivePathAndHarvest("10_center_to_shoot", -1),
                new CG_FireZeMissiles(),
                new TurretTurnToTargetHold(),
                new ShooterAutoSpeed(),
                new ParallelDeadlineGroup(
                        new DriveMotionProfile("10_five_more"),
                        new BallHandlerHarvest(),
                        new SequentialCommandGroup(
                                new HarvesterSet(-0.75).withTimeout(3.5),
                                new HarvesterUp()
                        )
                ),
                new CG_HarvesterOfBalls().withTimeout(1),
                new DriveStraightAndHarvest(-8, 12, 10, -0.75),
                new CG_FireZeMissiles(),
                new ShooterStop(),
                new TurretToDefaultPosition()
        );
    }
}
