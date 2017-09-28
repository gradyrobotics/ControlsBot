package org.usfirst.frc.team1648.subsystems;

public class Drive {

	DriveTrain driveTrain;
	GyroSensor gyro;
	EncoderSensor leftEncoder;
	EncoderSensor rightEncoder;
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
	 *  driveAngle(int angle, double turnPower, double power);
	 *  turnAngle(int angle, double turnPower);
	 *  rightDistance(double distance, double power);
	 *  leftDistance(double distance, double power);
	 *  driveDistance(double distance, double power);
	 *  driveFull(int angle, double meters, double power); //Exists but don't overuse
	 */
	public Drive(DriveTrain driveTrain, GyroSensor gyro, EncoderSensor leftEncoder, EncoderSensor rightEncoder) {
		
		this.driveTrain = driveTrain;
		this.leftEncoder = leftEncoder;
		this.rightEncoder = rightEncoder;
		this.gyro = gyro;
	}
	
	/**
	 * This function will attempt to drive forwards while correcting to the provided angle in reference to the original angle
	 * This is best used to achieve straight driving
	 * 
	 * angle is the angle you want to drive to, input between 1 and 360
	 * turnPower is the speed of turning. Input 1 for a perfect 90 degree turn. Input 0 to not turn at all. value between 1 and 0
	 * power is the speed at which you drive while not actively turning.
	 * @param angle
	 * @param power
	 * @param turnPower
	 */
	public void driveAngle(int angle, double turnPower, double power) {

		while (angle - gyro.getAngle() < 0) {
			driveTrain.driveBoth(power , power * (1 - turnPower));
		}
		
		while (angle - gyro.getAngle() > 0) {
			driveTrain.driveBoth(power * (1 - turnPower), power);
		}
		
		driveTrain.driveBoth(power, power);
	}
	
	/**
	 * Turns the robot to whatever angle you provide.
	 * 
	 * angle is the angle you want the robot to turn to relative to the robot's starting position
	 * power is the speed at which you want the robot to turn. (Value between 0 and 1) (slower is generally more accurate)
	 * @param angle
	 * @param power
	 */
	public void turnAngle(int angle, double power) {
		
		if (angle - gyro.getAngle() < 0) { 
			while (angle - gyro.getAngle() < 0) {
				driveTrain.driveBoth(power , 0);
			}
		}
		
		if (angle - gyro.getAngle() > 0) {
			while (angle - gyro.getAngle() > 0) {
				driveTrain.driveBoth(0, power);
			}
		}
		
		driveTrain.driveBoth(0, 0);
	}
	
	/**
	 * Turns the right driveTrain for the specified distance
	 * @param distance
	 * @param power
	 */
	public void rightDistance(double distance, double power) {
		
		double distanceInitial = rightEncoder.getDistance();
		driveTrain.driveBoth(power, power);
		
		if (rightEncoder.getDistance() - distanceInitial >= distance) {
		
			driveTrain.driveBoth(0, 0);
		}
	}
	
	public void leftDistance(double distance, double power) {
		
		double distanceInitial = leftEncoder.getDistance();
		driveTrain.driveBoth(power, power);
		
		if (leftEncoder.getDistance() - distanceInitial >= distance) {
		
			driveTrain.driveBoth(0, 0);
		}
	}
	
	public void driveDistance(double distance, double power) {
		rightDistance(distance, power);
		leftDistance(distance, power);
		
	}
	
	public void driveFull(int angle, double meters, double power) {
		
	}
	
	
	
	
	
}
