package org.usfirst.frc.team1648.robot.commands;

import org.usfirst.frc.team1648.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1648.utilities.G3Command;

/**
 * A G3Command that drives the driveTrain to the specified distance using closed
 * loop control
 * 
 * @author Swag31415
 */
public class DriveDistance implements G3Command {

	// Declaring the SubSystem we'll be using
	DriveTrain driveTrain;

	// The parameter for the command, the distance to be traveled in inches
	double distance;

	// A boolean that holds the state of this command
	boolean hasRun;

	/**
	 * Makes a DriveDistance command with distance as a parameter and a driveTrain
	 * as the system being modified
	 * 
	 * @param driveTrain A 4 wheel driveTrain with encoders and a gyro
	 * @param distance   The distance to be traveled in inches
	 */
	public DriveDistance(DriveTrain driveTrain, double distance) {
		this.driveTrain = driveTrain;
		this.distance = distance;
		intitialize();
	}

	/**
	 * Gets the current distance set to be traveled
	 * 
	 * @return Returns the set distance in inches
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * Sets the distance to be traveled
	 * 
	 * @param distance The distance in inches to be traveled
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

	@Override
	public void intitialize() {
		// Resetting DT parameters
		driveTrain.resetDistance();
		// driveTrain.resetGyro();
		// Intilializaing the state of the command
		hasRun = false;
	}

	@Override
	public void run() {
		if (!hasRun) {
			driveTrain.setTargetDist(distance, distance);
			if (isDone()) {
				hasRun = true;
			}
		}
	}

	@Override
	public boolean isDone() {
		// If we're close to the target and slow enough we should stop
		if (((Math.abs(driveTrain.getAvgDist() - distance)) < 1) && ((Math.abs(driveTrain.getAvgVelocity())) < 1)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void close() {
		// Stops the DriveTrain
		driveTrain.setPercentOutput(0, 0);
		hasRun = true;
	}
}
