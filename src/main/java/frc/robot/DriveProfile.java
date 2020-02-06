package frc.robot;

public class DriveProfile {
    private MotionProfile leftProfile;
    private MotionProfile rightProfile;

    public DriveProfile(String pathName){
        this.leftProfile = new MotionProfile(pathName + "_left");
        this.rightProfile = new MotionProfile(pathName + "_right");
    }

    public DriveProfile(double distance, double maxV, double accel){
        this.leftProfile = new MotionProfile(distance, maxV, accel);
        this.rightProfile = this.leftProfile;
    }

    public MotionProfile getLeftProfile() {
        return leftProfile;
    }

    public MotionProfile getRightProfile() {
        return rightProfile;
    }
}
