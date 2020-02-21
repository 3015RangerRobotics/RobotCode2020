/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class LimelightSwitchPipelineSmart extends CommandBase {
    /**
     * Creates a new LimelightSwitchPipeline.
     */

    /**
     * Command to change the Limelight's current pipeline
     */
    public LimelightSwitchPipelineSmart() {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(RobotContainer.limelight);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        double distance = RobotContainer.limelight.getRobotToTargetDistance();
        if (distance <= Constants.LIMELIGHT_INNER_DISTANCE) {
            RobotContainer.limelight.setPipeline(1);
        }else if(distance >= Constants.LIMELIGHT_ZOOM_DISTANCE) {
            RobotContainer.limelight.setPipeline(2);
        }else{
            RobotContainer.limelight.setPipeline(0);
        }
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return true;
    }
}
