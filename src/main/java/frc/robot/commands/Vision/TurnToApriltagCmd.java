package frc.robot.commands.Vision;

import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Swerve; 
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;

public class TurnToApriltagCmd extends Command {
    private final Limelight limelight;
    private final Swerve swerveDrive;

    private final double targetID = 6; 

    private double pidOutput = 0;

    private PIDController pidController;

    public TurnToApriltagCmd(Limelight limelight, Swerve swerveDrive) {
        this.limelight = limelight;
        this.swerveDrive = swerveDrive;
        this.pidController = new PIDController(0.008, 0.005, 0.0005);

        addRequirements(limelight);
    }

    @Override
    public void initialize() {
        pidController.reset();
    }

    @Override
    public void execute() {
        if (limelight.getAprilTagID() == targetID) {
            double x = limelight.getX(); // Get the X offset from the target
            
            if (Math.abs(x) > 0.6) { // Tolerance level
                pidOutput = pidController.calculate(x, 0); // 0 changes offset
                pidOutput = pidOutput * 1;
            } else {
                pidOutput = 0;
            }

            swerveDrive.visionRotationVal(pidOutput, true);

        } else {
            swerveDrive.visionRotationVal(0, false);
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        swerveDrive.visionRotationVal(0, false);
    }
}
