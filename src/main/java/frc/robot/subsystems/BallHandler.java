/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Add your docs here.
 */
public class BallHandler extends SubsystemBase {

    private VictorSP motor1;
    private VictorSP motor2;
    private VictorSP motor3;
    private VictorSP motor4;
    private VictorSP motor5;

    private DigitalInput switch1;
    private DigitalInput switch2;
    private DigitalInput switch3;
    private DigitalInput switch4;
    private DigitalInput switch5;

    private DoubleSolenoid tiltControl;

    private PowerDistributionPanel pdp;
    public final double MOTOR_IN_SPEED1 = .75; //.75
    public final double MOTOR_IN_SPEED2 = .68;  //.68
    public final double MOTOR_IN_SPEED3 = .62;  //.62
    public final double MOTOR_IN_SPEED4 = .55;  //.55
    public final double MOTOR_IN_SPEED5 = .75;   //.5
    public final double MOTOR_OUT_SPEED1 = -.5; 
    public final double MOTOR_OUT_SPEED2 = -.55;
    public final double MOTOR_OUT_SPEED3 = -.62;
    public final double MOTOR_OUT_SPEED4 = -.68;
    public final double MOTOR_OUT_SPEED5 = -.75;

    public final double MOTOR_SHOOT_SPEED1 = 1.0;
    public final double MOTOR_SHOOT_SPEED2 = 0.5;
    public final double MOTOR_SHOOT_SPEED3 = 0.4;
    public final double MOTOR_SHOOT_SPEED4 = 0.4;
    public final double MOTOR_SHOOT_SPEED5 = 0.4;
    public final double MOTOR_OFF_SPEED = 0.0;
    public enum State{
        kPurge,
        kShootBall1,
        kShootBall2,
        kShootBall3,
        kShootBall4,
        kShootBall5,
        kFillTo1,
        kFillTo2,
        kFillTo3,
        kFillTo4,
        kFillTo5,
        kOff
    }
    public State state = State.kOff;

    public BallHandler() {
        motor1 = new VictorSP(0); //Motor closest to shooter, used to push balls up to turret
        motor2 = new VictorSP(1); //2nd motor closest to shooter
        motor3 = new VictorSP(2); //3rd motor closest to shooter 
        motor4 = new VictorSP(3); //4th motor closest to shooter
        motor5 = new VictorSP(4); //5th motor closest to shooter

        tiltControl = new DoubleSolenoid(7,6);

        switch1 = new DigitalInput(2); //assigned to motor 1
        switch2 = new DigitalInput(3); //assigned to motor 2
        switch3 = new DigitalInput(4); //assigned to motor 3
        switch4 = new DigitalInput(5); //assigned to motor 4
        switch5 = new DigitalInput(6); //assigned to motor 5

        pdp = new PowerDistributionPanel(0);

        motor2.setInverted(true);
        motor3.setInverted(true);
        motor4.setInverted(true);
        motor5.setInverted(true);
    }

