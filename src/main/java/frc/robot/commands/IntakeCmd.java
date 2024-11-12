package frc.robot.commands;

import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Kicker;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.XboxController;

public class IntakeCmd extends Command {

  private final Intake intakeSubsystem;
  private final Kicker kickerSubsystem;

  private final XboxController driver = new XboxController(0);
  
  public static DigitalInput linebreak;

  public IntakeCmd(Intake intakeSubsystem, Kicker kickerSubsystem) {
    this.intakeSubsystem = intakeSubsystem;
    this.kickerSubsystem = kickerSubsystem;
    linebreak = new DigitalInput(8);

    addRequirements(intakeSubsystem, kickerSubsystem);
  }

  //while holding intake = on
  @Override
  public void initialize() {
    activeIntake(true);
  }

  //checks if line is broken, stops intake, rumbles
  @Override
  public void execute() {
    if (isLineBroken()){
      try {
        Thread.sleep(50);
        activeIntake(false);
        driver.setRumble(GenericHID.RumbleType.kRightRumble, 1.0);
    } catch (InterruptedException ie) {
        Thread.currentThread().interrupt();
    }
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
