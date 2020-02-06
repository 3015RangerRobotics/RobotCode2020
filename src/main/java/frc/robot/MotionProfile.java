package frc.robot;

import com.ctre.phoenix.motion.BufferedTrajectoryPointStream;
import com.ctre.phoenix.motion.TrajectoryPoint;
import edu.wpi.first.wpilibj.Filesystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class MotionProfile {
    private double[][] profile;

    public MotionProfile(String pathName){
        this.profile = loadProfile(pathName);
    }

    public MotionProfile(double distance, double maxV, double accel){
        this.profile = generate1D(distance, maxV, accel);
    }

    public double[][] getProfile() {
        return profile;
    }

    /**
     * Create a BufferedTrajectoryPointStream for the motors to follow
     * @return The given profile as a BufferedTrajectoryPointStream
     */
    public BufferedTrajectoryPointStream asBuffer() {
        BufferedTrajectoryPointStream buffer = new BufferedTrajectoryPointStream();

        for (int i = 0; i < profile.length; i++) {
            TrajectoryPoint point = new TrajectoryPoint();
            double position = profile[i][0];
            double velocity = profile[i][1];

            point.timeDur = Constants.MP_TIME_STEP;
            point.position = position;
            point.velocity = velocity;

            point.auxiliaryPos = 0;
            point.auxiliaryVel = 0;
            point.profileSlotSelect0 = 0;
            point.profileSlotSelect1 = 1;
            point.zeroPos = (i == 0);
            point.isLastPoint = ((i + 1) == profile.length);
            point.arbFeedFwd = 0;

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
            ArrayList<double[]> points = new ArrayList<double[]>();

            String line = "";
            while ((line = br.readLine()) != null) {
                String[] pointString = line.split(",");
                double[] point = new double[2];
                for (int i = 0; i < 2; i++) {
                    point[i] = Double.parseDouble(pointString[i]);
                }
                points.add(point);
            }
            profile = new double[points.size()][2];
            for (int i = 0; i < points.size(); i++) {
                profile[i][0] = points.get(i)[0] * Constants.DRIVE_PULSES_PER_FOOT;
                profile[i][1] = points.get(i)[1] * Constants.DRIVE_PULSES_PER_FOOT / 10;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return profile;
    }

    private double[][] generate1D(double distance, double maxV, double a) {
        double d = Math.abs(distance);
        boolean reverse = distance < 0;
        double timeStep = Constants.MP_TIME_STEP / 1000.0;
        ArrayList<double[]> points = new ArrayList<>();
        points.add(new double[] {0, 0, 0});
        int state = 0;//0 = accel, 1 = maxV, 2 = decel
        double lastPos = 0;
        double lastVel = 0;
        boolean done = false;
        double vT;
        double pT;
        while(!done) {
            switch(state) {
                case 0:
                    vT = lastVel + (a * timeStep);
                    pT = lastPos + (lastVel * timeStep) + (0.5 * a * (timeStep*timeStep));

                    if(vT >= maxV) {
                        vT = maxV;
                        state = 1;//maxV
                        points.add(new double[] {pT, vT, 0});
                        lastPos = pT;
                        lastVel = vT;
                        break;
                    }

                    if(pT <= d/2 + (lastVel * timeStep) && pT >= d/2 - (lastVel * timeStep)) {
                        state = 2;//decel
                        points.add(new double[] {pT, vT, 0});
                        lastPos = pT;
                        lastVel = vT;
                        break;
                    }

                    points.add(new double[] {pT, vT, a});
                    lastPos = pT;
                    lastVel = vT;
                    break;
                case 1:
                    vT = maxV;
                    pT = lastPos + (lastVel * timeStep);

                    double vToA = vT / a;
                    double pVoA = lastPos + (lastVel * vToA) + (0.5 * a * (vToA * vToA));
                    if(pVoA <= d - pT + (maxV * timeStep) && pVoA >= d - pT - (maxV * timeStep)) {
                        state = 2;//decel
                        points.add(new double[] {pT, vT, 0});
                        lastPos = pT;
                        lastVel = vT;
                        break;
                    }

                    points.add(new double[] {pT, vT, 0});
                    lastPos = pT;
                    lastVel = vT;
                    break;
                case 2:
                    vT = lastVel + (-a * timeStep);
                    pT = lastPos + (lastVel * timeStep) + (0.5 * -a * (timeStep*timeStep));

                    if(pT <= d + 0.01 && pT >= d - 0.01) {
                        points.add(new double[] {pT, vT, 0});
                        done = true;//stop
                        break;
                    }

                    if(lastVel < 0){
                        done = true;
                    }

                    points.add(new double[] {pT, vT, -a});
                    lastPos = pT;
                    lastVel = vT;
                    break;
            }
        }
        points.add(new double[] {d, 0, 0});
        double[][] ret = new double[points.size()][3];
        for(int i = 0; i < points.size(); i++) {
            if(!reverse){
                ret[i] = points.get(i);
            }else{
                ret[i] = new double[]{-points.get(i)[0], -points.get(i)[1], -points.get(i)[2]};
            }
        }

        return ret;
    }
}
