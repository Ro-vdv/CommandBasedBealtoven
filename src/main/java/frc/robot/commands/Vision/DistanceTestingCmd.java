package frc.robot.commands.Vision;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Kicker;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Swerve;

public class DistanceTestingCmd extends CommandBase {
    
    private final Limelight limelight;
    private Swerve swerveDrive;

    // how many degrees back is your limelight rotated from perfectly vertical?
    double limelightMountAngleDegrees = 19.0; 

    // distance from the center of the Limelight lens to the floor
    double limelightLensHeightInches = 23.4; 

    // distance from the target to the floor
    double goalHeightInches = 58.0; 

    double distanceFromLimelightToGoalInches = 0;
    double angleToGoalRadians = 0;

        public DistanceTestingCmd(Limelight limelight, Swerve swerveDrive) {
            this.limelight = limelight;
            this.swerveDrive = swerveDrive;

            addRequirements(limelight, swerveDrive);
        }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        //calculate distance
        double ty = limelight.getY();

        angleToGoalRadians = Units.degreesToRadians(limelightMountAngleDegrees + ty);

        distanceFromLimelightToGoalInches = (goalHeightInches - limelightLensHeightInches) / Math.tan(angleToGoalRadians);

        System.out.println(distanceFromLimelightToGoalInches);
    }

    @Override
    public boolean isFinished() {
        return false; // Run continuously while the button is pressed
    }

    @Override
    public void end(boolean interrupted) {

    }
}
