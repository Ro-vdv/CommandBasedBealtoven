package frc.robot.commands;

import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Swerve; 
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TurnToApriltagDriveCmd extends CommandBase {
    private final Limelight limelight;
    private final Swerve swerveDrive;

    private final double targetID = 6; 

    // how many degrees back is the limelight rotated from perfectly vertical?
    double limelightMountAngleDegrees = 19.0; 

    // distance from the center of the Limelight lens to the floor
    double limelightLensHeightInches = 23.4; 
    
    // distance from the target to the floor
    double goalHeightInches = 36.0; 
    
    double distanceFromLimelightToGoalInches = 0;
    double angleToGoalRadians = 0;
    double pidOutput = 0;
    double driving = 0;
    boolean distancePos = false;
    boolean rotationPos = false;

    private PIDController pidController;

    public TurnToApriltagDriveCmd(Limelight limelight, Swerve swerveDrive) {
        this.limelight = limelight;
        this.swerveDrive = swerveDrive;
        this.pidController = new PIDController(0.008, 0.005, 0.0005);

        addRequirements(limelight, swerveDrive);
    }

    @Override
    public void initialize() {
        pidController.reset();
    }

    @Override
    public void execute() {
        if (limelight.getAprilTagID() == targetID) {
            double x = limelight.getX(); // Get the X offset from the target
            double ty = limelight.getY();

            angleToGoalRadians = Units.degreesToRadians(limelightMountAngleDegrees + ty);
            distanceFromLimelightToGoalInches = (goalHeightInches - limelightLensHeightInches) / Math.tan(angleToGoalRadians);
            System.out.println(distanceFromLimelightToGoalInches);
            
            if (Math.abs(x) > 0.6) { // Tolerance level
                pidOutput = pidController.calculate(x, 0); 
                rotationPos = true;        
            } else {
                pidOutput = 0;
                rotationPos = false;
            }
            if (distanceFromLimelightToGoalInches < 65 && distanceFromLimelightToGoalInches > 60) {
                if (distanceFromLimelightToGoalInches < 60) {
                    driving = 1;
                    distancePos = false;
                } else if (distanceFromLimelightToGoalInches > 65) {
                    driving = -1;
                    distancePos = false;
                } 
            } else {
                driving = 0;
                distancePos = true;
            }
            
            if (distancePos && rotationPos) {
                pidOutput = 0; // 0 changes offset
                driving = 0;
            }

            swerveDrive.drive(driving * 0.1, 0, pidOutput, false, true);
        } 
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        swerveDrive.drive(0, 0, 0, interrupted, interrupted);
    }
}
