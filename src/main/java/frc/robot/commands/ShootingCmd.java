package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Kicker;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Shooter.ShooterSetpoint;
import frc.robot.subsystems.Shooter.ShooterState;

public class ShootingCmd extends Command {

private final Shooter shooterSubsystem;
private final Kicker kickerSubsystem;

    public ShootingCmd(Shooter shooterSubsystem, Kicker kickerSubsystem){
        this.shooterSubsystem = shooterSubsystem;
        this.kickerSubsystem = kickerSubsystem;

        addRequirements(shooterSubsystem,kickerSubsystem);
    }

  @Override
  public void initialize() {
    if(IntakeCmd.isLineBroken()){
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

    if(shooterSubsystem.state == ShooterState.WARMING && shooterSubsystem.isAtTargetVelocity()){
      shooterSubsystem.state = ShooterState.WARMED;
      cancel();
    }

    if(!IntakeCmd.isLineBroken()){
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

