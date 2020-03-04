package frc.robot.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

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

    private int ballCounter = 0;

    private static boolean isPaused = false;

    public enum State {
        kPurgeBall1,
        kPurgeBall2,
        kPurgeBall3,
        kPurgeBall4,
        kPurgeBall5,
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
        motor1 = new VictorSP(Constants.HANDLER_MOTOR1); //Motor closest to shooter, used to push balls up to turret
        motor2 = new VictorSP(Constants.HANDLER_MOTOR2); //2nd motor closest to shooter
        motor3 = new VictorSP(Constants.HANDLER_MOTOR3); //3rd motor closest to shooter
        motor4 = new VictorSP(Constants.HANDLER_MOTOR4); //4th motor closest to shooter
        motor5 = new VictorSP(Constants.HANDLER_MOTOR5); //5th motor closest to shooter

        switch1 = new DigitalInput(Constants.HANDLER_SWITCH1); //assigned to motor 1
        switch2 = new DigitalInput(Constants.HANDLER_SWITCH2); //assigned to motor 2
        switch3 = new DigitalInput(Constants.HANDLER_SWITCH3); //assigned to motor 3
        switch4 = new DigitalInput(Constants.HANDLER_SWITCH4); //assigned to motor 4
        switch5 = new DigitalInput(Constants.HANDLER_SWITCH5); //assigned to motor 5

        motor1.setInverted(false);
        motor2.setInverted(true);
        motor3.setInverted(false);
        motor4.setInverted(true);
        motor5.setInverted(true);
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("switch1", isSwitch1Pressed());
        SmartDashboard.putBoolean("switch2", isSwitch2Pressed());
        SmartDashboard.putBoolean("switch3", isSwitch3Pressed());
        SmartDashboard.putBoolean("switch4", isSwitch4Pressed());
        SmartDashboard.putBoolean("switch5", isSwitch5Pressed());

        double[] speeds;
        switch (state) {
            case kPurgeBall5:
                //Fill balls until 1 is pressed
                speeds = new double[]
                        {0,0,0,0, Constants.HANDLER_MOTOR_OUT_SPEED1};
                if (!isSwitch5Pressed()) {
                    //if switch pressed, change state, and fall through to that state
                    state = State.kPurgeBall4;
                } else {
                    break;
                }
            case kPurgeBall4:
                //Fill balls until 2 is pressed
                speeds = new double[]
                        {0,0,0, Constants.HANDLER_MOTOR_OUT_SPEED4, Constants.HANDLER_MOTOR_OUT_SPEED5};
                if (!isSwitch4Pressed()) {
                    //if switch pressed, change state, and fall through to that state
                    state = State.kPurgeBall3;
                } else {
                    break;
                }
            case kPurgeBall3:
                //Fill balls until 3 is pressed
                speeds = new double[]
                        {0,0, Constants.HANDLER_MOTOR_OUT_SPEED3, Constants.HANDLER_MOTOR_OUT_SPEED4,
                                Constants.HANDLER_MOTOR_OUT_SPEED5};
                if (!isSwitch3Pressed()) {
                    //if switch pressed, change state, and fall through to that state
                    state = State.kPurgeBall2;
                } else {
                    break;
                }

            case kPurgeBall2:
                //Fill balls until 4 is pressed
                speeds = new double[]
                        {0, Constants.HANDLER_MOTOR_OUT_SPEED2, Constants.HANDLER_MOTOR_OUT_SPEED3,
                                Constants.HANDLER_MOTOR_OUT_SPEED4, Constants.HANDLER_MOTOR_OUT_SPEED5};
                if (!isSwitch2Pressed()) {
                    //if switch pressed, change state, and fall through to that state
                    state = State.kPurgeBall1;
                } else {
                    break;
                }
                // break;
            case kPurgeBall1:
                //Fill balls until 5 is pressed
                speeds = new double[]
                        {Constants.HANDLER_MOTOR_OUT_SPEED1, Constants.HANDLER_MOTOR_OUT_SPEED2,
                                Constants.HANDLER_MOTOR_OUT_SPEED3, Constants.HANDLER_MOTOR_OUT_SPEED4,
                                Constants.HANDLER_MOTOR_OUT_SPEED5};
                    break;
            case kShootBall1:
                //Fires the first ball
                speeds = new double[]
                        {Constants.HANDLER_MOTOR_SHOOT_SPEED1, Constants.HANDLER_MOTOR_OFF_SPEED,
                                Constants.HANDLER_MOTOR_OFF_SPEED, Constants.HANDLER_MOTOR_OFF_SPEED,
                                Constants.HANDLER_MOTOR_OFF_SPEED};
                break;
            case kShootBall2:
                //Fires balls 1 and 2
                speeds = new double[]
                        {Constants.HANDLER_MOTOR_SHOOT_SPEED1, Constants.HANDLER_MOTOR_SHOOT_SPEED2,
                                Constants.HANDLER_MOTOR_OFF_SPEED, Constants.HANDLER_MOTOR_OFF_SPEED,
                                Constants.HANDLER_MOTOR_OFF_SPEED};
                break;
            case kShootBall3:
                //Fires balls 1 - 3
                speeds = new double[]
                        {Constants.HANDLER_MOTOR_SHOOT_SPEED1, Constants.HANDLER_MOTOR_SHOOT_SPEED2,
                                Constants.HANDLER_MOTOR_SHOOT_SPEED3, Constants.HANDLER_MOTOR_OFF_SPEED,
                                Constants.HANDLER_MOTOR_OFF_SPEED};
                break;
            case kShootBall4:
                //Fires balls 1-4
                speeds = new double[]
                        {Constants.HANDLER_MOTOR_SHOOT_SPEED1, Constants.HANDLER_MOTOR_SHOOT_SPEED2,
                                Constants.HANDLER_MOTOR_SHOOT_SPEED3, Constants.HANDLER_MOTOR_SHOOT_SPEED4,
                                Constants.HANDLER_MOTOR_OFF_SPEED};
                break;
            case kShootBall5:
                //Fires 1-5
                speeds = new double[]
                        {Constants.HANDLER_MOTOR_SHOOT_SPEED1, Constants.HANDLER_MOTOR_SHOOT_SPEED2,
                                Constants.HANDLER_MOTOR_SHOOT_SPEED3, Constants.HANDLER_MOTOR_SHOOT_SPEED4,
                                Constants.HANDLER_MOTOR_SHOOT_SPEED5};
                break;
            case kFillTo1:
                //Fill balls until 1 is pressed
                if (ballCounter >= 1){
                    state = State.kFillTo2;
                }else {
                    speeds = new double[]
                            {Constants.HANDLER_MOTOR_IN_SPEED1, 1,
                                    1, 1,
                                    1};
                    if (isSwitch1Pressed()) {
                        //if switch pressed, change state, and fall through to that state
                        ballCounter = 1;
                        state = State.kFillTo2;
                    } else {
                        break;
                    }
                }
            case kFillTo2:
                if (ballCounter >= 2){
                    state = State.kFillTo3;
                }else {
                    //Fill balls until 2 is pressed
                    speeds = new double[]
                            {0, .4,
                                    1, 1,
                                    1};
                    if (isSwitch2Pressed()) {
                        ballCounter = 2;
                        //if switch pressed, change state, and fall through to that state
                        state = State.kFillTo3;
                    } else {
                        break;
                    }
                }
            case kFillTo3:
                if (ballCounter >= 3){
                    state = State.kFillTo4;
                }else {
                    //Fill balls until 3 is pressed
                    speeds = new double[]
                            {0, 0,
                                    .4, 1,
                                    1};
                    if (isSwitch3Pressed()) {
                        ballCounter = 3;
                        //if switch pressed, change state, and fall through to that state
                        state = State.kFillTo4;
                    } else {
                        break;
                    }
                }

            case kFillTo4:
                if (ballCounter >=4){
                    state = State.kFillTo5;
                }else {
                    //Fill balls until 4 is pressed
                    speeds = new double[]
                            {0, 0,
                                    0, .4,
                                    1};
                    if (isSwitch4Pressed()) {
                        ballCounter = 4;
                        //if switch pressed, change state, and fall through to that state
                        state = State.kFillTo5;
                    } else {
                        break;
                    }
                }
                // break;
            case kFillTo5:
                if(ballCounter >= 5){
                    state = State.kOff;
                }else {
                    //Fill balls until 5 is pressed
                    speeds = new double[]
                            {0, 0,
                                    0, 0,
                                    .3};
                    if (isSwitch5Pressed()) {
                        ballCounter = 5;
                        //if switch pressed, change state, and fall through to that state
                        state = State.kOff;
                    } else {
                        break;
                    }
                }
            case kOff:
                //Turns all motors off
                speeds = new double[]
                        {Constants.HANDLER_MOTOR_OFF_SPEED, Constants.HANDLER_MOTOR_OFF_SPEED,
                                Constants.HANDLER_MOTOR_OFF_SPEED, Constants.HANDLER_MOTOR_OFF_SPEED,
                                Constants.HANDLER_MOTOR_OFF_SPEED};
                break;
            default:
                //Turns all motors off
                speeds = new double[]
                        {Constants.HANDLER_MOTOR_OFF_SPEED, Constants.HANDLER_MOTOR_OFF_SPEED,
                                Constants.HANDLER_MOTOR_OFF_SPEED, Constants.HANDLER_MOTOR_OFF_SPEED,
                                Constants.HANDLER_MOTOR_OFF_SPEED};
        }
        if (isPaused) {
            speeds = new double[]
                    {Constants.HANDLER_MOTOR_OFF_SPEED, Constants.HANDLER_MOTOR_OFF_SPEED,
                            Constants.HANDLER_MOTOR_OFF_SPEED, Constants.HANDLER_MOTOR_OFF_SPEED,
                            Constants.HANDLER_MOTOR_OFF_SPEED};
        }
        setAllMotors(speeds);

    }

    /**
     * Sets the state of the motor based on desired operation
     *
     * @param newState The new State you would like to change to
     */
    public void setState(State newState) {
        this.state = newState;
    }

    public void setBallCounter(int ballCounter){
        this.ballCounter = ballCounter;
    }

    /**
     * gets the current state of the enumerator
     *
     * @return state of enumerator
     */
    public State getState() {
        return state;
    }

    /**
     * Get if the ball handler is in a paused state
     * @return is the ball handler paused
     */
    public boolean isPaused() {
        return isPaused;
    }

    /**
     * Set the paused state
     * @param pausedState Should the ball handler be paused
     */
    public void setPaused(boolean pausedState) {
        isPaused = pausedState;
    }

    /**
     * Sets all five motors to a specific speed.
     *
     * @param speeds An array of doubles that has a length of 5 used to set all of the motor speeds
     */
    private void setAllMotors(double[] speeds) {
        //set motors for percent ouput
        if (isPaused()) {
            motor1.set(0);
            motor2.set(0);
            motor3.set(0);
            motor4.set(0);
            motor5.set(0);
        } else {
            motor1.set(speeds[0]);
            motor2.set(speeds[1]);
            motor3.set(speeds[2]);
            motor4.set(speeds[3]);
            motor5.set(speeds[4]);
        }
    }

    /**
     * Used to determine the state of the switch closest to the shooter (a.k.a switch 1)
     *
     * @return state of switch, true if pressed.
     */
    public boolean isSwitch1Pressed() {
        return !switch1.get();
    }

    /**
     * Used to determine the state of the second switch closest to the shooter (a.k.a switch 2)
     *
     * @return state of switch, true if pressed.
     */
    public boolean isSwitch2Pressed() {
        return !switch2.get();
    }

    /**
     * Used to determine the state of the third switch closest to the shooter (a.k.a switch 3)
     *
     * @return state of switch, true if pressed.
     */
    public boolean isSwitch3Pressed() {
        return !switch3.get();
    }

    /**
     * Used to determine the state of the fourth switch closest to the shooter (a.k.a switch 4)
     *
     * @return state of switch, true if pressed.
     */
    public boolean isSwitch4Pressed() {
        return !switch4.get();
    }

    /**
     * Used to determine the state of the farthest switch from the shooter (a.k.a switch 5)
     *
     * @return state of switch, true if pressed.
     */
    public boolean isSwitch5Pressed() {
        return !switch5.get();
    }
}