    @Override
    public void periodic(){
        double[] speeds;
        switch (state)
        {   
            case kPurge:
                //Run everything in reverse(spit out balls)
                speeds = new double[]
                    {MOTOR_OUT_SPEED1,MOTOR_OUT_SPEED2,MOTOR_OUT_SPEED3,MOTOR_OUT_SPEED4,MOTOR_OUT_SPEED5};
                break;
            case kShootBall1:
                //Fires the first ball
                speeds = new double[]
                    {MOTOR_SHOOT_SPEED1,MOTOR_OFF_SPEED,MOTOR_OFF_SPEED,MOTOR_OFF_SPEED,MOTOR_OFF_SPEED};
                break;
            case kShootBall2:
                //Fires balls 1 and 2
                speeds = new double[]
                    {MOTOR_SHOOT_SPEED1,MOTOR_SHOOT_SPEED2,MOTOR_OFF_SPEED,MOTOR_OFF_SPEED,MOTOR_OFF_SPEED};
                break; 
            case kShootBall3:
                //Fires balls 1 - 3
                speeds = new double[]
                    {MOTOR_SHOOT_SPEED1,MOTOR_SHOOT_SPEED2,MOTOR_SHOOT_SPEED3,MOTOR_OFF_SPEED,MOTOR_OFF_SPEED};
                break;
            case kShootBall4:
                //Fires balls 1-4
                speeds = new double[]
                    {MOTOR_SHOOT_SPEED1,MOTOR_SHOOT_SPEED2,MOTOR_SHOOT_SPEED3,MOTOR_SHOOT_SPEED4,MOTOR_OFF_SPEED};
                break;   
            case kShootBall5:
                //Fires 1-5
                speeds = new double[]
                    {MOTOR_SHOOT_SPEED1,MOTOR_SHOOT_SPEED2,MOTOR_SHOOT_SPEED3,MOTOR_SHOOT_SPEED4,MOTOR_SHOOT_SPEED5};
                break; 
            case kFillTo1:
                //Fill balls until 1 is pressed
                speeds = new double[]
                    {MOTOR_IN_SPEED1,MOTOR_IN_SPEED2,MOTOR_IN_SPEED3,MOTOR_IN_SPEED4,MOTOR_IN_SPEED5};
                if(isSwitch1Pressed()){
                    //if switch pressed, change state, and fall through to that state
                    state = State.kFillTo2;
                }else{
                    break;
                }
            case kFillTo2:
                //Fill balls until 2 is pressed
                speeds = new double[]
                    {MOTOR_OFF_SPEED,MOTOR_IN_SPEED2,MOTOR_IN_SPEED3,MOTOR_IN_SPEED4,MOTOR_IN_SPEED5};
                if(isSwitch2Pressed()){
                    //if switch pressed, change state, and fall through to that state
                    state = State.kFillTo3;
                }else{
                    break;
                }
            case kFillTo3:
                //Fill balls until 3 is pressed
                speeds = new double[]
                    {MOTOR_OFF_SPEED,MOTOR_OFF_SPEED,MOTOR_IN_SPEED3,MOTOR_IN_SPEED4,MOTOR_IN_SPEED5};
                if(isSwitch3Pressed()){
                    //if switch pressed, change state, and fall through to that state
                    state = State.kFillTo4;
                }else{
                    break;
                }
                
            case kFillTo4:
                //Fill balls until 4 is pressed
                speeds = new double[]
                    {MOTOR_OFF_SPEED,MOTOR_OFF_SPEED,MOTOR_OFF_SPEED,MOTOR_IN_SPEED4,MOTOR_IN_SPEED5};
                if(isSwitch4Pressed()){
                    //if switch pressed, change state, and fall through to that state
                    state = State.kFillTo5;
                }else{
                    break;
                }    
                // break;
            case kFillTo5:
                //Fill balls until 5 is pressed
                speeds = new double[]
                    {MOTOR_OFF_SPEED,MOTOR_OFF_SPEED,MOTOR_OFF_SPEED,MOTOR_OFF_SPEED,MOTOR_IN_SPEED5};
                if(isSwitch5Pressed()){
                    //if switch pressed, change state, and fall through to that state
                    state = State.kOff;
                }else{
                    break;
                }    
            case kOff:
                //Turns all motors off
                speeds = new double[]
                    {MOTOR_OFF_SPEED,MOTOR_OFF_SPEED,MOTOR_OFF_SPEED,MOTOR_OFF_SPEED,MOTOR_OFF_SPEED};
                break;
            default:
                //Turns all motors off
                speeds = new double[]
                    {MOTOR_OFF_SPEED,MOTOR_OFF_SPEED,MOTOR_OFF_SPEED,MOTOR_OFF_SPEED,MOTOR_OFF_SPEED};
        }
        setAllMotors(speeds);


    }
    /**
     * Sets the state of the motor based on desired operation
     * @param newState The new State you would like to change to
     */
    public void setState(State newState)
    {
        this.state = newState;
    }
    /**
     * gets the current state of the enumerator
     * @return state of enumerator
     */
    public State getState()
    {
        return state;
    }
    /**
     * Sets all five motors to a specific speed. 
     * @param speeds An array of doubles that has a length of 5 used to set all of the motor speeds
     */
    private void setAllMotors(double[] speeds){
        //set motors for percent ouput
        motor1.set(speeds[0]);
        motor2.set(speeds[1]);
        motor3.set(speeds[2]);
        motor4.set(speeds[3]);
        motor5.set(speeds[4]);
        //faux voltage control
        // double voltAdjust = 12/ pdp.getVoltage();
        // motor1.set(speeds[0] * voltAdjust);
        // motor2.set(speeds[1] * voltAdjust);
        // motor3.set(speeds[2] * voltAdjust);
        // motor4.set(speeds[3] * voltAdjust);
        // motor5.set(speeds[4] * voltAdjust);
    }

    /**
     * Used to determine the state of the switch closest to the shooter (a.k.a switch 1)
     * @return state of switch, true if pressed.
     */
    public boolean isSwitch1Pressed() {
        if (switch1.get()) {
            return false;
        } else {
            return true;
        }
    }
    /**
     * Used to determine the state of the second switch closest to the shooter (a.k.a switch 2)
     * @return state of switch, true if pressed.
     */
    public boolean isSwitch2Pressed() {
        if (switch2.get()) {
            return false;
        } else {
            return true;
        }

    }
    /**
     * Used to determine the state of the third switch closest to the shooter (a.k.a switch 3)
     * @return state of switch, true if pressed.
     */
    public boolean isSwitch3Pressed() {
        if (switch3.get()) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * Used to determine the state of the fourth switch closest to the shooter (a.k.a switch 4)
     * @return state of switch, true if pressed.
     */
    public boolean isSwitch4Pressed() {
        if (switch4.get()) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * Used to determine the state of the farthest switch from the shooter (a.k.a switch 5)
     * @return state of switch, true if pressed.
     */
    public boolean isSwitch5Pressed() {
        if (switch5.get()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Uses a double solonoid to redirect air into a piston. This piston causes the arm to move in or out.
     * 
     */
    public void harvesterIn()
    {
        //TODO: change kForward to kReverse if havesterIn() causes the piston arm to out
        tiltControl.set(DoubleSolenoid.Value.kForward);
    }
    public void harvsterOut()
    {
        //TODO: change kRelease to kForward if havesterIn() causes the piston arm to in
        tiltControl.set(DoubleSolenoid.Value.kReverse);
    }
}
