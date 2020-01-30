/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;


import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.BallHandler;


public class BallHandlerDefault extends CommandBase {
    BallHandler ballHandler = RobotContainer.ballHandler;
    Timer timer = new Timer();

    public BallHandlerDefault() {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(ballHandler);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        timer.start();
        timer.reset();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        BallHandler.State currentState = ballHandler.getState();
        if (currentState == BallHandler.State.kFillTo1 || currentState == BallHandler.State.kFillTo2 ||
                currentState == BallHandler.State.kFillTo3 || currentState == BallHandler.State.kFillTo4 ||
                currentState == BallHandler.State.kFillTo5) {
            if (timer.hasPeriodPassed(1.5)) {
                ballHandler.setPaused(true);
            }
        }


    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {

    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
