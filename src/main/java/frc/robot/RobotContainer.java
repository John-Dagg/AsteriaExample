// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.io.Button;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  //Subsystems
  private final Drivetrain mDrivetrain = new Drivetrain();
  private final Intake mIntake = new Intake();
  private final RollerClaw mRollerClaw = new RollerClaw();
  private final Hatcher mHatcher = new Hatcher();
  private final Elevator mElevator= new Elevator();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

    configureButtonBindings();

    mDrivetrain.setDefaultCommand(new RunCommand(mDrivetrain::arcadeDrive, mDrivetrain));
    mIntake.setDefaultCommand(new RunCommand(mIntake::defaultIntake, mIntake));
    mElevator.setDefaultCommand(new RunCommand(mElevator::joystickElevatorControl, mElevator));
    mRollerClaw.setDefaultCommand(new RunCommand(mRollerClaw::defaultRoller, mRollerClaw));

  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    //Drivetrain - Driver Controller
    new JoystickButton(Constants.driverController, Button.ButtonID.LEFT_BUMPER.getID())
            .whenPressed(new ConditionalCommand(
                    new InstantCommand(mDrivetrain::highGear),
                    new InstantCommand(mDrivetrain::lowGear),
                    mDrivetrain::getGear));

    //Intake - Driver Controller
    new JoystickButton(Constants.driverController, Button.ButtonID.A.getID())
            .whenHeld(new RunCommand(mIntake::intake));
    new JoystickButton(Constants.driverController, Button.ButtonID.A.getID())
            .whenHeld(new RunCommand(mRollerClaw::intakeClaw));


    new JoystickButton(Constants.driverController, Button.ButtonID.B.getID())
            .whenHeld(new RunCommand(mIntake::outtake));
    new JoystickButton(Constants.driverController, Button.ButtonID.B.getID())
            .whenHeld(new RunCommand(mRollerClaw::outtakeClaw));

    new JoystickButton(Constants.driverController, Button.ButtonID.RIGHT_BUMPER.getID())
            .whenPressed(new ConditionalCommand(
                    new InstantCommand(mIntake::extendFourBar),
                    new InstantCommand(mIntake::retractFourBar),
                    mIntake::getFourBarState));

    //RollerClaw - Driver Controller
    new JoystickButton(Constants.operatorController, Button.ButtonID.X.getID())
            .whenHeld(new RunCommand(mRollerClaw::outtakeClaw));

    //Hatcher - Operator Controller
    new JoystickButton(Constants.driverController, Button.ButtonID.X.getID())
            .whenPressed(new ConditionalCommand(
                    new InstantCommand(mHatcher::extendHatcher),
                    new InstantCommand(mHatcher::retractHatcher),
                    mHatcher::getHatchExtenderState));

    new JoystickButton(Constants.driverController, Button.ButtonID.Y.getID())
            .whenPressed(new ConditionalCommand(
                    new InstantCommand(mHatcher::openHatcher),
                    new InstantCommand(mHatcher::closeHatcher),
                    mHatcher::getHatchActuatorState));

    //Elevator - Operator Controller
    new JoystickButton(Constants.operatorController, Button.ButtonID.A.getID())
            .whenHeld(new ConditionalCommand(
                    new RunCommand(mElevator::lowHatch),
                    new RunCommand(mElevator::lowCargo),
                    mElevator::getMode));

    new JoystickButton(Constants.operatorController, Button.ButtonID.B.getID())
            .whenHeld(new ConditionalCommand(
                    new RunCommand(mElevator::midHatch),
                    new RunCommand(mElevator::midCargo),
                    mElevator::getMode));

    new JoystickButton(Constants.operatorController, Button.ButtonID.X.getID())
            .whenHeld(new ConditionalCommand(
                    new RunCommand(mElevator::highHatch),
                    new RunCommand(mElevator::highCargo),
                    mElevator::getMode));

    new JoystickButton(Constants.operatorController, Button.ButtonID.Y.getID())
            .whenHeld(new RunCommand(mElevator::playerStation));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }
}
