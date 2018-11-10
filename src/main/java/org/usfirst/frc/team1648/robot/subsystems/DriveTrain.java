package org.usfirst.frc.team1648.robot.subsystems;

import java.io.FileNotFoundException;

import org.usfirst.frc.team1648.robot.Constants;
import org.usfirst.frc.team1648.utilities.G3Talon;
import org.usfirst.frc.team1648.utilities.TalonRecorder;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.AnalogGyro;

/**
 * A Typical 4 wheel DriveTrain featuring Encoders, MotionProfiling,
 * ClosedLoopControl, and a Gyro among other things
 * 
 * @author Swag31415
 *
 */
public class DriveTrain {

	// Declaring MotorControllers
	G3Talon DTLeftFrontTalon, DTRightFrontTalon;
	VictorSPX DTLeftBackVictor, DTRightBackVictor;

	// Declaring Recorders for the Talons
	TalonRecorder DTLeftTalonRecorder, DTRightTalonRecorder;

	// Declaring Sensors
	AnalogGyro gyro;

	/**
	 * Creates a 4 wheel DriveTrain with a gyro and encoders on both sides.
	 * Initializes everything in starting position.
	 */
	public DriveTrain() {
		// Initializing MotorControllers
		DTLeftFrontTalon = new G3Talon(Constants.DT_LEFT_FRONT_MOTOR_CONTROLLER_ID);
		DTRightFrontTalon = new G3Talon(Constants.DT_RIGHT_FRONT_MOTOR_CONTROLLER_ID);
		DTLeftBackVictor = new VictorSPX(Constants.DT_LEFT_BACK_MOTOR_CONTROLLER_ID);
		DTRightBackVictor = new VictorSPX(Constants.DT_RIGHT_BACK_MOTOR_CONTROLLER_ID);

		// Setting Victors to Follow the Talon on their side
		DTLeftBackVictor.follow(DTLeftFrontTalon);
		DTRightBackVictor.follow(DTRightFrontTalon);

		// Reversing motors on the appropriate side
		DTLeftFrontTalon.setInverted(false);
		DTRightFrontTalon.setInverted(true);
		DTLeftBackVictor.setInverted(false);
		DTRightBackVictor.setInverted(true);

		// Initializing sensors
		DTLeftFrontTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		DTRightFrontTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		resetDistance();
		gyro = new AnalogGyro(Constants.GYRO_PORT);
		resetGyro();

		// Reversing the appropriate sensors
		DTLeftFrontTalon.setSensorPhase(false);
		DTRightFrontTalon.setSensorPhase(false);

		// Setting PID values
		setPID(Constants.DT_P_CONST, Constants.DT_I_CONST, Constants.DT_D_CONST, Constants.DT_FEED_FOREWARD_CONST,
				Constants.DT_RAMP_TIME, Constants.DT_VEL_LIMIT, Constants.DT_ACC_LIMIT);

		// Making Recorders for the Talons
		DTLeftTalonRecorder = new TalonRecorder(DTLeftFrontTalon, 0);
		DTRightTalonRecorder = new TalonRecorder(DTRightFrontTalon, 0);
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
		DTLeftFrontTalon.setPID(kP, kI, kD, kF, rampTime, maxVel, maxAcc);
		DTRightFrontTalon.setPID(kP, kI, kD, kF, rampTime, maxVel, maxAcc);
	}

	/**
	 * Sets the percent power output of the two sides of the driveTrain
	 * 
	 * @param leftPercentOutput  The left side's percent output, expressed as a
	 *                           double between -1 and 1
	 * @param rightPercentOutput The right side's percent output, expressed as a
	 *                           double between -1 and 1
	 */
	public void setPercentOutput(double leftPercentOutput, double rightPercentOutput) {
		DTLeftFrontTalon.set(ControlMode.PercentOutput, leftPercentOutput);
		DTRightFrontTalon.set(ControlMode.PercentOutput, rightPercentOutput);
	}

	/**
	 * Sets the target position for both sides of the DriveTrain. Doesn't actually
	 * drive that distance, just sets the talons' target. Uses MotionMagic
	 * 
	 * @param leftDist  The target dist for the left side of the DriveTrain,
	 *                  expressed in inches
	 * @param rightDist The target dist for the right side of the DriveTrain,
	 *                  expressed in inches
	 */
	public void setTargetDist(double leftDist, double rightDist) {
		DTLeftFrontTalon.set(ControlMode.MotionMagic, leftDist * Constants.DT_PPI);
		DTRightFrontTalon.set(ControlMode.MotionMagic, rightDist * Constants.DT_PPI);
	}

	/**
	 * Turns the robot towards the specified angle
	 * 
	 * @param angle The angle in degrees the driveTrain should turn towards
	 */
	public void turnTowardsAngle(double angle) {
		// Calculating variables necessary for PD calulation
		double angularErr = angle - getLimitedAngle();
		double angularErrRate = -gyro.getRate();

		// Doing PD calculation to find output
		double output = (((angularErr) * Constants.DT_TURNING_P_CONST)
				+ (angularErrRate * Constants.DT_TURNING_D_CONST));

		// Setting the motors to turn
		setPercentOutput(output, -output);
	}

