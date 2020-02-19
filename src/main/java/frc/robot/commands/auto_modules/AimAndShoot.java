package frc.robot.commands.auto_modules;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.*;
import frc.robot.subsystems.Limelight;

public class AimAndShoot extends ParallelDeadlineGroup {
    public AimAndShoot(double time){
        super(
                new WaitCommand(time),
                new SequentialCommandGroup(
                        new LimelightSwitchLEDMode(Limelight.LEDMode.LED_ON),
                        new WaitCommand(0.25),
//                                new DriveResetEncoders(),
                        new ParallelCommandGroup(
                                new TurretTurnToTarget(),
                                new ShooterStartAuto()
                        )
                ),
                new SequentialCommandGroup(
                        new WaitCommand(0.75),
                        new BallHandlerShoot()
                )
        );
    }
    public AimAndShoot(double time, double shooter){
        super(
                new WaitCommand(time),
                new SequentialCommandGroup(
                        new LimelightSwitchLEDMode(Limelight.LEDMode.LED_ON),
                        new WaitCommand(0.25),
//                                new DriveResetEncoders(),
                        new ParallelCommandGroup(
                                new TurretTurnToTarget(),
                                new ShooterStart(shooter)
                        )
                ),
                new SequentialCommandGroup(
                        new WaitCommand(0.75),
                        new BallHandlerShoot()
                )
        );
    }
}
