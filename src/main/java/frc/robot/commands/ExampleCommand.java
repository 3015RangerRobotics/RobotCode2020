/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.RobotContainer;
import frc.robot.subsystems.ExampleSubsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ExampleCommand extends CommandBase {
    ExampleSubsystem example = RobotContainer.exampleSubsystem;

    public ExampleCommand() {
        addRequirements(example);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        example.setMotor(ControlMode.PercentOutput, 0.5);
    }

    @Override
    public void end(boolean interrupted) {
        example.setMotor(ControlMode.PercentOutput, 0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
