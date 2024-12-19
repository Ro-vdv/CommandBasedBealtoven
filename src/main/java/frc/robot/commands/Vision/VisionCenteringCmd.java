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
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class VisionCenteringCmd extends CommandBase {
    private final Limelight limelight;
    private final Swerve swerveDrive;

    private final double targetID = 16; 

    private final Shooter shooterSubsystem;
    private final Kicker kickerSubsystem;
    private final Intake intakeSubsystem;
    private final Arm armSubsystem;

    private boolean shot = false;
    private boolean newWarm = false;

    double strafePidOutput = 0;
    double rotationPidOutput = 0;
    double translationPidOutput = 0;

    private PIDController strafePidController;
    private PIDController rotationPidController;
    private PIDController translationPidController;

    public VisionCenteringCmd(Limelight limelight, Swerve swerveDrive, Shooter shooterSubsystem, Kicker kickerSubsystem, Intake intakeSubsystem, Arm armSubsystem) {
        this.limelight = limelight;
        this.swerveDrive = swerveDrive;
        this.intakeSubsystem = intakeSubsystem;
        this.shooterSubsystem = shooterSubsystem;
        this.kickerSubsystem = kickerSubsystem;
        this.armSubsystem = armSubsystem;

        this.strafePidController = new PIDController(0.017, 0, 0.0007);
        this.translationPidController = new PIDController(0.017, 0, 0.0007);
        this.rotationPidController = new PIDController(0.003, 0.01, 0.003);

        addRequirements(limelight, swerveDrive, kickerSubsystem, shooterSubsystem, intakeSubsystem);
    }

    @Override
    public void initialize() {
        strafePidController.reset();
        translationPidController.reset();
        rotationPidController.reset();
    }

    @Override
    public void execute() {

        if (limelight.getAprilTagID() == targetID) {
            double[] botPose = LimelightHelpers.getTargetPose_CameraSpace("");

            double x = limelight.getX(); // Get X offset
            double yaw = botPose[4];

            if (Math.abs(yaw) > 1) { // Adjust tolerance as needed
                rotationPidOutput = rotationPidController.calculate(yaw, 0);
                rotationPidOutput = rotationPidOutput * 1; //Speed multiplier
                strafePidOutput = 0;
            } else 


            // if (Math.abs(x) > 1) { // Adjust tolerance as needed
            //     strafePidOutput = strafePidController.calculate(x, 0);
            //     strafePidOutput = -strafePidOutput * 1; //Speed multiplier
            // } 
            // else 


            {
                strafePidOutput = 0;
                rotationPidOutput = 0;
                cancel();
            } 

            swerveDrive.drive(0, strafePidOutput, rotationPidOutput, false, true); // Increase multiplier if needed
        } else {
            cancel();
        }
    }
    

    @Override
    public boolean isFinished() {
        return false; // Stop when aligned
    }

    @Override
    public void end(boolean interrupted) {
        swerveDrive.drive(0, 0, 0, false, false); // Stop all motion
    }
}

