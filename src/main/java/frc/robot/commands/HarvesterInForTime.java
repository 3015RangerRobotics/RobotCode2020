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

public class HarvesterInForTime extends CommandBase {

    private double speed;
    private double time;
    private Timer timer;
    /**
     * Creates a new HarvesterIn.
     */
    public HarvesterInForTime(double speed, double time) {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(RobotContainer.harvester);
        this.speed = speed;
        this.time = time;
        this.timer = new Timer();
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        timer.reset();
        timer.start();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        RobotContainer.harvester.harvesterIn(speed);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        RobotContainer.harvester.harvesterStop();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return timer.hasPeriodPassed(time);
    }
}
