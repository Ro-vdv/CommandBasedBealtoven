package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm;


public class ArmCmd extends Command {

    private final Arm armSubsystem;

    private double desiredDestination; 

    public ArmCmd (Arm armSubsystem, boolean isMoving, double desiredDestination){
        this.armSubsystem = armSubsystem;
        this.desiredDestination = desiredDestination;
        addRequirements(armSubsystem);
    }


@Override
public void initialize() {
    System.out.println("ArmCmd initialized");
    armSubsystem.setDestination(desiredDestination);
}

@Override
  public void execute() {
    System.out.println("Moving to " + desiredDestination + " in my mind!");
  }

  @Override
  public void end(boolean interrupted) {
    System.out.println("Moved to " + desiredDestination + " in my mind!");
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}