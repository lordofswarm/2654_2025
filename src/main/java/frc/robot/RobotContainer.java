// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final IntakeSubsystem m_IntakeMotor = new IntakeSubsystem();
  private final DrivetrainSubsystem drivetrain = new DrivetrainSubsystem();
  private final SendableChooser<Command> autoChooser = new SendableChooser<>();
  private final Joystick driveJoystick;
  private boolean fieldCentric;
  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);
      private final CommandJoystick c_driverController = 
      new CommandJoystick(OperatorConstants.mDriverControllerPort);
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    
    // Configure the trigger bindings
    configureBindings();
 // Subsystem defined here
    final DrivetrainSubsystem drivetrain = new DrivetrainSubsystem();
    final Joystick driveJoystick;
      //private final Joystick steerJoystick;
      boolean fieldCentric;
//Controller Call
    final CommandJoystick m_driverController =
      new CommandJoystick(OperatorConstants.kDriverControllerPort);
    final CommandJoystick c_driverController = 
      new CommandJoystick(OperatorConstants.mDriverControllerPort);
    final XboxController m_XboxController = 
      new XboxController(0);
 drivetrain.setDefaultCommand(
      new DriveCommand(
          // Forward velocity supplier.
          m_XboxController::getLeftY,
          //driveJoystick::getY,
          // Sideways velocity supplier.
          m_XboxController::getLeftX,
          //driveJoystick::getX,
          // Rotation velocity supplier.
          m_XboxController::getRightX,
          //driveJoystick::getZ,
          () -> fieldCentric,
          drivetrain       
             
      )
    );
    configureBindings();
    configureAutochooser();
  }
   private void configureBindings() {
      //JoystickButton zeroGyroButton = new JoystickButton(driveJoystick, 0);
      //zeroGyroButton.onTrue(new InstantCommand(() -> drivetrain.zeroGyro(), drivetrain));
  
      JoystickButton driveModeToggleButton = new JoystickButton(driveJoystick, 5);
      ParallelCommandGroup driveModeToggleCommandGroup = new ParallelCommandGroup(
        new InstantCommand( () -> fieldCentric = !fieldCentric), 
        new InstantCommand( () -> {if(fieldCentric){System.out.println("FIELD CENTRIC");} else {System.out.println("ROBOT CENTRIC");}})
      );
      driveModeToggleButton.onTrue(driveModeToggleCommandGroup);
    

  }

private void configureAutochooser() {
  Command DefaultAuto = Autos.exampleAuto(m_exampleSubsystem);
  Command autoCommand1 = Autos.auto1(drivetrain, () -> fieldCentric);

  autoChooser.setDefaultOption("Default Auto Mode", DefaultAuto);

  }
  
  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
*/

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(m_exampleSubsystem);
  }
}
