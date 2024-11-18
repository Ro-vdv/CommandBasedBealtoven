package frc.robot.subsystems;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.GenericHID;

public class Rumble extends SubsystemBase {
    
    private final XboxController driver;
    
    public Rumble() {
        driver = new XboxController(0); 
    }

    public void staticRumble(long timeMilli) {
        try {
            rumbleOn();
            wait(timeMilli);
            rumbleOff();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void doubleRumble() {
        try {
            rumbleOn();
            wait(225);
            rumbleOff();
            wait(50);
            rumbleOn();
            wait(225);
            rumbleOff();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    
    public void rumbleOff(){
        driver.setRumble(GenericHID.RumbleType.kRightRumble, 0);
        driver.setRumble(GenericHID.RumbleType.kLeftRumble, 0);
    }

    public void rumbleOn() {
        driver.setRumble(GenericHID.RumbleType.kRightRumble, 1.0);
        driver.setRumble(GenericHID.RumbleType.kLeftRumble, 1.0);
    }   
}
