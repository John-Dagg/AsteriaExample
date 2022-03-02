package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.MotorControllerFactory;

public class Intake extends SubsystemBase {

    private VictorSPX mIntakeMotor;
    private DoubleSolenoid mFourBar;

    public Intake(){

        mIntakeMotor = MotorControllerFactory.makeVictorSPX(Constants.Intake.intakeMotorPort);

        mFourBar = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.Intake.fourBarPorts[0], Constants.Intake.fourBarPorts[1]);

    }

    public void intake(){
        mIntakeMotor.set(VictorSPXControlMode.PercentOutput, 0.5);
    }

    public void outtake(){
        mIntakeMotor.set(VictorSPXControlMode.PercentOutput, -0.5);
    }

    public void defaultIntake(){
        mIntakeMotor.set(VictorSPXControlMode.PercentOutput, 0);
    }

    public void extendFourBar(){
        mFourBar.set(DoubleSolenoid.Value.kForward);
    }

    public void retractFourBar(){
        mFourBar.set(DoubleSolenoid.Value.kReverse);
    }

    public boolean getFourBarState(){
        if(mFourBar.get() == DoubleSolenoid.Value.kForward){
            return false;
        } else if (mFourBar.get() == DoubleSolenoid.Value.kReverse){
            return true;
        } else {
            return true; // Default but should never happen
        }
    }
}
