package org.usfirst.frc.team1648.robot;

import org.usfirst.frc.team1648.subsystems.EncoderSensor;
import org.usfirst.frc.team1648.subsystems.GyroSensor;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SensorBase;

/**
 * The VM(RoboRio) is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	EncoderSensor encoder;
	GyroSensor gyro;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		//Handling Sensor Stuff
		//gyro = new GyroSensor();
		//gyro.gyroInit();
		
		encoder = new EncoderSensor(4, 5);
		
	}

	/**
	 * This function is run whenever autonomous is started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void autonomousInit() {
		//Handling Sensor Stuff
		//gyro.gyroReset();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		//Smart DashBoard Data
		System.out.println("Rate: " + encoder.getRate() + "/n" + "Count: " + encoder.getCount());
		//gyro.getGyroData();
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

