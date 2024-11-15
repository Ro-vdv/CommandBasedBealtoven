package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.Constants.ArmConstants;

public class Arm extends PIDSubsystem {

    public double destination = 0;

    public final TalonFX armMotor;

    public Arm() {

        super (new PIDController(ArmConstants.kP, ArmConstants.kI, ArmConstants.kD));

        armMotor = new TalonFX(7);
        armMotor.setPosition(0.0);

        //armMotor.setInverted(m_enabled);
    }

    public void move (){
        enable();

        setSetpoint(destination);
    }

    public void setDestination(double desiredDestination){
        destination = desiredDestination;
        move();
    }

    @Override
    public void useOutput(double output, double setpoint) {

        var speedCap = 8; 
        armMotor.setVoltage(-MathUtil.clamp(output, -speedCap, speedCap));
    }

    @Override
    protected double getMeasurement() {

        return armMotor.getPosition().getValue() * ArmConstants.kEncoderDistancePerPulseRAD * -1;
    }

    @Override
    public void periodic() {

        super.periodic();
    }

}
