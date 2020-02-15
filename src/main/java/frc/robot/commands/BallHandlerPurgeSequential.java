/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.BallHandler;
import frc.robot.subsystems.BallHandler.State;



public class BallHandlerPurgeSequential extends CommandBase {
  /**
   * Creates a new BallHandlerPurgeSequential.
   */
  BallHandler  ballHandler = RobotContainer.ballHandler;

  public BallHandlerPurgeSequential() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(ballHandler);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // ballHandler.setState(BallHandler.State.kPurgeBall5);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // if(ballHandler.getState() == State.kPurgeBall5 && !ballHandler.isSwitch5Pressed()) // If there is not a ball on switch 5 and the state is kPurgeBall5, set the state to kPurge4
    // {
    //   ballHandler.setState(BallHandler.State.kPurgeBall4);
    // }
    // else if(ballHandler.getState() == State.kPurgeBall4 && !ballHandler.isSwitch4Pressed()) // If there is not a ball on switch 4 and the state is kPurgeBall4, set the state to kPurge3
    // {
    //   ballHandler.setState(BallHandler.State.kPurgeBall3);
    // }
    // else if(ballHandler.getState() == State.kPurgeBall3 && !ballHandler.isSwitch3Pressed()) // If there is not a ball on switch 3 and the state is kPurgeBall3, set the state to kPurge2
    // {
    //   ballHandler.setState(BallHandler.State.kPurgeBall2);
    // }
    // else if(ballHandler.getState() == State.kShootBall2 && !ballHandler.isSwitch2Pressed()) // If there is not a ball on switch 2 and the state is kPurgeBall2, set the state to kPurge
    // { 
      ballHandler.setState(BallHandler.State.kPurge);
    // }
    
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
