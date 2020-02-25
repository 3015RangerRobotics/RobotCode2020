package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class ShooterStartAuto extends CommandBase {
    double rpm = 5400;
    boolean constantUpdate;

    public ShooterStartAuto() {
        addRequirements(RobotContainer.shooter);
        constantUpdate = true;
    }

    public ShooterStartAuto(boolean constantUpdate) {
        addRequirements(RobotContainer.shooter);
        this.constantUpdate = constantUpdate;
    }

    @Override
    public void initialize() {
        double d = RobotContainer.limelight.getRobotToTargetDistance();
        double turretPos = RobotContainer.turret.getPosition() + RobotContainer.limelight.getTargetAngleX();
        rpm = 4222.866701 + (110.34724 * d) + (-1.51320429 * d * d) + (1.5 * Math.abs(turretPos)); //Average ball
    }

    @Override
    public void execute() {
        double d = RobotContainer.limelight.getRobotToTargetDistance();
        double turretPos = RobotContainer.turret.getPosition() + RobotContainer.limelight.getTargetAngleX();
        if (constantUpdate) {
//            rpm = 7430.1186 + (-255.07933*d) + (7.2472131*d*d); //Perfect ball
            rpm = 4222.866701 + (110.34724 * d) + (-1.51320429 * d * d); //Average ball
        }
//        System.out.println("shooter," + rpm + "," + RobotContainer.shooter.getRPM());
        RobotContainer.shooter.set(ControlMode.Velocity, (rpm + (1.5 * Math.abs(turretPos))) / 10 / 60 * Constants.SHOOTER_PULSES_PER_ROTATION);
    }

    @Override
    public void end(boolean interrupted) {
        RobotContainer.shooter.set(ControlMode.PercentOutput, 0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
