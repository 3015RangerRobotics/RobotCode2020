package lib.motion_profiles;

import com.ctre.phoenix.motion.BufferedTrajectoryPointStream;
import com.ctre.phoenix.motion.TrajectoryPoint;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.util.Units;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class MotionProfile {
    protected double pulsesPerUnit;
    protected double timeStep;
    protected double[][] profile;

    public MotionProfile(String profileName, double pulsesPerUnit, double timeStep){
        this.pulsesPerUnit = pulsesPerUnit;
        this.timeStep = timeStep;
        this.profile = loadProfile(profileName);
    }

    public MotionProfile(double distance, double maxV, double maxA, double pulsesPerUnit, double timeStep){
        this.pulsesPerUnit = pulsesPerUnit;
        this.timeStep = timeStep;
        this.profile = generate1D(distance, maxV, maxA);
    }

    public double[][] getProfile() {
        return profile;
    }

    public int getLength(){
        return profile.length;
    }

    public double getPositionFeet(int i){
        if(profile[i].length > 0){
            return profile[i][0];
        }
        return 0;
    }

    public double getVelocityFeet(int i){
        if(profile[i].length > 1){
            return profile[i][1];
        }
        return 0;
    }

    public double getAccelerationFeet(int i){
        if(profile[i].length > 2){
            return profile[i][2];
        }
        return 0;
    }

    public double getPositionEncoder(int i){
        return getPositionFeet(i) * pulsesPerUnit;
    }

    public double getVelocityEncoder(int i){
        return getVelocityFeet(i) * pulsesPerUnit;
    }

    public double getAccelerationEncoder(int i){
        return getAccelerationFeet(i) * pulsesPerUnit;
    }

    /**
     * Create a BufferedTrajectoryPointStream for the motors to follow
     * @return The given profile as a BufferedTrajectoryPointStream
     */
    public BufferedTrajectoryPointStream getProfileAsCTREBuffer(double kV, double kA) {
        BufferedTrajectoryPointStream buffer = new BufferedTrajectoryPointStream();

        for (int i = 0; i < profile.length; i++) {
            TrajectoryPoint point = new TrajectoryPoint();
            double position = getPositionEncoder(i);
            double velocity = getVelocityEncoder(i) / 10;
            double acceleration = getAccelerationEncoder(i);

            point.timeDur = (int) (timeStep * 1000);
            point.position = position;
            point.velocity = velocity;

            point.auxiliaryPos = 0;
            point.auxiliaryVel = 0;
            point.profileSlotSelect0 = 0;
            point.profileSlotSelect1 = 1;
            point.zeroPos = (i == 0);
            point.isLastPoint = ((i + 1) == profile.length);
            point.arbFeedFwd = (velocity * kV) + (acceleration * kA);

            buffer.Write(point);

        }
        return buffer;
    }

    /**
     * Load a motion profile from a csv file
     * @param profileName The name of the profile to load
     * @return The profile as a BufferedTrajectoryPointStream
     */
    private double[][] loadProfile(String profileName) {
        double[][] profile = new double[][]{};
        try (BufferedReader br = new BufferedReader(
                new FileReader(new File(Filesystem.getDeployDirectory(), "paths/" + profileName + ".csv")))) {
            ArrayList<double[]> points = new ArrayList<>();

            String line = "";
            while ((line = br.readLine()) != null) {
                String[] pointString = line.split(",");
                double[] point = new double[pointString.length];
                for (int i = 0; i < pointString.length; i++) {
                    point[i] = Double.parseDouble(pointString[i]);
                }
                points.add(point);
            }
            int dataPoints = points.get(0).length;
            profile = new double[points.size()][dataPoints];
            for (int i = 0; i < points.size(); i++) {
                for(int j = 0; j < dataPoints; j++){
                    profile[i][j] = points.get(i)[j];
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return profile;
    }

    /**
     * Generate a 1D motion profile
     * @param distance The total distance to travel
     * @param maxV The max velocity (feet/sec)
     * @param maxA The max acceleration (feet/sec2)
     * @return The generated profile
     */
    private double[][] generate1D(double distance, double maxV, double maxA){
        double d = Math.abs(distance);
        TrajectoryConfig config = new TrajectoryConfig(Units.feetToMeters(maxV), Units.feetToMeters(maxA));
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                new Pose2d(0, 0, new Rotation2d(0)),
                List.of(new Translation2d(Units.feetToMeters(d / 2), 0)),
                new Pose2d(Units.feetToMeters(d), 0, new Rotation2d(0)),
                config
        );

        double duration = trajectory.getTotalTimeSeconds();
        int numPoints = (int) Math.ceil(duration / timeStep);
        double[][] profile = new double[numPoints][3];
        int n = (distance < 0) ? -1 : 1;

        for(int i = 0; i < numPoints; i++){
            Trajectory.State state = trajectory.sample(i * timeStep);
            profile[i] = new double[]{Units.metersToFeet(state.poseMeters.getTranslation().getX()) * n,
                    Units.metersToFeet(state.velocityMetersPerSecond) * n,
                    Units.metersToFeet(state.accelerationMetersPerSecondSq) * n};
        }

        return profile;
    }
}
