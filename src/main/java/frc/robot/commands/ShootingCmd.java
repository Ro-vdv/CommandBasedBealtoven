package frc.robot.commands;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Kicker;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Shooter.ShooterSetpoint;

public class ShootingCmd extends Command {

private final Shooter shooterSubsystem;
 private final Kicker kickerSubsystem;
private final boolean startShooter;

//private DigitalInput linebreak;

    public ShootingCmd(Shooter shooterSubsystem, Kicker kickerSubsystem,boolean startShooter){
        this.shooterSubsystem = shooterSubsystem;
        this.kickerSubsystem = kickerSubsystem;
        this.startShooter = startShooter;
        //linebreak = new DigitalInput(8);
        

        addRequirements(shooterSubsystem);
    }
  boolean warming;

  @Override
  public void initialize() {
    
    if (shooterSubsystem.sendSetpoint == ShooterSetpoint.zero && !warming){
      shooterSubsystem.startShooter(ShooterSetpoint.speakerSetpoint);
      warming = true;
    } else if (shooterSubsystem.sendSetpoint == ShooterSetpoint.speakerSetpoint){
      kickerSubsystem.startKicker(true);
    }
  }

  @Override
  public void execute() {
    // if (!linebreak.get()){
    //   shooterSubsystem.startShooter(false);
    // }
  }

  @Override
  public void end(boolean interrupted) {
    shooterSubsystem.startShooter(ShooterSetpoint.zero);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

}

