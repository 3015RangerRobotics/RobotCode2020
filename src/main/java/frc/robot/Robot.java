package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.systems_checks.SystemCheckLayout;
import frc.robot.subsystems.Limelight;

public class Robot extends TimedRobot {
    private Command autoCommand;

    private RobotContainer robotContainer;

    @Override
    public void robotInit() {
        robotContainer = new RobotContainer();
        SystemCheckLayout.init();
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
        String alliance = "white";
        if(DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Red){
            alliance = "red";
        }else if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue){
            alliance = "blue";
        }
        SmartDashboard.putString("alliance", alliance);
        SmartDashboard.putNumber("time", (int) DriverStation.getInstance().getMatchTime());
    }

    @Override
    public void disabledInit() {
        RobotContainer.drive.enableCoastMode();
        RobotContainer.shooter.setStateOff();
        RobotContainer.turret.setStateDefault();
        RobotContainer.limelight.setLEDMode(Limelight.LEDMode.LED_OFF);
    }

    @Override
    public void disabledPeriodic() {

    }

    @Override
    public void autonomousInit() {
        RobotContainer.drive.enableBrakeMode();

        autoCommand = robotContainer.getAutonomousCommand();

        if (autoCommand != null) {
            autoCommand.schedule();
        }
    }

    @Override
    public void autonomousPeriodic() {

    }

    @Override
    public void teleopInit() {
        RobotContainer.drive.enableBrakeMode();

        if (autoCommand != null) {
            autoCommand.cancel();
        }
    }

    @Override
    public void teleopPeriodic() {

    }

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {

    }
}
