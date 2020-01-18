/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class TempShooter extends SubsystemBase {
  /**
   * Creates a new TempShooter.
   */
  private VictorSP shooter1;
  private VictorSP shooter2;

  public TempShooter() {
    shooter1 = new VictorSP(Constants.spinningShooter);
    shooter2 = new VictorSP(9);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void set(double speed){
      shooter1.set(speed);
      shooter2.set(-speed);
  }
}