	/**
	 * Moves the robot to the specfied distance while preserving angle of rotation
	 * 
	 * @param dist The distance to be traveled in inches
	 */
	public void setTargetTrajectory(double dist) {
		// Determining Error in distance
		double leftError = dist - getLeftDist();
		double rightError = dist - getRightDist();

		// Determining Error in velocity
		double leftErrorRate = -getLeftVelocity();
		double rightErrorRate = -getRightVelocity();

		// Determining Error in angle and angular velocity
		double angularError = -getLimitedAngle();
		double angularErrorRate = -gyro.getRate();

		// Determining output using PD control
		double leftOutput = (Constants.DT_P_CONST * leftError) + (Constants.DT_D_CONST * leftErrorRate)
				+ Constants.DT_ANGULAR_WEIGHT * (((angularError) * Constants.DT_TURNING_P_CONST)
						+ (angularErrorRate * Constants.DT_TURNING_D_CONST));
		double rightOutput = (Constants.DT_P_CONST * rightError) + (Constants.DT_D_CONST * rightErrorRate)
				- Constants.DT_ANGULAR_WEIGHT * (((angularError) * Constants.DT_TURNING_P_CONST)
						+ (angularErrorRate * Constants.DT_TURNING_D_CONST));

		// Setting power (percent) output to motors
		setPercentOutput(leftOutput, rightOutput);
	}

	/**
	 * Starts recording the motion of the driveTrain into a csv file as a
	 * motionProfile
	 * 
	 * @param macroNumber The Number of the CSV file it'll read from
	 */
	public void startRecordingMovement(int macroNumber) {
		// Sets the file we're gonna be writing to
		DTLeftTalonRecorder.set_proFileNumber(macroNumber);
		DTRightTalonRecorder.set_proFileNumber(macroNumber);

		// Starts the task at 20ms intervals
		DTLeftTalonRecorder.startTask(20);
		DTRightTalonRecorder.startTask(20);
	}

	/**
	 * Stops Recording the movement of the talon
	 */
	public void stopRecordingMovement() {
		DTLeftTalonRecorder.stopTask();
		DTRightTalonRecorder.stopTask();
	}

	/**
	 * Reads and runs a motion profile from the provided numbered csv file
	 * 
	 * @param macroNumber the number of the csv file
	 * @throws FileNotFoundException if the file doesn't exist, it crashes
	 */
	public boolean runProfile(int macroNumber) throws FileNotFoundException {
		if (DTLeftFrontTalon.runProfile(macroNumber) & DTRightFrontTalon.runProfile(macroNumber)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Gets the number of inches the left side of the driveTrain has traveled
	 * 
	 * @return Returns a double, the distance traveled by the left driveTrain
	 */
	public double getLeftDist() {
		return DTLeftFrontTalon.getSelectedSensorPosition(0) / Constants.DT_PPI;
	}

	/**
	 * Gets the number of inches the right side of the driveTrain has traveled
	 * 
	 * @return Returns a double, the distance traveled by the right driveTrain
	 */
	public double getRightDist() {
		return DTRightFrontTalon.getSelectedSensorPosition(0) / Constants.DT_PPI;
	}

	/**
	 * Gets the average number of inches traveled by the driveTrain
	 * 
	 * @return Returns a double gotten by averaging left and right distances
	 */
	public double getAvgDist() {
		return (getRightDist() + getLeftDist()) / 2;
	}

	/**
	 * Gets the average current velocity in inches/sec of the left side of
	 * driveTrain
	 * 
	 * @return Returns a double, the velocity of the left driveTrain
	 */
	public double getLeftVelocity() {
		return DTLeftFrontTalon.getSelectedSensorVelocity(0) / Constants.DT_PPI;
	}

	/**
	 * Gets the average current velocity in inches/sec of the right side of
	 * driveTrain
	 * 
	 * @return Returns a double, the velocity of the right driveTrain
	 */
	public double getRightVelocity() {
		return DTRightFrontTalon.getSelectedSensorVelocity(0) / Constants.DT_PPI;
	}

	/**
	 * Gets the average current velocity in inches/sec of the driveTrain
	 * 
	 * @return Returns a double gotten by averaging left and right velocities
	 */
	public double getAvgVelocity() {
		return (getRightVelocity() + getLeftVelocity()) / 2;
	}

	/**
	 * Gets the angle the driveTrain is currently facing
	 * 
	 * @return Returns the angle of the gyro expressed as a continuous double
	 */
	public double getAngle() {
		return gyro.getAngle();
	}

	/**
	 * Gets the angle the driveTrain is facing, with wrap around enabled; 361 turns
	 * to 1 and -361 turns to -1
	 * 
	 * @return Returns the angle of the gyro modulus 360
	 */
	public double getLimitedAngle() {
		return getAngle() % 360;
	}

	/**
	 * Gets how fast the driveTrain is turning
	 * 
	 * @return Returns the rate at which the gyro is spinning in degrees/sec
	 */
	public double getAngularVelocity() {
		return gyro.getRate();
	}

	/**
	 * Sets the current position of the encoders as zero
	 */
	public void resetDistance() {
		DTLeftFrontTalon.setSelectedSensorPosition(0, 0, 0);
		DTRightFrontTalon.setSelectedSensorPosition(0, 0, 0);
	}

	public void resetGyro() {
		gyro.reset();
	}
}
