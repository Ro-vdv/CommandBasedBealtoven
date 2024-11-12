package frc.robot.subsystems;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID;

public class Rumble {
    
    private final XboxController driver = new XboxController(0);   

    public void staticRumble() {
        try {
            rumbleOn();
            Thread.sleep(500);
            rumbleOff();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void doubleRumble() {
        try{
            rumbleOn();
            Thread.sleep(250);
            rumbleOff();
            Thread.sleep(50);
            rumbleOn();
            Thread.sleep(250);
            rumbleOff();

        } 
        catch (InterruptedException e) {
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
