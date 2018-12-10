package org.usfirst.frc.team1648.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

import org.usfirst.frc.team1648.robot.Constants;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motion.TrajectoryPoint.TrajectoryDuration;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Notifier;

/**
 * A modified TalonSRX class that makes motionProfiling way simpler
 * 
 * @author Swag31415
 */
public class G3Talon extends TalonSRX {

	// Acts as a really small buffer to ensure the Trajectory points make it to the
	// talon
	private int loopTimeout;

	// Useful variables to track and set the status of the motion profile
	private int profileState;
	private MotionProfileStatus status;

	/**
	 * 
	 * @return Returns the current status of the motion profile as a
	 *         MotionProfileStatus (enum)
	 */
	public MotionProfileStatus getStatus() {
		return status;
	}

	/**
	 * A periodic task to funnel trajectory points into the talon
	 */
	class PeriodicRunnable implements java.lang.Runnable {
		public void run() {
			processMotionProfileBuffer();
		}
	}

	/**
	 * Creates a Talon with the typical TalonSRX methods along with a few new ones
	 * 
	 * @param deviceNumber The talon's DeviceID on the CAN Bus
	 * @param isInverted   Whether to invert the talon or not
	 */
	public G3Talon(int deviceNumber, boolean isInverted) {
		// Creates a typical TalonSRX
		super(deviceNumber);

		// Sets Polarity
		setInverted(isInverted);
		// Making a notifier to start the task when needed
		Notifier notifier = new Notifier(new PeriodicRunnable());

		// Starts our Periodic task and makes it run every 5ms
		notifier.startPeriodic(0.005);

		status = new MotionProfileStatus();
		loopTimeout = -1;
	}

	/**
	 * Creates a Talon with the typical TalonSRX methods along with a few new ones
	 * 
	 * @param deviceNumber     The talon's DeviceID on the CAN Bus
	 * @param isInverted       Whether to invert the talon or not
	 * @param sensor           The type of feedback device plugged into the talon
	 * @param isSensorInverted Whether to invert the sensor or not
	 */
	public G3Talon(int deviceNumber, boolean isInverted, FeedbackDevice sensor, boolean isSensorInverted) {
		// Creates a typical TalonSRX
		super(deviceNumber);

		// Sets Talon Polarity
		setInverted(isInverted);

		// Sets up a sensor
		configSelectedFeedbackSensor(sensor, 0, 0);
		setSensorPhase(isSensorInverted);
		setSelectedSensorPosition(0, 0, 0);

		// Making a notifier to start the task when needed
		Notifier notifier = new Notifier(new PeriodicRunnable());

		// Starts our Periodic task and makes it run every 5ms
		notifier.startPeriodic(0.005);

		status = new MotionProfileStatus();
		loopTimeout = -1;
	}

	/**
	 * Reads a CSV file for TrajectoryPoints and sends them to the talon
	 * 
	 * @param macroNumber The "id" of the file you're reading from
	 * @throws FileNotFoundException If the file you're trying to read from doesn't
	 *                               exist, it crashes
	 */
	public void fillPoints(int macroNumber) throws FileNotFoundException {
		// Creates the file it'll read from
		File proFile = new File(
				Constants.profileDir + "MacroNumber" + macroNumber + "_TalonId" + getDeviceID() + ".csv");
		// Creates the reader and sets it up to read from the file we made earlier
		Scanner scanner = new Scanner(proFile);
		scanner.useLocale(Locale.US);
		scanner.useDelimiter(", |\\n");

		// Plays from the autoFile if possible. the thread gets trapped here for
		// about 15ms
		while (scanner.hasNextDouble() && scanner.hasNextLine()) {

			// Makes a new TrajectoryPoint for the talon to read
			TrajectoryPoint point = new TrajectoryPoint();

			// Selects the PID profile we'll be using (when in doubt, 0)
			point.profileSlotSelect0 = 0;

			// The parameters for the point
			point.position = scanner.nextDouble() * Constants.DT_PPI;
			point.velocity = scanner.nextDouble() * Constants.DT_PPI;

			// How long we have to get to the point
			point.timeDur = TrajectoryDuration.valueOf((int) scanner.nextDouble());

			// If the point is stationary/the last point
			point.zeroPos = point.position == 1;
			point.isLastPoint = !scanner.hasNextDouble();

			// Pushes the TrajectoryPoint onto the CAN Bus towards the Talon
			pushMotionProfileTrajectory(point);
		}

		// Saves and nullifies the reader
		scanner.close();
		scanner = null;
	}

