/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;
import frc.robot.subsystems.BallHandler;
import frc.robot.RobotContainer;



import edu.wpi.first.wpilibj2.command.CommandBase;

public class BallHandlerShootOneBall extends CommandBase {
  BallHandler  ballHandler = RobotContainer.ballHandler;
  /**
   * Creates a new BallHandlerShootOneBall.
   */
  public BallHandlerShootOneBall() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(ballHandler);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if(ballHandler.isSwitch1Pressed()) // If switch 1 is pressed (aka there is a ball there), fire this ball 
    {
      ballHandler.setState(BallHandler.State.kShootBall1);
    }
    else if(ballHandler.isSwitch2Pressed()) // Else if switch 2 is pressed (aka there is a ball here), instead fire this ball 
    {
      ballHandler.setState(BallHandler.State.kShootBall2);
    }
    else if(ballHandler.isSwitch3Pressed()) // Else if switch 3 is pressed (aka there is a ball here), instead fire this ball 
    {
      ballHandler.setState(BallHandler.State.kShootBall3);
    }
    else if(ballHandler.isSwitch4Pressed())// Else if switch 4 is pressed (aka there is a ball here), instead fire this ball 
    {
      ballHandler.setState(BallHandler.State.kShootBall4);
    }
    else // Else fire this ball 
    {
      ballHandler.setState(BallHandler.State.kShootBall5);
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
  }
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    ballHandler.setState(BallHandler.State.kOff);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
