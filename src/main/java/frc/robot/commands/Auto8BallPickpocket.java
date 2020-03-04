package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.auto_modules.DrivePathAndHarvest;
import frc.robot.commands.auto_modules.DriveStraightAndHarvest;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class Auto8BallPickpocket extends SequentialCommandGroup {

    public Auto8BallPickpocket() {
        super(
                new LimelightSwitchLEDMode(Limelight.LEDMode.LED_ON),
                new TurretHomePosition(),
                new DriveStraightAndHarvest(7.7, 12,10, -1.0),
                new ShooterAutoSpeed(),
                new TurretTurnToTargetHold(),
                new ParallelCommandGroup(
                        new DriveMotionProfile("pp_trench_to_shoot"),
                        new CG_HarvesterOfBalls().withTimeout(1)
                ),
                new CG_FireZeMissiles(),
                new DriveStraightAndHarvest(2.3,7,3, -1.0),
                new ShooterAutoSpeed(),
                new TurretTurnToTargetHold(),
                new DrivePathAndHarvest("pp_scoot", -1.0),
                new DriveStraightAndHarvest(1.5, 12, 10, -1.0),
                new DriveStraightAndHarvest(-2, 12, 10, -1.0),
                new CG_FireZeMissiles(),
                new ShooterStop(),
                new TurretToDefaultPosition()
//                new TurretTurnToTargetHold(),
//                new ShooterAutoSpeed(),
//                new CG_HarvesterOfBalls().withTimeout(1),
////                new DriveStraightAndHarvest(-2, 12,8, -0.75),
//                new ParallelCommandGroup(
//                        new DriveMotionProfile("pp_balls_to_shoot"),
//                        new CG_HarvesterOfBalls().withTimeout(1)
//                ),
//                new CG_FireZeMissiles(),
//                new TurretToDefaultPosition(),
//                new ShooterStop()
        );
    }
}
