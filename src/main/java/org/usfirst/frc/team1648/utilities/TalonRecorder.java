package org.usfirst.frc.team1648.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team1648.robot.Constants;

/**
 * A recorder that utilizes threading to create CSV files that can be played
 * back using the G3Talon's profile reader. The recorder records a motion
 * profile.
 * 
 * @author Swag31415
 */
public class TalonRecorder {

	// Declaring the task and the timer responsible for repeating it at a set
	// interval
	Timer recordingTimer;
	TimerTask recordingTask;

	// Declaring the csv file and the writer we'll use to write to it
	FileWriter writer;
	File proFile;

	// Holds the state of the recorder (0 = initializing, 1 = running, 2 = closing)
	int recorderState;

	// The number of the file we're working in
	int _proFileNumber;

	public TalonRecorder(G3Talon talon, int proFileNumber) {
		// Resetting the recorder
		recorderState = 0;

		// Making a variable to hold the number file we're working in
		_proFileNumber = proFileNumber;
		// if (talon has a sensor) { TODO

		// Making our task and the timer to run it
		recordingTimer = new Timer();
		recordingTask = new TimerTask() {
			// This gets called at regular intervals
			@Override
			public void run() {
				// Needs to go in a try catch because the file "may" not exist (it will though)
				try {
					// Switches between the different states of the recorder
					switch (recorderState) {
					case (0): // Initializing the recorder, gets called once

						// Making the file we're going to write to
						proFile = new File(Constants.profileDir + "MacroNumber" + _proFileNumber + "_TalonId"
								+ talon.getDeviceID() + ".csv");

						// Making the writer we'll be using
						writer = new FileWriter(proFile);

						// The first "trajectory point", labeled as a "zero point"
						writer.append(0 + ",");
						writer.append(0 + ",");
						writer.append(1 + ",");
						writer.append(0 + ",");

						// Moves onto the next line/TrajectoryPoint
						writer.append("\n");

						// Moves onto the running step
						recorderState++;
						break;
					case (1): // Running it, gets called periodically

						// Adding a TrajectoryPoint
						writer.append(talon.getSelectedSensorPosition(0) + ",");
						writer.append(talon.getSelectedSensorVelocity(0) + ",");
						writer.append(0 + ",");
						writer.append(0 + ",");

						// Moves onto the next line/TrajectoryPoint
						writer.append("\n");
						break;
					case (2): // Closing it off, gets called once
						// The last TrajectoryPoint, labeled as the last point as well as a zero point
						writer.append(0 + ",");
						writer.append(0 + ",");
						writer.append(1 + ",");
						writer.append(1 + "");

						// Sends any pending points to the file and closes the writer
						writer.flush();
						writer.close();

						// Resets the recorder
						recorderState = 0;

						// Stops the TimerTask, It won't run again unless called for.
						cancel();
						break;
					}
				} catch (IOException e) {
					e.printStackTrace(); // Crashes the script because there was some error in making the CSV
				}

			}
		};
	}

	/**
	 * Starts recording, and will continue recording at the specified time interval
	 * until stopTask() is called.
	 * 
	 * @param timeIntervalMillis
	 *            Amount of MilliSeconds to wait between every run of the TimerTask
	 */
	public void startTask(long timeIntervalMillis) {
		recordingTimer.scheduleAtFixedRate(recordingTask, 0, timeIntervalMillis);
	}

	/**
	 * Gers the current number file we're working in
	 * 
	 * @return Returns the proFileNumber
	 */
	public int get_proFileNumber() {
		return _proFileNumber;
	}

	/**
	 * Sets the Profile file number
	 * 
	 * @param _proFileNumber
	 *            The current number file we're working in
	 */
	public void set_proFileNumber(int _proFileNumber) {
		this._proFileNumber = _proFileNumber;
	}

	/**
	 * Safely stops the TimerTask
	 */
	public void stopTask() {
		if (recorderState != 0) {
			recorderState = 2;
		}
	}
}
