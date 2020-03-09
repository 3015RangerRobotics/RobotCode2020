package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.subsystems.BallHandler;
import frc.robot.subsystems.BallHandler.State;

public class CreateShooterPlan extends CommandBase {

    public CreateShooterPlan() {
    }

    @Override
    public void initialize() {
        SequentialCommandGroup commandGroup = new SequentialCommandGroup();
        boolean[] quality = RobotContainer.ballQuality.getAllBallQuality();
        commandGroup.addCommands(new BallHandlerSettle(quality[0]), new ShooterWaitUntilPrimed());
        int count = 1;
        for(int i = 1; i < 5; i++) {
            if(quality[i] == quality[i-1]) {
                count++;
                if(i == 4) {
                    commandGroup.addCommands(new BallHandlerShoot(0, count));
                    break;
                }
            } else {
                commandGroup.addCommands(new BallHandlerShoot(0, count), new BallHandlerSettle(quality[i]), new ShooterWaitUntilPrimed());
            }
        }
        new CG_FireZeMissiles(commandGroup).schedule();
    }

    @Override
    public void execute() {

    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
