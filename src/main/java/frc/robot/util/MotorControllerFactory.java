package frc.robot.util;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class MotorControllerFactory {

    public static TalonSRX makeTalonSRX(int port){
        TalonSRX mTalon = new TalonSRX(port);
        mTalon.configFactoryDefault();
        return mTalon;
    }

    public static VictorSPX makeVictorSPX(int port){
        VictorSPX mVictor = new VictorSPX(port);
        mVictor.configFactoryDefault();
        return mVictor;
    }

    public static TalonFX makeTalonFX(int port){
        TalonFX mTalonFX = new TalonFX(port);
        mTalonFX.configFactoryDefault();
        return mTalonFX;
    }

}
