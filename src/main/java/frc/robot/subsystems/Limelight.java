package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Limelight extends SubsystemBase {
    NetworkTable limelight;

    public enum LEDMode {
        PIPELINE(0),
        LED_OFF(1),
        LED_BLINK(2),
        LED_ON(3);

        private int m;

        LEDMode(int mode) {
            m = mode;
        }

        public int getMode() {
            return m;
        }
    }

    public enum StreamingMode {
        STANDARD(0),
        PIP_MAIN(1),
        PIP_SECONDARY(2);

        private int m;

        StreamingMode(int mode) {
            m = mode;
        }

        public int getMode() {
            return m;
        }
    }

    public enum CameraMode {
        VISION_PROCESSING(0),
        DRIVER_CAMERA(1);

        private int m;

        CameraMode(int mode) {
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
        setLEDMode(LEDMode.LED_OFF);
        setStreamingMode(StreamingMode.STANDARD);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Distance to Target", getRobotToTargetDistance());
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
        return (limelight.getEntry("tl").getDouble(0) + 11) / 1000.0;
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

    public double get3DDistance(){
        return Math.sqrt(getTranslationZ()*getTranslationZ() + getTranslationX()*getTranslationX());
    }

    public double getArea(){
        return limelight.getEntry("ta").getDouble(0);
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
        return (Constants.LL_TARGET_HEIGHT - Constants.LL_MOUNT_HEIGHT) / Math.tan(Math.toRadians(Constants.LL_MOUNT_ANGLE + getTargetAngleY()));
    }

    public double getShooterLaunchVelocity() {
        double g = 9.81;
        double y = Constants.LL_TARGET_HEIGHT;
        double x = getRobotToTargetDistance();
        double launchAngle = Constants.SHOOTER_LAUNCH_ANGLE; // Set to proper value
        double tanA = Math.tan(Math.toRadians(launchAngle));
        double upper = Math.sqrt(g) * Math.sqrt(x) * Math.sqrt(Math.pow(tanA, 2) + 1);
        double lower = Math.sqrt(2 * tanA - ((2 * y) / x));
        return Units.metersToFeet(upper / lower);
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
