package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm;

public class ArmCmd extends Command {
    
    private final Arm armSubsystem;

    private double desiredDestination;

    public ArmCmd(Arm armSubsystem, double desiredDestination) {
        this.armSubsystem = armSubsystem;
        this.desiredDestination = desiredDestination;
        addRequirements(armSubsystem);
    }

    // sends the desired destination (degree value) to arm subsystem to be used
    @Override
    public void initialize() {
        armSubsystem.setDestination(desiredDestination);
    }

    @Override
    public void execute() {
        
    }

    @Override
    public void end(boolean interrupted) {
         System.out.println("Arm Done");
    }

     @Override
    public boolean isFinished() {
        return true;
    }
}
