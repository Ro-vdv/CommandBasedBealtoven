package frc.robot;

import frc.robot.subsystems.Swerve;
import frc.robot.commands.ArmCmd;
import frc.robot.commands.IntakeCmd;
import frc.robot.commands.RumbleCmd;
import frc.robot.commands.ShootingCmd;
import frc.robot.commands.TeleopSwerve;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Kicker;
import frc.robot.subsystems.Rumble;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Arm;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {

  private final Intake intakeSubsystem = new Intake();
  private final Kicker kickerSubsystem = new Kicker();
  private final Shooter shooterSubsystem = new Shooter();
  private final Arm armSubsystem = new Arm();
  private final Rumble rumbleSubsytem = new Rumble();

  private final CommandXboxController driver = new CommandXboxController(0);

  private final Trigger ampPos = driver.x();
  private final Trigger zeroPos = driver.leftTrigger();
  private final Trigger speakerPos = driver.leftBumper();
  private final Trigger passingPos = driver.b();

  private final Trigger zeroGyro = driver.y();

  private final Swerve s_Swerve = Swerve.getInstance();

  private final int translationAxis = XboxController.Axis.kLeftY.value;
  private final int strafeAxis = XboxController.Axis.kLeftX.value;
  private final int rotationAxis = XboxController.Axis.kRightX.value;

  public RobotContainer() {

    s_Swerve.setDefaultCommand(
                new TeleopSwerve(
                        s_Swerve,
                        () -> -driver.getRawAxis(translationAxis),
                        () -> -driver.getRawAxis(strafeAxis),
                        () -> -driver.getRawAxis(rotationAxis),
                        () -> false // true = robotcentric

                ));

    configureBindings();

    
  }

  private void configureBindings() {

    driver.rightBumper().whileTrue(new IntakeCmd(intakeSubsystem, kickerSubsystem, armSubsystem));
    driver.rightTrigger().onTrue(new ShootingCmd(shooterSubsystem, kickerSubsystem));

    driver.a().onTrue(new RumbleCmd(rumbleSubsytem));

    zeroPos.onTrue(new ArmCmd(armSubsystem, Constants.ArmConstants.zeroPosition));
    speakerPos.onTrue(new ArmCmd(armSubsystem, Constants.ArmConstants.speakerPosition));
    ampPos.onTrue(new ArmCmd(armSubsystem, Constants.ArmConstants.ampPosition));
    passingPos.onTrue(new ArmCmd(armSubsystem, Constants.ArmConstants.passingPosition));
   
    zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroHeading()));
  }

  private void setStartingPose(Pose2d pose) {
        // this.poseEstimatorSubsystem.setCurrentPose(pose);
        s_Swerve.setPose(pose);
    }
}