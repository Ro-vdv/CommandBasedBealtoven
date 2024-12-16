package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Kicker;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Shooter.ShooterState;
import frc.robot.subsystems.Swerve; 
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveInfrontApriltagCmd extends CommandBase {
    private final Limelight limelight;
    private final Swerve swerveDrive;

    private final double targetID = 16; 

    private final Shooter shooterSubsystem;
    private final Kicker kickerSubsystem;
    private final Intake intakeSubsystem;
    private final Arm armSubsystem;

    private boolean shot = false;
    private boolean newWarm = false;

    private PIDController pidController;

    public DriveInfrontApriltagCmd(Limelight limelight, Swerve swerveDrive, Shooter shooterSubsystem, Kicker kickerSubsystem, Intake intakeSubsystem, Arm armSubsystem) {
        this.limelight = limelight;
        this.swerveDrive = swerveDrive;
        this.intakeSubsystem = intakeSubsystem;
        this.shooterSubsystem = shooterSubsystem;
        this.kickerSubsystem = kickerSubsystem;
        this.armSubsystem = armSubsystem;

        this.pidController = new PIDController(0.017, 0, 0.0007);

        addRequirements(limelight, swerveDrive, kickerSubsystem, shooterSubsystem, intakeSubsystem);
    }

    @Override
    public void initialize() {
        pidController.reset();
    }

    @Override
    public void execute() {
        if (limelight.getAprilTagID() == targetID) {
            double x = limelight.getX(); // Get X offset

            double pidOutput = pidController.calculate(x, 0);
            
            if (Math.abs(x) > 1) { // Adjust tolerance as needed
                swerveDrive.drive(0, (-pidOutput * 1), 0, false, true); // Increase multiplier if needed
            }

            if(Intake.isLineBroken()){
                if(shooterSubsystem.state == ShooterState.IDLE){
                    shooterSubsystem.setWarming();
                    shooterSubsystem.state = ShooterState.WARMING;
                    armSubsystem.setDestination(Constants.ArmConstants.speakerPosition);
                } 
                if(shooterSubsystem.state == ShooterState.WARMING && shooterSubsystem.isAtTargetVelocity() && !newWarm){
                    shooterSubsystem.state = ShooterState.WARMED;
                    newWarm = true;
                }
                if (shooterSubsystem.state == ShooterState.WARMED && Math.abs(x) > 1){
                    kickerSubsystem.startKicker(true);
                    shooterSubsystem.state = ShooterState.SHOOTING;
                    shot = true;
                }
            }

            if(!Intake.isLineBroken()){
                shooterSubsystem.state = ShooterState.IDLE;
                if (Math.abs(x) > 1) {
                    cancel();
                    System.out.println("done");
                }
            }

            if (shot && !Intake.isLineBroken()) {
                cancel();
                System.out.println("done");
            }

        } else {
            swerveDrive.drive(0, 0, 0, false, true); // No target
        }
    }
    

    @Override
    public boolean isFinished() {
        return false; // Stop when aligned
    }

    @Override
    public void end(boolean interrupted) {
        shooterSubsystem.state = ShooterState.IDLE;
        shooterSubsystem.setIdle();
        kickerSubsystem.startKicker(false);
        newWarm = false;
        shot = false;
        armSubsystem.setDestination(Constants.ArmConstants.zeroPosition);
        swerveDrive.drive(0, 0, 0, false, false); // Stop all motion
    }
}

