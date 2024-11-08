package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
// import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
// import frc.robot.Constants;
import frc.robot.Constants.ArmConstants;

public class Arm extends SubsystemBase {

    //variables
    public ArmPosition position;

    //inputs & motors
    // private DigitalInput armHallEffect;

    ShuffleboardTab tab = Shuffleboard.getTab("Arm");
    
    public Arm() {
        // armHallEffect = new DigitalInput(ArmConstants.armHallEffectID);
    }

    public enum ArmPosition {
        Zero(),
        Amp(),
        Speaker();

        public String Zero(){
            return "Zero";
        }

        public String Amp(){
            return "Amp";
        }

        public String Speaker(){
            return "Speaker";
        }
    }
    // public void Move(boolean isMoving){
    // }

    // public  setDesiredDestination(double desiredDestination, ArmPosition position) {
    //     if (desiredDestination == Constants.ArmConstants.zeroPosition){
    //         position = ArmPosition.Zero;
    //     } else if (desiredDestination == Constants.ArmConstants.ampPosition){
    //         position = ArmPosition.Amp;
    //     } else if (desiredDestination == Constants.ArmConstants.speakerPosition){
    //         position = ArmPosition.Speaker;
    //     }
    // }

}