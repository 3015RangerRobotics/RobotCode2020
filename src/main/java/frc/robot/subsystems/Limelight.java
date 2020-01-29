/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Limelight extends SubsystemBase {
    NetworkTable limelight;

    private double targetHeight = 7.58;
    private double limelightHeight = 2.2;
    private double limelightAngle = 29.8;
    private double outerToInnerTargetDistance = 2.4;

    public static enum LEDMode {
        PIPELINE(0),
        LED_OFF(1),
        LED_BLINK(2),
        LED_ON(3);

        private int m;

        private LEDMode(int mode) {
            m = mode;
        }

        public int getMode() {
            return m;
        }
    }

    public static enum StreamingMode {
        STANDARD(0),
        PIP_MAIN(1),
        PIP_SECONDARY(2);

        private int m;

        private StreamingMode(int mode) {
            m = mode;
        }

        public int getMode() {
            return m;
        }
    }

    public static enum CameraMode {
        VISION_PROCESSING(0),
        DRIVER_CAMERA(1);

        private int m;

        private CameraMode(int mode) {
            m = mode;
        }

        public int getMode() {
            return m;
        }
    }

    /**
     * Creates a new Limelight.
     */
    public Limelight() {
        limelight = NetworkTableInstance.getDefault().getTable("limelight");
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    /**
     * @return if the limelight has a target
     */
    public boolean hasTarget() {
        return limelight.getEntry("tv").getDouble(0) == 1;
    }

    /**
     * @return The X angle to the target
     */
    public double getTargetAngleX() {
        return limelight.getEntry("tx").getDouble(0);
    }

    /**
     * @return The Y angle to the target
     */
    public double getTargetAngleY() {
        return limelight.getEntry("ty").getDouble(0);
    }

    /**
     * @return The current latency from the limelight to the robot
     */
    public double getLatency() {
        return limelight.getEntry("tl").getDouble(0) + 11;
    }

    /**
     * @return The current active pipeline
     */
    public int getPipeline() {
        return limelight.getEntry("getpipe").getNumber(0).intValue();
    }

    /**
     * @return The solution to solvePnP
     */
    public double[] get3DSolution() {
        return limelight.getEntry("camtran").getDoubleArray(new double[]{0, 0, 0, 0, 0, 0});
    }

    /**
     * @return The X distance to the target
     */
    public double getTranslationX() {
        return get3DSolution()[0];
    }

    /**
     * @return The Y distance to the target
     */
    public double getTranslationY() {
        return get3DSolution()[1];
    }

    /**
     * @return The Z distance to the target
     */
    public double getTranslationZ() {
        return get3DSolution()[2];
    }

    /**
     * @return Target pitch
     */
    public double getRotationPitch() {
        return get3DSolution()[3];
    }

    /**
     * @return Target yaw
     */
    public double getRotationYaw() {
        return get3DSolution()[4];
    }

    /**
     * @return Target roll
     */
    public double getRotationRoll() {
        return get3DSolution()[5];
    }

    /**
     * Set the LED mode
     * @param mode The mode to use
     */
    public void setLEDMode(LEDMode mode) {
        limelight.getEntry("ledMode").setNumber(mode.getMode());
    }

    /**
     * Set the camera mode
     * @param mode The mode to use
     */
    public void setCameraMode(CameraMode mode) {
        limelight.getEntry("camMode").setNumber(mode.getMode());
    }

    /**
     * Set the streaming mode
     * @param mode The mode to use
     */
    public void setStreamingMode(StreamingMode mode) {
        limelight.getEntry("stream").setNumber(mode.getMode());
    }

    /**
     * Set the processing pipeline
     * @param id The id of the pipeline to use
     */
    public void setPipeline(int id) {
        limelight.getEntry("pipeline").setNumber(id);
    }

    /**
     * @return The distance from the robot to the target (parallel line with floor)
     */
    public double getRobotToTargetDistance() {
        return (targetHeight - limelightHeight) / Math.tan(Math.toRadians(limelightAngle + getTargetAngleY()));
    }

    // public double getlimelightToOuterTargetDistance() {
    //     double d2 = Math.pow(getRobotToOuterTargetDistance(), 2);
    //     return Math.sqrt(d2 + ((targetHeight - limelightHeight)*(targetHeight - limelightHeight)));
    // }

    // public double getInnerTargetAngleX() {
    //     double a = outerToInnerTargetDistance;
    //     double b = getlimelightToOuterTargetDistance();
    //     double c = Math.sqrt((a * a) + (b * b)- (2*a*b * Math.cos(Math.toRadians(get3DSolution()[4] + 90)))); 
    //     double angle = Math.asin(a* Math.sin(get3DSolution()[4] + 90)/c);
    //     return getAngleToOuterGoalX() - angle;
    // }
}
