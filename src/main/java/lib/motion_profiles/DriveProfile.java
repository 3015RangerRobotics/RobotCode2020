package lib.motion_profiles;

import frc.robot.Constants;

public class DriveProfile {
    private MotionProfile leftProfile;
    private MotionProfile rightProfile;

    public DriveProfile(String pathName){
        this.leftProfile = new MotionProfile(pathName + "_left", Constants.DRIVE_PULSES_PER_FOOT, Constants.MP_TIME_STEP);
        this.rightProfile = new MotionProfile(pathName + "_right", Constants.DRIVE_PULSES_PER_FOOT, Constants.MP_TIME_STEP);
    }

    public DriveProfile(double distance, double maxV, double maxA){
        this.leftProfile = new MotionProfile(distance, maxV, maxA, Constants.DRIVE_PULSES_PER_FOOT, Constants.MP_TIME_STEP);
        this.rightProfile = this.leftProfile;
    }

    public MotionProfile getLeftProfile() {
        return leftProfile;
    }

    public MotionProfile getRightProfile() {
        return rightProfile;
    }
}
