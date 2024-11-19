package frc.robot.subsystems;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Timer;

public class Rumble extends SubsystemBase {
    
    private final XboxController driver;

    private Timer rumbleTime;

    boolean timeComplete = false;
    double time = 0;
    RumbleState state;
    long cycle = 0;

    public enum RumbleState {
        OFF,
        STATIC,
        DOUBLE
    }
    
    public Rumble() {
        driver = new XboxController(0); 

        rumbleTime = new Timer();
        rumbleTime.reset();
    }

    public void staticRumble() {
        state = RumbleState.STATIC;
        rumbleTime.reset();
        rumbleOn();
        rumbleTime.start();
        
    }

    public void doubleRumble() { 
        state = RumbleState.DOUBLE;
        rumbleTime.reset();
        rumbleOn();
        rumbleTime.start();
        
    }

    @Override
    public void periodic() {
        time = rumbleTime.get();

        if (state == RumbleState.STATIC && time >= 0.5){
            rumbleOff();
            state = RumbleState.OFF;
        }

        // if (state == RumbleState.DOUBLE && time >= 0.2){

        //     rumbleOff();
        //     rumbleTime.restart();
            
        //     if (cycle == 0) {
        //         cycle = 1;

        //     }

        //     state = RumbleState.OFF;
        // }

        // if (cycle == 1 && ) { 
        //     cycle = 0;

        // }
    }
    


    public void rumbleOff(){
        driver.setRumble(GenericHID.RumbleType.kBothRumble, 0);
    }

    public void rumbleOn() {
        driver.setRumble(GenericHID.RumbleType.kBothRumble, 1.0);
    }   
}

