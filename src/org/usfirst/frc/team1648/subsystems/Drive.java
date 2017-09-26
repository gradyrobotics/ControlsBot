package org.usfirst.frc.team1648.subsystems;

public class Drive {

	DriveTrain driveTrain;
	GyroSensor gyro;
	EncoderSensor encoder;
	/**
	 * A drive function that handles all Tele-Op driving.
	 * 	For Autonomous driving see AutoDrive
	 * 	For the DriveTrain itself see DriveTrain
	 *
	 * Inputs:
	 *  the drivetrain itself
	 *  a gyro(preferably the spartan board one)
	 *  an encoder
	 * 
	 * Methods Provided:
	 *  driveAngle(int angle, double power);
	 *  turnAngle(int angle, double power);
	 *  turnRightDistance(double meters, double power);
	 *  turnLeftDistance(double meters, double power);
	 *  driveDistance(double meters, double power);
	 *  driveFull(int angle, double meters, double power); //Exists but don't overuse
	 */
	public Drive(DriveTrain driveTrain, GyroSensor gyro, EncoderSensor encoder) {
		
		this.driveTrain = driveTrain;
		this.encoder = encoder;
		this.gyro = gyro;
	}
	
	public void driveAngle(int angle, double power) {
		
	}
	
	
	
}
