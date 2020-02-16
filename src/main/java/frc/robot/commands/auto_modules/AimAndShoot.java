package frc.robot.commands.auto_modules;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.BallHandlerShoot;
import frc.robot.commands.ShooterStartAuto;
import frc.robot.commands.TurretTurnToTarget;

public class AimAndShoot extends ParallelDeadlineGroup {
    public AimAndShoot(){
        super(
                new WaitCommand(3),
                new SequentialCommandGroup(
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
}
