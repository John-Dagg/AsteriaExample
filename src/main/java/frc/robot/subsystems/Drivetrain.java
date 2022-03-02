package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.io.Axis;
import frc.robot.util.MotorControllerFactory;

public class Drivetrain extends SubsystemBase {

    private TalonSRX mLeftLeader, mLeftFollowerA, mLeftFollowerB, mRightLeader, mRightFollowerA, mRightFollowerB;

    private DoubleSolenoid mShifter;

    public Drivetrain(){

        mLeftLeader = MotorControllerFactory.makeTalonSRX(Constants.Drivetrain.leftLeaderPort);
        mLeftFollowerA = MotorControllerFactory.makeTalonSRX(Constants.Drivetrain.leftFollowerAPort);
        mLeftFollowerB = MotorControllerFactory.makeTalonSRX(Constants.Drivetrain.leftFollowerBPort);
        mRightLeader = MotorControllerFactory.makeTalonSRX(Constants.Drivetrain.rightLeaderPort);
        mRightFollowerA = MotorControllerFactory.makeTalonSRX(Constants.Drivetrain.rightFollowerAPort);
        mRightFollowerB = MotorControllerFactory.makeTalonSRX(Constants.Drivetrain.rightFollowerBPort);

        mShifter = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.Drivetrain.shifterPorts[0], Constants.Drivetrain.shifterPorts[1]);

        mRightLeader.setInverted(true);
        mRightFollowerA.setInverted(true);
        mRightFollowerB.setInverted(true);

        mLeftFollowerA.follow(mLeftLeader);
        mLeftFollowerB.follow(mLeftLeader);
        mRightFollowerA.follow(mRightLeader);
        mRightFollowerB.follow(mRightLeader);

    }


    public void arcadeDrive(){

        double turn, throttle, deadbandThrottle, deadbandTurn, left, right;

        throttle = Constants.driverController.getRawAxis(Axis.AxisID.LEFT_Y.getID());
        turn = Constants.driverController.getRawAxis(Axis.AxisID.RIGHT_X.getID());

        deadbandThrottle = deadband(throttle);
        deadbandTurn = deadband(turn);

        left = deadbandThrottle - deadbandTurn;
        right = deadbandThrottle + deadbandTurn;


        mLeftLeader.set(TalonSRXControlMode.PercentOutput, left);
        mRightLeader.set(TalonSRXControlMode.PercentOutput, right);
    }

    public double deadband(double percentOutput){
        if(Math.abs(percentOutput) > Constants.Drivetrain.deadband){
            return percentOutput;
        } else if (percentOutput < Constants.Drivetrain.deadband){
            return 0;
        } else {
            return 0;
        }
    }

    public void lowGear(){
        mShifter.set(DoubleSolenoid.Value.kForward);
    }

    public void highGear(){
        mShifter.set(DoubleSolenoid.Value.kReverse);
    }

    public boolean getGear(){
        if(mShifter.get() == DoubleSolenoid.Value.kForward){ //lowGear
            return true;
        } else if(mShifter.get() == DoubleSolenoid.Value.kReverse){ //highGear
            return false;
        } else {
            return false;
        }
    }

}
