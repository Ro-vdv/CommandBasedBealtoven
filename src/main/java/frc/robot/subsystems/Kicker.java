package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Kicker extends SubsystemBase {

    private CANSparkMax kickerMotor;

    public Kicker() {
    kickerMotor = new CANSparkMax(3, MotorType.kBrushless);
        kickerMotor.setInverted(true);

    }

    public void startKicker(boolean intaking) {
    if (intaking){
      kickerMotor.set(0.25);
    } else {
      kickerMotor.set(0);
    }
  }
}
