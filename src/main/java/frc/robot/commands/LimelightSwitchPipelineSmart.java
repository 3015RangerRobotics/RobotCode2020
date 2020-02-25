package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class LimelightSwitchPipelineSmart extends CommandBase {
    /**
     * Command to change the Limelight's current pipeline
     */
    public LimelightSwitchPipelineSmart() {
        addRequirements(RobotContainer.limelight);
    }

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
