package frc.robot;

import frc.robot.commands.IntakeCmd;
import frc.robot.commands.ShootingCmd;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Kicker;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {

  private final Intake intakeSubsystem = new Intake();
  private final Kicker kickerSubsystem = new Kicker();
  private final Shooter shooterSubsystem = new Shooter();

  private final CommandXboxController driver = new CommandXboxController(0);

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {

    driver.rightBumper().whileTrue(new IntakeCmd(intakeSubsystem, kickerSubsystem, true, true));
    driver.rightTrigger().onTrue(new ShootingCmd(shooterSubsystem, kickerSubsystem));
  }
}