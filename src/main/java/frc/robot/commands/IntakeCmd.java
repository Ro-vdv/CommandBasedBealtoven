package frc.robot.commands;

import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Kicker;
import frc.robot.subsystems.Rumble;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;

public class IntakeCmd extends Command {

  private final Intake intakeSubsystem;
  private final Kicker kickerSubsystem;

  Rumble rumble = new Rumble();
  
  public static DigitalInput linebreak;

  public boolean loaded;

  public IntakeCmd(Intake intakeSubsystem, Kicker kickerSubsystem) {
    this.intakeSubsystem = intakeSubsystem;
    this.kickerSubsystem = kickerSubsystem;

    linebreak = new DigitalInput(8);

    rumble.rumbleOff();

    addRequirements(intakeSubsystem, kickerSubsystem);
  }

  //while holding intake = on
  @Override
  public void initialize() {
    activeIntake(true);
    rumble.rumbleOff();
  }

  //checks if line is broken, stops intake, rumbles
  @Override
  public void execute() {
    if (isLineBroken()){
      try {
        Thread.sleep(50);
        activeIntake(false);
        if (!loaded){
          rumble.staticRumble();
      }
    } catch (InterruptedException ie) {
        Thread.currentThread().interrupt();
    } 
    loaded = true;
  } else {
    loaded = false;
  }
  }

  //if button let go stops motors
  @Override
  public void end(boolean interrupted) {
      activeIntake(false);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  //returns boolean if line is broken
  public static boolean isLineBroken() {
    return linebreak.get();
}

//simple altering motor on/off system
public void activeIntake(boolean intaking){
  if (!isLineBroken() && intaking){
      intakeSubsystem.startMotors(true);
      kickerSubsystem.startKicker(true);
    } else {
      intakeSubsystem.startMotors(false);
      kickerSubsystem.startKicker(false);
    }
}
}