	/**
	 * Runs a MotionProfile from a specified file
	 * 
	 * @param macroNumber The "id" of the file you're reading from
	 * @return Returns True when done, false otherwise
	 * @throws FileNotFoundException If the file does not exist, it crashes
	 */
	public boolean runProfile(int macroNumber) throws FileNotFoundException {

		// Checks the status of the Talon every Loop
		getMotionProfileStatus(status);

		// A simple timer used throughout to ensure the talon is not broken
		if (loopTimeout < 0) {
			// Timer's disabled
		} else if (loopTimeout == 0) {
			System.out.println("Talon " + getDeviceID() + " is broken");
		} else {
			--loopTimeout;
		}

		// Handles the state of the talon and tells it what to do.
		switch (profileState) {
		case (0): // This is run once, it gets the talon ready to read a MotionProfile

			// Disables the talon in MotionProfile mode so it can take in points and not act
			// on them
			set(ControlMode.MotionProfile, SetValueMotionProfile.Disable.value);

			// Puts all the TrajetoryPoints on the CAN bus towards the talon
			fillPoints(macroNumber);

			// Moves on
			profileState++;

			// Waits 10 Loops for some points to make it to the talon
			loopTimeout = 10;
			break;
		case (1): // This is run once, it only exists to ensure that Trajetory points are making
					// it to the talon safely

			// Checks if at least 10 TrajectoryPoints have made it to the talon
			if (status.btmBufferCnt > 10) {
				// Enables the Profile, the talon should start moving
				set(ControlMode.MotionProfile, SetValueMotionProfile.Enable.value);
				// Moves on
				profileState++;
				loopTimeout = 10;
			}
			break;
		case (2):
			/*
			 * if talon is reporting things are good, keep adding to our timeout. Really
			 * this is so that you can unplug your talon in the middle of an MP and react to
			 * it.
			 */
			if (status.isUnderrun == false) {
				loopTimeout = 10;
			}
			/*
			 * If we are executing an MP and the MP finished, start loading another. We will
			 * go into hold state so robot servo's position.
			 */
			if (status.activePointValid && status.isLast) {
				/*
				 * because we set the last point's isLast to true, we will get here when the MP
				 * is done
				 */
				// set(ControlMode.MotionProfile, SetValueMotionProfile.Hold.value);
				set(ControlMode.PercentOutput, 0);

				// Resetting things and getting ready for another profile potentially
				profileState = 0;
				loopTimeout = -1;
				return true;
			}
			break;
		}
		return false;
	}

	/**
	 * Sets the typically used ClosedLoop constants all in one method
	 * 
	 * @param kP       Proportional constant
	 * @param kI       Integral Constant
	 * @param kD       Derivative Constant
	 * @param kF       Feed-Forward Constant
	 * @param rampTime Time taken to ramp up to full speed in seconds
	 * @param maxVel   Maximum Cruise Velocity (used only by MotionProfiles)
	 * @param maxAcc   Maximum Acceleration (used only by MotionProfiles)
	 */
	public void setPID(double kP, double kI, double kD, double kF, double rampTime, int maxVel, int maxAcc) {
		config_kP(0, kP, 0);
		config_kI(0, kI, 0);
		config_kD(0, kD, 0);
		config_kF(0, kF, 0);
		configClosedloopRamp(rampTime, 0);
		configMotionCruiseVelocity(maxVel, 0);
		configMotionAcceleration(maxAcc, 0);
	}
}
