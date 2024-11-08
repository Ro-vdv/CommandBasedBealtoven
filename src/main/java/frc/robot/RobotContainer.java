package frc.robot;

import frc.robot.commands.ArmCmd;
import frc.robot.commands.IntakeCmd;
import frc.robot.commands.ShootingCmd;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Kicker;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {

  private final Intake intakeSubsystem = new Intake();
  private final Kicker kickerSubsystem = new Kicker();
  private final Shooter shooterSubsystem = new Shooter();
  private final Arm armSubsystem = new Arm();

  private final CommandXboxController driver = new CommandXboxController(0);
  private final CommandXboxController operator = new CommandXboxController(1);

  private final Trigger ampPos = operator.b();
  private final Trigger zeroPos = operator.a();
  private final Trigger speakerPos = operator.x();

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {

    driver.rightBumper().whileTrue(new IntakeCmd(intakeSubsystem, kickerSubsystem, true, true));
    driver.rightTrigger().whileTrue(new ShootingCmd(shooterSubsystem, kickerSubsystem, true));

    ampPos.onTrue(new ArmCmd(armSubsystem, true, Constants.ArmConstants.ampPosition));
    speakerPos.onTrue(new ArmCmd(armSubsystem, true, Constants.ArmConstants.speakerPosition));
    zeroPos.onTrue(new ArmCmd(armSubsystem, true, Constants.ArmConstants.zeroPosition));
  }
}