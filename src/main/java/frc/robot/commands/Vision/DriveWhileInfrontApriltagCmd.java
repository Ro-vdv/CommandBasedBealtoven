package frc.robot.commands.Vision;

import frc.robot.Constants;
import frc.robot.LimelightHelpers;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Kicker;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Shooter.ShooterState;
import frc.robot.subsystems.Swerve; 
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;

public class DriveWhileInfrontApriltagCmd extends Command {
    private final Limelight limelight;
    private final Swerve swerveDrive;

    private final double targetID = 16; 

    private final Shooter shooterSubsystem;
    private final Kicker kickerSubsystem;
    private final Arm armSubsystem;

    private boolean shot = false;
    private boolean newWarm = false;

    double pidOutput = 0;

    private PIDController pidController;

    public DriveWhileInfrontApriltagCmd(Limelight limelight, Swerve swerveDrive, Shooter shooterSubsystem, Kicker kickerSubsystem, Intake intakeSubsystem, Arm armSubsystem) {
        this.limelight = limelight;
        this.swerveDrive = swerveDrive;
        this.shooterSubsystem = shooterSubsystem;
        this.kickerSubsystem = kickerSubsystem;
        this.armSubsystem = armSubsystem;

        this.pidController = new PIDController(0.017, 0, 0.0007);

        addRequirements(limelight, kickerSubsystem, shooterSubsystem, intakeSubsystem);
    }

    @Override
    public void initialize() {
        pidController.reset();
    }

    @Override
    public void execute() {
        LimelightHelpers.SetRobotOrientation("limelight",0,0,0,0,0, 0);

        if (limelight.getAprilTagID() == targetID) {
            double x = limelight.getX(); // Get X offset
            
            // checks to see if camera is within a certain tolerance range
            if (Math.abs(x) > 1) { // Adjust tolerance as needed
                pidOutput = pidController.calculate(x, 0);
                pidOutput = pidOutput * 1;
            } else {
                pidOutput = 0;
            }

            // commented out shooting the note code for later use

            // if(Intake.isLineBroken()){
            //     if(shooterSubsystem.state == ShooterState.IDLE){
            //         shooterSubsystem.setWarming();
            //         shooterSubsystem.state = ShooterState.WARMING;
            //         armSubsystem.setDestination(Constants.ArmConstants.speakerPosition);
            //     } 
            //     if(shooterSubsystem.state == ShooterState.WARMING && shooterSubsystem.isAtTargetVelocity() && !newWarm){
            //         shooterSubsystem.state = ShooterState.WARMED;
            //         newWarm = true;
            //     }
            //     if (shooterSubsystem.state == ShooterState.WARMED && Math.abs(x) < 1){
            //         kickerSubsystem.startKicker(true);
            //         shooterSubsystem.state = ShooterState.SHOOTING;
            //         shot = true;
            //     }
            // }


            // moves robot to be within range
            swerveDrive.visionStrafeVal(pidOutput, true);
            swerveDrive.visionRotationVal(0, true);

        } else {           
            swerveDrive.visionStrafeVal(0, false);
            swerveDrive.visionRotationVal(0, false);
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
        swerveDrive.visionStrafeVal(0, false);
        swerveDrive.visionRotationVal(0, false); 
    }
}

