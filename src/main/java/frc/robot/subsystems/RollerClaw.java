package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.MotorControllerFactory;

public class RollerClaw extends SubsystemBase {

    private VictorSPX rollerClawMotor;

    public RollerClaw(){

        rollerClawMotor = MotorControllerFactory.makeVictorSPX(Constants.RollerClaw.rollerClawMotorPort);

    }

    public void defaultRoller(){
        rollerClawMotor.set(VictorSPXControlMode.PercentOutput, 0); //Idles and hold cargo into the claw
    }

    public void intakeClaw(){
        rollerClawMotor.set(VictorSPXControlMode.PercentOutput, 0.5);
    }

    public void outtakeClaw(){
        rollerClawMotor.set(VictorSPXControlMode.PercentOutput, -0.5);
    }
}
