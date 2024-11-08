package frc.robot;

import edu.wpi.first.math.util.Units;

public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static class ArmConstants {
    
    public static final double ampPosition = Units.degreesToRadians(120);
    public static final double podiumPosition = Units.degreesToRadians(29);
    public static final double passingPosition = Units.degreesToRadians(33);
    public static final double speakerPosition = Units.degreesToRadians(12);
    public static final double climbPosition = Units.degreesToRadians(95);
    public static final double zeroPosition = 0;
  }
}