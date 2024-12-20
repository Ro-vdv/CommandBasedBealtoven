package frc.robot.commands.Vision;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.LimelightHelpers;
import frc.robot.subsystems.Limelight;

public class LogAprilTagDataCmd extends CommandBase {

    private final Limelight limelight;

    double[] test;
  
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

            double[] botPose = LimelightHelpers.getTargetPose_CameraSpace("");

            double yawDeg = botPose[4];
            double zDis = botPose[2];

            double atDeg = yawDeg - x;
            double atRad = Math.toRadians(atDeg);
            double atXDis = zDis * (Math.tan(atRad));
            //double yawRad = Math.toRadians(yawDeg);

            // System.out.println("AprilTag ID: " + limelight.getAprilTagID() + "2D VALUES:   X: " + x + " Y: " + y );
            // System.out.println("");

            // System.out.println("3D VALUES:");

            // System.out.println("X: " + botPose[0]);
            // System.out.println("Y: " + botPose[1]);
            // System.out.println("Z: " + botPose[2]);
            // System.out.println("Yaw: " + botPose[4]);

            //System.out.println(test[10]);

            System.out.println("\n AT distance:    ");
            System.out.println(zDis);
            System.out.println(yawDeg);
            System.out.println(x);
            System.out.print("Tan( ):    ");
            System.out.println(Math.tan(yawDeg - x));
            System.out.println(atXDis);
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
