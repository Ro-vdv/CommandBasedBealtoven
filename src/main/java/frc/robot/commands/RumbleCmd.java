package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Rumble;

public class RumbleCmd extends Command{
    
    private final Rumble rumbleSubsytem;

    public RumbleCmd(Rumble rumbleSubsytem){
        this.rumbleSubsytem = rumbleSubsytem;

        addRequirements(rumbleSubsytem);
    }

    @Override
    public void initialize(){
        rumbleSubsytem.staticRumble(5000);
    }

    @Override
    public void execute() {

    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Rumbling finished");
    }

    @Override
    public boolean isFinished() {
    return false;
    }
}
