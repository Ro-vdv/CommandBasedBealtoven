package frc.robot.subsystems;

// import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.Constants;

public class Arm extends SubsystemBase {

    // public ArmPosition position;
    
    // public Arm() {

    // }

    public enum ArmPosition{
        Zero,
        Amp,
        Speaker;
    }

    // public void Move(boolean isMoving){
    // }

    public void setDestination(double desiredDestination, ArmPosition position) {
    }

}