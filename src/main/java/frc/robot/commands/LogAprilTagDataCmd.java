package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Kicker;
import frc.robot.subsystems.Limelight;

public class LogAprilTagDataCmd extends CommandBase {

    private final Limelight limelight;
  
    //public static DigitalInput linebreak;

    public LogAprilTagDataCmd(Limelight limelight) {
        this.limelight = limelight;

        addRequirements(limelight);
    }
    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if (limelight.isTargetVisible()) {
            double x = limelight.getX();
            double y = limelight.getY();
            String direction = (x >= 0) ? "    turn Right" : "    turn Left";
            if (x <= 1 && x >=- 1) {
                direction = "      In position";
            } 
            System.out.println("AprilTag ID: " + limelight.getAprilTagID() + " X: " + x + " Y: " + y + direction);
        }
    }

    @Override
    public boolean isFinished() {
        return false; // Run continuously while the button is pressed
    }

    @Override
  public void end(boolean interrupted) {

  }
}
