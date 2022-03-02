package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Hatcher extends SubsystemBase {

    private DoubleSolenoid mHatchExtender, mHatchActuator;

    public Hatcher(){

        mHatchExtender = new DoubleSolenoid(PneumaticsModuleType.CTREPCM,Constants.Hatcher.hatchExtenderPorts[0],
                Constants.Hatcher.hatchExtenderPorts[1]);
        mHatchActuator = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.Hatcher.hatchActuatorPorts[0],
                Constants.Hatcher.hatchActuatorPorts[1]);

    }

    public void extendHatcher(){
        mHatchExtender.set(DoubleSolenoid.Value.kForward);
    }

    public void retractHatcher(){
        mHatchExtender.set(DoubleSolenoid.Value.kReverse);
    }

    public boolean getHatchExtenderState(){
        if (mHatchExtender.get() == DoubleSolenoid.Value.kReverse){
            return true;
        } else if (mHatchExtender.get() == DoubleSolenoid.Value.kForward){
            return false;
        } else {
            return false; // Default, should never happen
        }
    }

    public void openHatcher(){
        mHatchActuator.set(DoubleSolenoid.Value.kForward);
    }

    public void closeHatcher(){
        mHatchActuator.set(DoubleSolenoid.Value.kReverse);
    }

    public boolean getHatchActuatorState(){
        if (mHatchActuator.get() == DoubleSolenoid.Value.kReverse){
            return true;
        } else if (mHatchActuator.get() == DoubleSolenoid.Value.kForward){
            return false;
        } else {
            return false; // Default, should never happen
        }
    }

}
