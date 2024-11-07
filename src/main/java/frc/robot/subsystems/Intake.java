package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {

  private TalonSRX topRoller;

  public Intake() {
    topRoller = new TalonSRX(10);
    topRoller.setInverted(true);
  }

  public void startMotors(boolean intaking) {
    if (intaking){
      topRoller.set(TalonSRXControlMode.PercentOutput,0.6);
    } else {
      topRoller.set(TalonSRXControlMode.PercentOutput, 0);
    }
  }
}
