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


    public ShootingCmd(Shooter shooterSubsystem, Kicker kickerSubsystem, boolean startShooter){
        this.shooterSubsystem = shooterSubsystem;
        this.kickerSubsystem = kickerSubsystem;
        this.startShooter = startShooter;

        addRequirements(shooterSubsystem, kickerSubsystem);
    }
  //boolean warming;

  @Override
  public void initialize() {

    if(shooterSubsystem.switched && IntakeCmd.isLineBroken()){
      shooterSubsystem.setWarming();
      shooterSubsystem.switched = false;
    } else if (IntakeCmd.isLineBroken()){
      shooterSubsystem.setShoot();
      shooterSubsystem.switched = true;
    } else {
      cancel();
    }
    
  }

  @Override
  public void execute() {
    if(!IntakeCmd.isLineBroken()){
      shooterSubsystem.setIdle();
    }
  }

  @Override
  public void end(boolean interrupted) {
    System.out.println("finished");
  }

  @Override
  public boolean isFinished() {
    return false;
  }

}

