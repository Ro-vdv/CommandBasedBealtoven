package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
// import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.ArmConstants;

public class Arm extends PIDSubsystem {

    //variables
    // public ArmPosition position;
    private double destination = 0;

    //inputs & motors
    private TalonFX armMotor;


    ShuffleboardTab tab = Shuffleboard.getTab("Arm");
    
    public Arm() {
        super (new PIDController(ArmConstants.kP, ArmConstants.kI, ArmConstants.kD));

        armMotor = new TalonFX(7);
        armMotor.setPosition(0.0);

        //Shuffleboard tabs
        tab.addDouble("Current Setpoint", () -> getSetpoint());
        tab.addDouble("Deg Encoder", () -> Units.radiansToDegrees(getMeasurement()));
        tab.addDouble("GetMeasurement", () -> getMeasurement());
        tab.addDouble("Raw getPos", () -> armMotor.getPosition().getValue());
        tab.addDouble("Raw getRotor", () -> armMotor.getRotorPosition().getValue());

    }

    // public enum ArmPosition {
    //     Zero(),
    //     Amp(),
    //     Speaker();

    //     public String Zero(){
    //         return "Zero";
    //     }

    //     public String Amp(){
    //         return "Amp";
    //     }

    //     public String Speaker(){
    //         return "Speaker";
    //     }
    // }
    
    public void Move(){

        enable();
        setSetpoint(destination);
        //double positionInUnits = Units.radiansToDegrees(getMeasurement());
    }

    public void setDestination(double desiredDestination) {
       destination = desiredDestination;
       Move();

    }

    @Override
    public void useOutput(double output, double setpoint) {

        // System.out.println(output);
        //Keeps the arm motor from exploding i assume
        var speedCap = 8; // maxSpeedEntry.getDouble(5);
        armMotor.setVoltage(-MathUtil.clamp(output, -speedCap, speedCap));
    }

    @Override
    public double getMeasurement() {
        //exactly what it says on the tin
        return armMotor.getPosition().getValue() * ArmConstants.kEncoderDistancePerPulseRAD * -1;
    }

    @Override
    public void periodic() {

        super.periodic();
    }

    // public  setDesiredDestination(double desiredDestination, ArmPosition position) {
    //     if (desiredDestination == Constants.ArmConstants.zeroPosition){
    //         position = ArmPosition.Zero;
    //     } else if (desiredDestination == Constants.ArmConstants.ampPosition){
    //         position = ArmPosition.Amp;
    //     } else if (desiredDestination == Constants.ArmConstants.speakerPosition){
    //         position = ArmPosition.Speaker;
    //     }
    // }

}