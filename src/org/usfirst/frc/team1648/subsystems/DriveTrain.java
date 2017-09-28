package org.usfirst.frc.team1648.subsystems;

import org.usfirst.frc.team1648.robot.Robot;

import edu.wpi.first.wpilibj.VictorSP;

/**
 * Is Strictly the DriveTrain.
 * For Tele-operated driving see Drive
 * For Autonomous driving see AutoDrive
 * 
 * Inputs: 4 VictorSP Port #'s
 * Methods provided:
 *  driveRight(double power);
 *  driveLeft(double power);
 *  driveBoth(double powerLeft, double powerRight);
 * 
 * @author Swag31415
 *
 */

public class DriveTrain {

	private static VictorSP // Declaring some Victors
	leftFrontVictor, leftBackVictor, rightFrontVictor, rightBackVictor;
	
	/**
	 * Constructs a DriveTrain Inputs: 4 VictorSP Port #'s
	 * 
	 * @param leftFrontVictorPort
	 * @param leftBackVictorPort
	 * @param rightFrontVictorPort
	 * @param rightBackVictorPort
	 */
	public DriveTrain(int leftFrontVictorPort, int leftBackVictorPort, int rightFrontVictorPort, int rightBackVictorPort) {
		leftFrontVictor = new VictorSP(leftFrontVictorPort);
		leftBackVictor = new VictorSP(leftBackVictorPort);
		rightFrontVictor = new VictorSP(rightFrontVictorPort);
		rightBackVictor = new VictorSP(rightBackVictorPort);
	}

	/**
	 * Sets the signal sent to the right DriveTrain.
	 * Input a double from -1 to 1.
	 * 1 is forwards.
	 * 
	 * @param power
	 */
	public void driveRight(double power) { //Inversed because right driveTrain 
		rightFrontVictor.set(-power);
		rightBackVictor.set(-power);
	}

	/**
	 * Sets the signal sent to the left DriveTrain.
	 * Input a double from -1 to 1.
	 * 1 is forwards.
	 * 
	 * @param power
	 */
	public void driveLeft(double power) {
		leftFrontVictor.set(power);
		leftBackVictor.set(power);
	}

	/**
	 * Sets the signal sent to the left and right DriveTrain.
	 * Input doubles from -1 to 1.
	 * 1 is forwards.
	 * 
	 * @param powerLeft
	 * @param powerRight
	 */
	public void driveBoth(double powerLeft, double powerRight) {
		driveLeft(powerLeft);
		driveRight(powerRight);
	}
}
