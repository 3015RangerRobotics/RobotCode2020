package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Harvester extends SubsystemBase {
    private VictorSP harvesterMotor;
    private DoubleSolenoid tiltControl;

    public Harvester() {
        tiltControl = new DoubleSolenoid(Constants.HARVESTER_SOLENOID_FORWARD, Constants.HARVESTER_SOLENOID_REVERSE);
        harvesterMotor = new VictorSP(Constants.HARVESTER_MOTOR);
        harvesterMotor.setInverted(true);
    }

    @Override
    public void periodic() {

    }

    /**
     * Pull the harvester up
     */
    public void harvesterUp() {
        tiltControl.set(DoubleSolenoid.Value.kReverse);
    }

    /**
     * Push the harvester down
     */
    public void harvesterDown() {
        tiltControl.set(DoubleSolenoid.Value.kForward);
    }

    /**
     * Set the harvester motor to intake balls
     */
    public void harvesterSet(double speed) {
        harvesterMotor.set(speed);
    }

    /**
     * Set the harvester motor to stop
     */
    public void harvesterStop() {
        harvesterMotor.set(0.0);
    }
}
