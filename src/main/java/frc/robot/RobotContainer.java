package frc.robot;

import frc.robot.commands.ArmCmd;
import frc.robot.commands.IntakeCmd;
import frc.robot.commands.ShootingCmd;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Kicker;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Arm;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {

  private final Intake intakeSubsystem = new Intake();
  private final Kicker kickerSubsystem = new Kicker();
  private final Shooter shooterSubsystem = new Shooter();
  private final Arm armSubsystem = new Arm();

  private final CommandXboxController driver = new CommandXboxController(0);

  private final Trigger ampPos = driver.x();
  private final Trigger zeroPos = driver.leftTrigger();
  private final Trigger speakerPos = driver.leftBumper();

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {

    driver.rightBumper().whileTrue(new IntakeCmd(intakeSubsystem, kickerSubsystem));
    driver.rightTrigger().onTrue(new ShootingCmd(shooterSubsystem, kickerSubsystem));

    zeroPos.onTrue(new ArmCmd(armSubsystem, Constants.ArmConstants.zeroPosition));
    speakerPos.onTrue(new ArmCmd(armSubsystem, Constants.ArmConstants.speakerPosition));
    ampPos.onTrue(new ArmCmd(armSubsystem, Constants.ArmConstants.ampPosition));
    
  }
}