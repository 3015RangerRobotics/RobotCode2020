/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motion.BufferedTrajectoryPointStream;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class DriveMotionProfileOld extends CommandBase {
    private volatile boolean isFinished = false;
    private double[][] leftMotion;
    private double[][] rightMotion;
    private volatile int i = 0;
    private volatile double prevErrorL = 0;
    private volatile double prevErrorR = 0;

    public DriveMotionProfileOld(String pathName) {
        addRequirements(RobotContainer.drive);
        this.leftMotion = RobotContainer.drive.loadProfile(pathName + "_left");
        this.rightMotion = RobotContainer.drive.loadProfile(pathName + "_right");
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        RobotContainer.drive.resetEncoders();
        isFinished = false;
        i = 0;
        prevErrorL = 0;
        prevErrorR = 0;

        System.out.println("======================================");

        if (leftMotion.length != rightMotion.length) {
            System.out.println("Left and right profiles not of equal length!");
            this.cancel();
            return;
        }

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
        if(i < leftMotion.length){
            double goalPosL = leftMotion[i][0];
            double goalVelL = leftMotion[i][1];
//            double goalAccL = leftMotion[i][2];

            double goalPosR = rightMotion[i][0];
            double goalVelR = rightMotion[i][1];
//            double goalAccR = rightMotion[i][2];

            double errorL = goalPosL - RobotContainer.drive.getLeftPositionRaw();
            double errorDerivL = ((errorL - prevErrorL) / 0.1) - goalVelL;

            double errorR = goalPosR - RobotContainer.drive.getRightPositionRaw();
            double errorDerivR = ((errorR - prevErrorR) / 0.1) - goalVelR;

            double kP = Constants.DRIVE_P;
            double kD = Constants.DRIVE_D;
            double kV = (Constants.DRIVE_F / 1023);
            double kA = 0;

            double percentL = (kP * errorL) + (kD * errorDerivL) + (kV * goalVelL);
            double percentR = (kP * errorR) + (kD * errorDerivR) + (kV * goalVelR);

            System.out.println("drive_train_left," + goalPosL + "," + RobotContainer.drive.getLeftPositionRaw());
            System.out.println("drive_train_right," + goalPosR + "," + RobotContainer.drive.getRightPositionRaw());

            prevErrorL = errorL;
            prevErrorR = errorR;

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
