package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.TalonSRXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.io.Axis;
import frc.robot.io.Button;
import frc.robot.util.MotorControllerFactory;

import static frc.robot.subsystems.Elevator.Mode.kCargo;
import static frc.robot.subsystems.Elevator.Mode.kHatch;
import static frc.robot.subsystems.Elevator.elevatorPosition.*;

public class Elevator extends SubsystemBase {

    private TalonSRX mElevatorMotor;
    private DigitalInput mLimitSwitch;

    private double mPosition;
    private double mAcceptableError = 50;

    private boolean mCurrentState, mFinalState;
    private boolean mLastState = false;

    public Elevator(){

        mElevatorMotor = MotorControllerFactory.makeTalonSRX(Constants.Elevator.elevatorMotor);
        mElevatorMotor.setNeutralMode(NeutralMode.Brake); //So the elevator doesn't fall back down

        //Arbitrary timeout for now
        mElevatorMotor.configSelectedFeedbackSensor(TalonSRXFeedbackDevice.CTRE_MagEncoder_Relative, 0, 20);
        mElevatorMotor.configReverseSoftLimitThreshold(0); //So the elevator shouldn't go into the floor and pop the chains off
        mElevatorMotor.setSensorPhase(true);
        mElevatorMotor.setSelectedSensorPosition(0);

        mLimitSwitch = new DigitalInput(Constants.Elevator.limitSwitchPort);

    }

    public void joystickElevatorControl(){
        if (!mLimitSwitch.get()) {
            mElevatorMotor.set(TalonSRXControlMode.PercentOutput, -Constants.operatorController.getRawAxis(Axis.AxisID.RIGHT_Y.getID()));
        }
        mElevatorMotor.set(TalonSRXControlMode.PercentOutput, -Constants.operatorController.getRawAxis(Axis.AxisID.RIGHT_Y.getID()));

        System.out.println(mElevatorMotor.getSelectedSensorPosition());
//        System.out.println(mLimitSwitch.get());
    }

    public void printTicks(){
        System.out.println(mElevatorMotor.getSelectedSensorPosition());
    }

    public double updateEncoder(){
        return mElevatorMotor.getSelectedSensorPosition();
    }

    public boolean getMode(){
        mCurrentState = Constants.operatorController.getRawButtonPressed(Button.ButtonID.LEFT_STICK.getID());
        if(mCurrentState && !mLastState){
            mFinalState = true;
        } else {
            mFinalState = false;
        }
        mLastState = mCurrentState;

        if(mFinalState){
            System.out.println("Hatch Mode");
            return true;
        } else {
            System.out.println("Cargo Mode");
            return false;
        }
    }

    public void lowHatch(){
        mPosition = updateEncoder();
        if (mPosition < LOW_HATCH.getPosition()){
            mElevatorMotor.set(TalonSRXControlMode.PercentOutput, 0);
            System.out.println("Something is wrong! Abort! Position should not be below 0.");
        }
        if (mPosition > LOW_HATCH.getPosition() + mAcceptableError){
            mElevatorMotor.set(TalonSRXControlMode.PercentOutput, -1);
        }
    }

    public void midHatch(){
        mPosition = updateEncoder();
        if(mPosition < MID_HATCH.getPosition() - mAcceptableError){
            mElevatorMotor.set(TalonSRXControlMode.PercentOutput, 1);
        }
        if (mPosition > MID_HATCH.getPosition() + mAcceptableError){
            mElevatorMotor.set(TalonSRXControlMode.PercentOutput, -1);
        }
    }

    public void highHatch(){
        mPosition = updateEncoder();
        if(mPosition < HIGH_HATCH.getPosition() - mAcceptableError){
            mElevatorMotor.set(TalonSRXControlMode.PercentOutput, 1);
        }
        if (mPosition > HIGH_HATCH.getPosition() + mAcceptableError){
            mElevatorMotor.set(TalonSRXControlMode.PercentOutput, -1);
        }
    }

    public void lowCargo(){
        mPosition = updateEncoder();
        if(mPosition < LOW_CARGO.getPosition() - mAcceptableError){
            mElevatorMotor.set(TalonSRXControlMode.PercentOutput, 1);
        }
        if (mPosition > LOW_CARGO.getPosition() + mAcceptableError){
            mElevatorMotor.set(TalonSRXControlMode.PercentOutput, -1);
        }
    }

    public void midCargo(){
        mPosition = updateEncoder();
        if(mPosition < MID_CARGO.getPosition() - mAcceptableError){
            mElevatorMotor.set(TalonSRXControlMode.PercentOutput, 1);
        }
        if (mPosition > MID_CARGO.getPosition() + mAcceptableError){
            mElevatorMotor.set(TalonSRXControlMode.PercentOutput, -1);
        }
    }

    public void highCargo(){
        mPosition = updateEncoder();
        if(mPosition < HIGH_CARGO.getPosition() - mAcceptableError){
            mElevatorMotor.set(TalonSRXControlMode.PercentOutput, 1);
        }
        if (mPosition > HIGH_CARGO.getPosition() + mAcceptableError){
            mElevatorMotor.set(TalonSRXControlMode.PercentOutput, -1);
        }
    }

    public void playerStation(){
        mPosition = updateEncoder();
        if(mPosition < PLAYER_STATION.getPosition() - mAcceptableError){
            mElevatorMotor.set(TalonSRXControlMode.PercentOutput, 1);
        }
        if (mPosition > PLAYER_STATION.getPosition() + mAcceptableError){
            mElevatorMotor.set(TalonSRXControlMode.PercentOutput, -1);
        }
    }

    public enum elevatorPosition {
        LOW_HATCH(0, kHatch), MID_HATCH(9100, kHatch), HIGH_HATCH(18000, kHatch),
        LOW_CARGO(5800, kCargo), MID_CARGO(14500, kCargo), HIGH_CARGO(22000, kCargo), PLAYER_STATION(11200, kCargo);

        elevatorPosition(int position, Mode mode){
            mPosition = position;
            mMode = mode;
        }

        private int mPosition;
        private Mode mMode;

        public Mode getMode(){
            return mMode;
        }

        public double getPosition(){
            return mPosition;
        }
    }

    public enum Mode{
        kHatch, kCargo;
    }
/*
    LOW_HATCH - 0
    MID_HATCH - 9100
    HIGH_HATCH - 18000
    LOW_CARGO - 5800
    MID_CARGO - 14500
    HIGH_CARGO - 22000
    PLAYER_STATION - 11200

 */
}
