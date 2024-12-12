package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Kicker;
import frc.robot.subsystems.Rumble;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Shooter.ShooterState;

public class ShootingCmd extends Command {

private final Shooter shooterSubsystem;
private final Kicker kickerSubsystem;

Rumble rumble = new Rumble();

    public ShootingCmd(Shooter shooterSubsystem, Kicker kickerSubsystem){
        this.shooterSubsystem = shooterSubsystem;
        this.kickerSubsystem = kickerSubsystem;

        addRequirements(shooterSubsystem);
    }

  // when pressed if note is loaded starts to warmup, if warmed up shoots
  @Override
  public void initialize() {
    if(Intake.isLineBroken()){
      if(shooterSubsystem.state == ShooterState.IDLE){
        shooterSubsystem.setWarming();
        shooterSubsystem.state = ShooterState.WARMING;
        printState();
      } else if (shooterSubsystem.state == ShooterState.WARMED){
        kickerSubsystem.startKicker(true);
        shooterSubsystem.state = ShooterState.SHOOTING;
        printState();
      }  
    }
  }

  @Override
  public void execute() {
    // once desired RPM is met sets state to warmed (shootable)
    if(shooterSubsystem.state == ShooterState.WARMING && shooterSubsystem.isAtTargetVelocity()){
      shooterSubsystem.state = ShooterState.WARMED;
      rumble.staticRumble();
      cancel();
    }

    //if note is ever not loaded sets state to idle and wont start warming
    if(!Intake.isLineBroken()){
      shooterSubsystem.state = ShooterState.IDLE;
      shooterSubsystem.setIdle();
      kickerSubsystem.startKicker(false);
      cancel();
    }
  }

  @Override
  public void end(boolean interrupted) {
      printState();
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  private void printState(){
    System.out.println(shooterSubsystem.state);
  }

}

