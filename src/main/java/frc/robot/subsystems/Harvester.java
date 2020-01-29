/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Harvester extends SubsystemBase {
            VictorSP harvester;
    private DoubleSolenoid tiltControl;

  /**
   * Creates a new Harvester.
   */
  public Harvester() {
     harvester = new VictorSP(5);
     tiltControl = new DoubleSolenoid(7,6);
  }

  @Override
  public void periodic() {

    // This method will be called once per scheduler run
  }
    public void harvesterUp()
    {
        //TODO: change kForward to kReverse if havesterIn() causes the piston arm to out
        tiltControl.set(DoubleSolenoid.Value.kForward);
    }
    public void harvesterDown()
    {

        tiltControl.set(DoubleSolenoid.Value.kReverse);
    }
  public void harvesterIn(){
      harvester.set(-.75);
  }
  public void harvesterStop(){
      harvester.set(0.0);
  }
  public void harvesterOut(){
      harvester.set(.5);
  }
}
