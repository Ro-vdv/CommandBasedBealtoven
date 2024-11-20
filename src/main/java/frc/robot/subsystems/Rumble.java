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

    // sets possible rumble states
    public enum RumbleState {
        OFF,
        STATIC,
        DOUBLE
    }
    
    //sets rumbles predetermineds
    public Rumble() {
        driver = new XboxController(0); 

        rumbleTime = new Timer();
        rumbleTime.reset();
    }

    //when called turns on rumble and starts timer
    public void staticRumble() {
        state = RumbleState.STATIC;
        rumbleTime.reset();
        rumbleOn();
        rumbleTime.start();
        
    }

    // currently not in use (WIP)
    public void doubleRumble() { 
        state = RumbleState.DOUBLE;
        rumbleTime.reset();
        rumbleOn();
        rumbleTime.start();
        
    }

    // takes the state and waits until timer is above a certain point before turning off rumbling
    @Override
    public void periodic() {
        time = rumbleTime.get();

        // static rumble section
        if (state == RumbleState.STATIC && time >= 0.5){
            rumbleOff();
            state = RumbleState.OFF;
            rumbleTime.stop();
        }

        // double rumble section
        // if (state == RumbleState.DOUBLE && time >= 0.2){
        //     rumbleOff();
        //     rumbleTime.stop();
            
        //     if (cycle == 0) {
        //         cycle = 1;
        //         rumbleTime.start();
        //     } else if (cycle == 1){
        //         state = RumbleState.OFF;
        //         cycle = 0;
        //         rumbleOff();
        //         rumbleTime.stop();
        //     }

        // } else if (cycle == 1 && state == RumbleState.DOUBLE && time >= 0.1) {
        //     doubleRumble();
        // }


    }
    

    //turns motor to %0
    public void rumbleOff(){
        driver.setRumble(GenericHID.RumbleType.kBothRumble, 0);
    }

    //turns motor to 100%
    public void rumbleOn() {
        driver.setRumble(GenericHID.RumbleType.kBothRumble, 1.0);
    }   
}

