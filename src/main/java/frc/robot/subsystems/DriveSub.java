/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.DriveCommand;
import frc.robot.utilities.Drive;

/**
 * Add your docs here.
 */
public class DriveSub extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  TalonSRX rf,rb,lf,lb;
  Drive drive;


  public DriveSub()
  {
    rf = new TalonSRX(RobotMap.FrontRightMotor);
    rb = new TalonSRX(RobotMap.BackRightMotor);
    lf = new TalonSRX(RobotMap.FrontLeftMotor);
    lb = new TalonSRX(RobotMap.BackLeftMotor);


    //Tells the left side that it should be inverted so that we drive stight with each side having positive motor values.
    lf.setInverted(true);
    lb.setInverted(true);


    //Set the back motors to follow the front motors.
    rb.set(ControlMode.Follower, rf.getDeviceID());
    lb.set(ControlMode.Follower, lf.getDeviceID());


    //Config all talons.
    configTalons(rf);
    configTalons(rb);
    configTalons(lf);
    configTalons(lb);

    drive = new Drive(lf,rf);


  }

  public void driveArcade(double moveValue, double rotateValue)
  {
    drive.driveArcade(moveValue, rotateValue);
  }


  public void configTalons(TalonSRX tSrx)
  {
    //Tells the talon that the max output that it can give is between 1 and -1 which would mean full forward and full backward.
    tSrx.configPeakOutputForward(1,0);
    tSrx.configPeakOutputReverse(-1,0);

    //Tells the talon that it should current limit its self so that we dont blow a 40Amp breaker.
    tSrx.configPeakCurrentLimit(40, 0);
    tSrx.enableCurrentLimit(true);
    tSrx.configContinuousCurrentLimit(40, 0);
    //The max output current is 40Amps for .25 of a second.
    tSrx.configPeakCurrentDuration(250, 0);

    //Tells the talon that it should only apply 12 volts (or less) to the motor.
    tSrx.configVoltageCompSaturation(12, 0);
  }



  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new DriveCommand());
  }
}
