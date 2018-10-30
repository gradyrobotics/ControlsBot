package org.usfirst.frc.team1648.robot.commands;

import org.usfirst.frc.team1648.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1648.utilities.G3Command;

/**
 * A G3Command that turns the driveTrain to the specified angle
 * 
 * @author Swag31415
 */
public class TurnToAngle implements G3Command {

	// Declaring the SubSystem we'll be using
	DriveTrain driveTrain;

	// The parameter for the command, the angle to be sought in degrees
	double angle;

	/**
	 * Makes a TurnAngle command with angle as a parameter and the DriveTrain as the
	 * system being modified
	 * 
	 * @param driveTrain
	 *            A 4 wheel driveTrain with encoders and a gyro
	 */
	public TurnToAngle(DriveTrain driveTrain, double angle) {
		this.driveTrain = driveTrain;
		this.angle = angle;
	}

	/**
	 * Gets the angle set to be traveled to
	 * 
	 * @return Returns the angle parameter
	 */
	public double getAngle() {
		return angle;
	}

	/**
	 * Sets the angle to be traveled to
	 * 
	 * @param angle
	 *            The angle you want to turn to
	 */
	public void setAngle(double angle) {
		this.angle = angle;
	}

	@Override
	public void intitialize() {
		// Resetting DT parameters
		driveTrain.resetDistance();
		// driveTrain.resetGyro();

	}

	@Override
	public void run() {
		// Turns the robot towards the provided angle
		driveTrain.turnTowardsAngle(angle);

	}

	@Override
	public boolean isDone() {
		// If its close to the target angle and its moving pretty slow, stop turning
		if (((Math.abs(driveTrain.getAngle() - angle)) < 3) && ((Math.abs(driveTrain.getAngularVelocity())) < 2)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void close() {
		// Stops the driveTrain
		driveTrain.setPercentOutput(0, 0);
	}

}
