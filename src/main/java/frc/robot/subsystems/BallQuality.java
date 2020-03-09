package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogOutput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import lib.data_analyzer.DataAnalyzer;

public class BallQuality extends SubsystemBase {
    private AnalogOutput pressureSensor;
    private DataAnalyzer data;
    private Timer timer;

    private int ballCounter = 0;

    private static boolean isPaused = false;
    private static boolean[] quality = {true, true, true, true, true};

    public enum State {
        kOff,
        kReading,
        kWaiting
    }

    public State state = State.kOff;

    public BallQuality() {
        pressureSensor = new AnalogOutput(Constants.BALL_PRESSURE_SENSOR);
        data = new DataAnalyzer();
        timer = new Timer();
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("ball1good", isBall1Good());
        SmartDashboard.putBoolean("ball2good", isBall2Good());
        SmartDashboard.putBoolean("ball3good", isBall3Good());
        SmartDashboard.putBoolean("ball4good", isBall4Good());
        SmartDashboard.putBoolean("ball5good", isBall5Good());

        switch(state) {
            case kWaiting:
                if(pressureSensor.getVoltage() >= 1) {
                    setState(State.kReading);
                    timer.reset();
                    timer.start();
                    ballCounter++;
                }
                break;
            case kReading:
                if(!timer.hasElapsed(0.1)) {
                    data.addData(pressureSensor.getVoltage());
                } else {
                    quality[ballCounter - 1] = data.getMaxValue() >= 3;
                    data.clearData();
                    if(ballCounter != 5) {
                        setState(State.kWaiting);
                    } else {
                        setState(State.kOff);
                    }
                }
            case kOff:
                break;
        }
    }

    private void setState(State newState) {
        this.state = newState;
    }

    public boolean isBall1Good() {
        return quality[0];
    }

    public boolean isBall2Good() {
        return quality[1];
    }

    public boolean isBall3Good() {
        return quality[2];
    }

    public boolean isBall4Good() {
        return quality[3];
    }

    public boolean isBall5Good() {
        return quality[4];
    }

    public boolean[] getAllBallQuality() {
        return quality;
    }

    public void resetBallCounter() {
        ballCounter = 0;
        quality[0] = true;
        quality[1] = true;
        quality[2] = true;
        quality[3] = true;
        quality[4] = true;
    }
}
