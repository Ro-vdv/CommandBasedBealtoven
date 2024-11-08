package frc.robot.commands;

import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Kicker;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;

public class IntakeCmd extends Command {

  private final Intake intakeSubsystem;
  private final Kicker kickerSubsystem;
  private final boolean startMotors;
  private final boolean kickerMotor;
  
  public static DigitalInput linebreak;

  public IntakeCmd(Intake intakeSubsystem, Kicker kickerSubsystem, boolean startMotors, boolean kickerMotor) {
    this.intakeSubsystem = intakeSubsystem;
    this.kickerSubsystem = kickerSubsystem;
    this.startMotors = startMotors;
    this.kickerMotor = kickerMotor;
    linebreak = new DigitalInput(8);

    addRequirements(intakeSubsystem, kickerSubsystem);
  }

  @Override
  public void initialize() {
    activeIntake(true);
  }

  @Override
  public void execute() {
    if (isLineBroken()){
      try {
        Thread.sleep(50);
        activeIntake(false);
    } catch (InterruptedException ie) {
        Thread.currentThread().interrupt();
    }
  }
  }

  @Override
  public void end(boolean interrupted) {
      activeIntake(false);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  public static boolean isLineBroken() {
    return linebreak.get();
}

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
