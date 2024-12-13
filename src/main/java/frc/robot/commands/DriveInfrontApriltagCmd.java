package frc.robot.commands;

import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Swerve; 
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveInfrontApriltagCmd extends CommandBase {
    private final Limelight limelight;
    private final Swerve swerveDrive;

    private final double targetID = 6; 

    private PIDController pidController;

    public DriveInfrontApriltagCmd(Limelight limelight, Swerve swerveDrive) {
        this.limelight = limelight;
        this.swerveDrive = swerveDrive;
        this.pidController = new PIDController(0.017, 0, 0.0007);

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

            double pidOutput = pidController.calculate(x, 0); // 0 changes offset
            
            if (Math.abs(x) > 0) { // Tolerance level
                swerveDrive.drive(0, (-pidOutput * 1), 0, false, true);
            } else {
                swerveDrive.drive(0, 0, 0, false, true);
            }
        } else {
            swerveDrive.drive(0, 0, 0, false, true);
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
