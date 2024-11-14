package frc.robot;

import edu.wpi.first.math.util.Units;

public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static class ArmConstants {
    
    //Motor stuff
    public static final double gearRatio = 1.0 / 120.0;
    public static final double kEncoderDistancePerPulse = 2048;
    public static final double kEncoderDistancePerPulseRAD = (2 * Math.PI) * gearRatio;

    //Arm positions, not all used yet
    public static final double ampPosition = Units.degreesToRadians(120);
    public static final double speakerPosition = Units.degreesToRadians(12);
    public static final double zeroPosition = 0;

    //PID values
    public static final double kP = 15;
    public static final double kI = 0;
    public static final double kD = 0;
    
  }
}