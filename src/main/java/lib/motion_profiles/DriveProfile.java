package lib.motion_profiles;

import com.ctre.phoenix.motion.BufferedTrajectoryPointStream;
import com.ctre.phoenix.motion.TrajectoryPoint;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.Constants;

public class DriveProfile extends MotionProfile{

    public DriveProfile(String pathName) {
        super(pathName, Constants.DRIVE_PULSES_PER_FOOT, Constants.MP_TIME_STEP);
        if(this.profile[0].length != 5){
            DriverStation.reportError("Not enough data points in motion profile. Must include position, velocity, heading, and angular velocity!", false);
        }
    }

    public DriveProfile(double distance, double maxV, double maxA){
        super(distance, maxV, maxA, Constants.DRIVE_PULSES_PER_FOOT, Constants.MP_TIME_STEP);
    }

    public double getHeadingDegrees(int i){
        if(this.profile[i].length > 3){
            return this.profile[i][3];
        }
        return 0;
    }

    public double getAngularVelocityDegrees(int i){
        if(this.profile[i].length > 4){
            return this.profile[i][4];
        }
        return 0;
    }

    public double getHeadingIMU(int i){
        return getHeadingDegrees(i) * Constants.DRIVE_PIGEON_UNITS_PER_DEGREE;
    }

    public double getAngularVelocityIMU(int i){
        return getAngularVelocityDegrees(i) * Constants.DRIVE_PIGEON_UNITS_PER_DEGREE;
    }

    @Override
    public BufferedTrajectoryPointStream getProfileAsCTREBuffer(double kV, double kA) {
        BufferedTrajectoryPointStream buffer = new BufferedTrajectoryPointStream();

        for (int i = 0; i < profile.length; i++) {
            TrajectoryPoint point = new TrajectoryPoint();
            double position = getPositionEncoder(i);
            double velocity = getVelocityEncoder(i) / 10;
            double acceleration = getAccelerationEncoder(i);
            double heading = getHeadingIMU(i);
            double angularVelocity = getAngularVelocityIMU(i) / 10;

            point.timeDur = (int) (timeStep * 1000);
            point.position = position;
            point.velocity = velocity;

            point.auxiliaryPos = heading;
            point.auxiliaryVel = angularVelocity;
            point.profileSlotSelect0 = 0;
            point.profileSlotSelect1 = 1;
            point.zeroPos = false;
            point.isLastPoint = ((i + 1) == profile.length);
            point.arbFeedFwd = (velocity * kV) + (acceleration * kA);
            point.useAuxPID = true;

            buffer.Write(point);

        }
        return buffer;
    }
}
