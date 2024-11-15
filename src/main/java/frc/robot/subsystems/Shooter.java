package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase.ControlType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase{

    private CANSparkMax leftMotor;
    private CANSparkMax rightMotor;

    private RelativeEncoder m_leftEncoder;
    private RelativeEncoder m_rightEncoder;
    public ShooterSetpoint sendSetpoint;

    public ShooterState state;

    // shooter states used to create a toggle button
    public enum ShooterState {
        IDLE,
        WARMING,
        WARMED,
        SHOOTING
    }

    public Shooter(){
        leftMotor = new CANSparkMax(62, MotorType.kBrushless);
        rightMotor = new CANSparkMax(61, MotorType.kBrushless);

        leftMotor.restoreFactoryDefaults();
        rightMotor.restoreFactoryDefaults();

        leftMotor.setInverted(true);

        leftMotor.setSmartCurrentLimit(20);
        rightMotor.setSmartCurrentLimit(20);

        m_leftEncoder = leftMotor.getEncoder();
        m_rightEncoder = rightMotor.getEncoder();

        var pidController = leftMotor.getPIDController();
        pidController.setP(0.000012);
        pidController.setD(0.0002);
        pidController.setFF(0.000172);
        pidController.setOutputRange(-1, 1);

        pidController = rightMotor.getPIDController();
        pidController.setP(0.000012);
        pidController.setD(0.0002);
        pidController.setFF(0.000172);
        pidController.setOutputRange(-1, 1);

        state = ShooterState.IDLE;
    }

    // sets up setpoints to be used as RPM values in shooter cmd
    public enum ShooterSetpoint {
            zero(0, 0),
            plopSetpoint(1500, 1500),
            speakerSetpoint(5200, 5200),
            podiumSetpoint(5200, 5200),
            ampSetpoint(2000, 2000);

            private final double leftVelocity;
            private final double rightVelocity;

            ShooterSetpoint(double leftVelocity, double rightVelocity) {
                this.leftVelocity = leftVelocity;
                this.rightVelocity = rightVelocity;
            }

            public double getLeftVelocity() {
                return leftVelocity;
            }

            public double getRightVelocity() {
                return rightVelocity;
            }
        }

    // turns motors on and uses the PID to control its increase until RPM value is reached
    public void setTargetVelocity(ShooterSetpoint setpoint){
        if (setpoint == null){
            setpoint = ShooterSetpoint.zero;
        }

        sendSetpoint = setpoint;

        leftMotor.getPIDController().setReference(setpoint.getLeftVelocity(), ControlType.kVelocity);
        rightMotor.getPIDController().setReference(setpoint.getRightVelocity(), ControlType.kVelocity);
    }

    // turns motors off quickly
    public void setIdle(){
        setTargetVelocity(ShooterSetpoint.zero);
    }

    // turns motors on to speaker RPM
    public void setWarming(){
        setTargetVelocity(ShooterSetpoint.speakerSetpoint);
    }

   public boolean isAtTargetVelocity() {
    // Checks if both motors are within a small range of the setpoint to consider it warmed
    double leftVelocity = m_leftEncoder.getVelocity();
    double rightVelocity = m_rightEncoder.getVelocity();
    double targetLeft = sendSetpoint.getLeftVelocity();
    double targetRight = sendSetpoint.getRightVelocity();
    
    return Math.abs(leftVelocity - targetLeft) < 250 && Math.abs(rightVelocity - targetRight) < 250;
    }
}

