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

    public boolean hasTarget() {
        return limelight.getEntry("tv").getDouble(0) == 1;
    }

    public double getTargetAngleX() {
        return limelight.getEntry("tx").getDouble(0);
    }

    public double getTargetAngleY() {
        return limelight.getEntry("ty").getDouble(0);
    }

    public double getLatency() {
        return limelight.getEntry("tl").getDouble(0) + 11;
    }

    public int getPipeline() {
        return limelight.getEntry("getpipe").getNumber(0).intValue();
    }

    public double[] get3DSolution() {
        return limelight.getEntry("camtran").getDoubleArray(new double[]{0, 0, 0, 0, 0, 0});
    }

    public double getTranslationX() {
        return get3DSolution()[0];
    }

    public double getTranslationY() {
        return get3DSolution()[1];
    }

    public double getTranslationZ() {
        return get3DSolution()[2];
    }

    public double getRotationPitch() {
        return get3DSolution()[3];
    }

    public double getRotationYaw() {
        return get3DSolution()[4];
    }

    public double getRotationRoll() {
        return get3DSolution()[5];
    }

    public void setLEDMode(LEDMode mode) {
        limelight.getEntry("ledMode").setNumber(mode.getMode());
    }

    public void setCameraMode(CameraMode mode) {
        limelight.getEntry("camMode").setNumber(mode.getMode());
    }

    public void setStreamingMode(StreamingMode mode) {
        limelight.getEntry("stream").setNumber(mode.getMode());
    }

    public void setPipeline(int id) {
        limelight.getEntry("pipeline").setNumber(id);
    }

    public double getRobotToOuterTargetDistance() {
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
