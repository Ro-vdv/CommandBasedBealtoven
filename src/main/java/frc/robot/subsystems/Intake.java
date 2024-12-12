package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

  private TalonSRX topRoller;
  public static DigitalInput linebreak;

  public Intake() {
    topRoller = new TalonSRX(10);
    topRoller.setInverted(true);

    linebreak = new DigitalInput(8);
  }

    //returns boolean if line is broken
    public static boolean isLineBroken() {
        return linebreak.get();
    }

  // on/off motor depending on input
  public void startMotors(boolean intaking) {
    if (intaking){
      topRoller.set(TalonSRXControlMode.PercentOutput,0.6);
    } else {
      topRoller.set(TalonSRXControlMode.PercentOutput, 0);
    }
  }
}
