package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import motionlib.DriveProfile;
import frc.robot.RobotContainer;

public class DriveStraightTemp extends CommandBase {
    private volatile boolean isFinished = false;
    private DriveProfile profile;
    private volatile int i = 0;

    public DriveStraightTemp(double distance, double maxV, double maxA) {
        addRequirements(RobotContainer.drive);
        profile = new DriveProfile(distance, maxV, maxA);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        RobotContainer.drive.resetEncoders();
        RobotContainer.drive.resetIMU();
        isFinished = false;
        i = 0;

        new Thread(() -> {
            double lastTime = 0;

            while(!isFinished && DriverStation.getInstance().isEnabled()){
                if(Timer.getFPGATimestamp() >= lastTime + 0.01){
                    lastTime = Timer.getFPGATimestamp();
                    threadedExecute();
                }
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private synchronized void threadedExecute(){
        if(i < profile.getLeftProfile().getLength()){
            double goalVelL = profile.getLeftProfile().getVelocityEncoder(i) / 10;

            double goalVelR = profile.getRightProfile().getVelocityEncoder(i) / 10;

            double kP = Constants.DRIVE_P_TURN;
            double kV = (Constants.DRIVE_F / 1023);

            double percentL = (kV * goalVelL);
            double percentR = (kV * goalVelR);
            double turnOffset = kP * RobotContainer.drive.getAngle();

            percentL -= turnOffset;
            percentR += turnOffset;

            RobotContainer.drive.setMotorOutputs(ControlMode.PercentOutput, percentL, percentR);
            i++;
        }else{
            isFinished = true;
        }
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        RobotContainer.drive.setMotorOutputs(ControlMode.PercentOutput, 0, 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return isFinished;
    }
}