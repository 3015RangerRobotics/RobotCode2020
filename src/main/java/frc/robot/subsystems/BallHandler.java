/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/


package frc.robot.subsystems;



import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Add your docs here.
 */
public class BallHandler extends SubsystemBase{

    private  VictorSP forwardLeftMotor1;
    private  VictorSP backLeftMotor2;
    private  VictorSP backRightMotor3;
    private  VictorSP forwardRightMotor4;
    private VictorSP feedMotor5; 

    private DigitalInput ballSensor;

    private final double MOTOR_DOWN_SPEED = -1.0;
    private final double MOTOR_UP_SPEED = 1.0;

    private int ballCount = 0;

    



    public void BallHandler()
    {
        forwardLeftMotor1 = new VictorSP(0);
        backLeftMotor2 = new VictorSP(0);
        backRightMotor3 = new VictorSP(0);
        forwardRightMotor4 = new VictorSP(0);     
        feedMotor5 = new VictorSP(0);

        ballSensor = new DigitalInput(0);

        ballCount = 3;


    }

    
    public boolean isFull()
    {
        if(ballCount >= 5)
        {
            return true;

        }
        else
        {
            return false;
        }
    }
    public int getBallCount()
    {
        return ballCount;
    }
    public void addBall()
    {
        ballCount++;
    }
    public void removeBall()
    {
        ballCount--;
    }


    public void motorUp()
     {

        forwardLeftMotor1.set(MOTOR_UP_SPEED);
        backLeftMotor2.set(MOTOR_UP_SPEED);
        backRightMotor3.set(MOTOR_UP_SPEED);
        forwardRightMotor4.set(MOTOR_UP_SPEED);
        feedMotor5.set(MOTOR_UP_SPEED);

	}



    public void motorDown() 
    {

		forwardLeftMotor1.set(MOTOR_DOWN_SPEED);
        backLeftMotor2.set(MOTOR_DOWN_SPEED);
        backRightMotor3.set(MOTOR_DOWN_SPEED);
        forwardRightMotor4.set(MOTOR_DOWN_SPEED);
        feedMotor5.set(MOTOR_DOWN_SPEED);

	}



    public void motorStop() 
    {

		forwardLeftMotor1.set(0);
        backLeftMotor2.set(0);
        backRightMotor3.set(0);
        forwardRightMotor4.set(0);
        feedMotor5.set(0);

	}

}
