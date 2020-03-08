package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.auto_modules.DrivePathAndHarvest;
import frc.robot.commands.auto_modules.DriveStraightAndHarvest;
import frc.robot.subsystems.Limelight;

public class Auto10Ball extends SequentialCommandGroup {

    public Auto10Ball() {
        super(
                new LimelightSwitchLEDMode(Limelight.LEDMode.LED_ON),
                new LimelightSwitchPipeline(1),
                new HarvesterDown(),
                new TurretHomePosition(),
                new ParallelDeadlineGroup(
                        new SequentialCommandGroup(
                                new DriveMotionProfile("10_line_to_center"),
                                new ShooterAutoSpeed(),
                                new TurretTurnToTargetHold(),
                                new DriveMotionProfile("10_center_to_shoot")
                        ),
                        new CG_HarvesterOfBalls(-0.8)
                ),
                new CG_FireZeMissiles(),
                new ShooterAutoSpeed(),
                new DrivePathAndHarvest("10_five_more2", -1),
                new TurretTurnToTargetHold(),
                new DrivePathAndHarvest("10_trench_to_shoot2", -1),
                new CG_FireZeMissiles(),
                new ShooterStop(),
                new TurretToDefaultPosition(),
                new LimelightSwitchPipeline(0)
        );
    }
}
