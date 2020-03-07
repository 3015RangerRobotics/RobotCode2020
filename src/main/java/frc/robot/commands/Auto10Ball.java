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
                new HarvesterDown(),
                new TurretTurnToTargetHold(),
                new ShooterAutoSpeed(),
                new ParallelDeadlineGroup(
                        new SequentialCommandGroup(
                                new DriveMotionProfile("10_line_to_center"),
//                                new WaitCommand(0.5),
                                new DriveMotionProfile("10_center_to_shoot")
                            ),
                        new CG_HarvesterOfBalls()
                        ),
                new CG_FireZeMissiles(),
                new TurretTurnToTargetHold(),
                new ShooterAutoSpeed(),
//                new ParallelDeadlineGroup(
//                        new SequentialCommandGroup(
                                new DrivePathAndHarvest("10_five_more", -1),
                                new WaitCommand(0.25),
                                new DriveStraightAndHarvest(-8, 12, 12, -1),
//                                ),
//                        new BallHandlerHarvest(),
//                        new SequentialCommandGroup(
//                                new HarvesterSet(-0.75).withTimeout(2.85),
//                                new HarvesterUp(),
//                                new WaitCommand(0.5),
//                                new HarvesterDown(),
//                                new HarvesterSet(-0.75)
//                         )
//                ),
                new CG_FireZeMissiles(),
                new ShooterStop(),
                new TurretToDefaultPosition()
        );
    }
}
