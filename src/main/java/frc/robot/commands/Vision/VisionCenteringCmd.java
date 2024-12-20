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

    //double desiredz = 1.92; // in meters

    boolean xPos = false;
    boolean rotationPos = false;
   // boolean zPos = false;

    public VisionCenteringCmd(Limelight limelight, Swerve swerveDrive, Shooter shooterSubsystem, Kicker kickerSubsystem, Intake intakeSubsystem, Arm armSubsystem) {
        this.limelight = limelight;
        this.swerveDrive = swerveDrive;
        this.intakeSubsystem = intakeSubsystem;
        this.shooterSubsystem = shooterSubsystem;
        this.kickerSubsystem = kickerSubsystem;
        this.armSubsystem = armSubsystem;

        this.strafePidController = new PIDController(0.3, 0, 0.0007);
        this.translationPidController = new PIDController(0.1, 0, 0.0007);
        this.rotationPidController = new PIDController(0.0006, 0.001, 0.0001);

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
            double yawDeg = botPose[4];

            double zDis = botPose[2];

            double atDeg = yawDeg - x;
            double atRad = Math.toRadians(atDeg);
            double atXDis = zDis * (Math.tan(atRad));

            //double zDiff = desiredz - zDis;

            //System.out.println(zDiff);

            if (Math.abs(yawDeg) > 1) { // Adjust tolerance as needed
                rotationPidOutput = rotationPidController.calculate(yawDeg, 0);
                rotationPidOutput = rotationPidOutput * 1; //Speed multiplier
                rotationPos = false;
            } else {
                rotationPidOutput = 0;
                rotationPos = true;
            }

            if (Math.abs(atXDis) > 0.1) { // In meters
                strafePidOutput = strafePidController.calculate(atXDis, 0);
                strafePidOutput = -strafePidOutput * 1; //Speed multiplier
                xPos = false;
            } else {
                strafePidOutput = 0;
                xPos = true;
            } 

            // if (Math.abs(zDiff) > 0.1) { // In meters
            //     translationPidOutput = translationPidController.calculate(zDiff, 0);
            //     translationPidOutput = translationPidOutput * 0.2; //Speed multiplier
            //     zPos = false;
            // } else {
            //     translationPidOutput = 0;
            //     zPos = true;
            // } 

            if (xPos && rotationPos) {
                cancel();
            }

            swerveDrive.drive(0, strafePidOutput, rotationPidOutput, true, true); // Increase multiplier if needed
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

