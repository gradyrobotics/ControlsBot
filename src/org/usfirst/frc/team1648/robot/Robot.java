package org.usfirst.frc.team1648.robot;

import org.usfirst.frc.team1648.subsystems.Drive;
import org.usfirst.frc.team1648.subsystems.DriveTrain;
import org.usfirst.frc.team1648.subsystems.EncoderSensor;
import org.usfirst.frc.team1648.subsystems.GyroSensor;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM(RoboRio) is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	//Declaring Sensors
	EncoderSensor leftEncoder;
	EncoderSensor rightEncoder;
	GyroSensor gyro;
	
	//Declaring Subsystems
	Drive drive;
	DriveTrain driveTrain;
	
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		leftEncoder = new EncoderSensor(11, 10); //ports are backwards b/c this side is left
		rightEncoder = new EncoderSensor(12, 13);
		gyro = new GyroSensor();
		
		driveTrain = new DriveTrain(8, 6, 2, 3);
		drive = new Drive(driveTrain, gyro, leftEncoder, leftEncoder);
		
	}

	/**
	 * This function is run whenever autonomous is started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void autonomousInit() {
		gyro.gyroReset();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		//Smart DashBoard Data
		gyro.getGyroData();
		leftEncoder.getEncoderData("Left Encoder");
		rightEncoder.getEncoderData("Right Encoder");
		
		//Autonomous actual
//		drive.driveAngle(90, 1, 1); 
		//drive.driveDistance(24, 1);
		driveTrain.driveBoth(1, 1);
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		
	}
}

