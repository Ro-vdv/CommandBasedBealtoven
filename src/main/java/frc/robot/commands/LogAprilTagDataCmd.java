package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Kicker;
import frc.robot.subsystems.Limelight;

public class LogAprilTagDataCmd extends CommandBase {

    private final Limelight limelight;

    public boolean intaking = false;

    private final Intake intakeSubsystem;
    private final Kicker kickerSubsystem;
  
    //public static DigitalInput linebreak;

    public LogAprilTagDataCmd(Limelight limelight, Intake intakeSubsystem, Kicker kickerSubsystem) {
        this.limelight = limelight;
        this.intakeSubsystem = intakeSubsystem;
        this.kickerSubsystem = kickerSubsystem;

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
                if (!intaking && !Intake.isLineBroken()) {
                    autoIntake(true);
                    intaking = true;
                } 
            } else {
                autoIntake(false);
                intaking = false;
            }
            System.out.println("AprilTag ID: " + limelight.getAprilTagID() + " X: " + x + " Y: " + y + direction);
        } else {
            autoIntake(false);
            intaking = false;
        }

        if (Intake.isLineBroken()){
            autoIntake(false);
            intaking = false;
        }
    }

    public void autoIntake(boolean running) {
        if (!Intake.isLineBroken() && running){
            intakeSubsystem.startMotors(true);
            kickerSubsystem.startKicker(true);
        } else {
            intakeSubsystem.startMotors(false);
            kickerSubsystem.startKicker(false);
        }
    }

    @Override
    public boolean isFinished() {
        return false; // Run continuously while the button is pressed
    }

    @Override
  public void end(boolean interrupted) {
      autoIntake(false);
      intaking = false;
  }
}
