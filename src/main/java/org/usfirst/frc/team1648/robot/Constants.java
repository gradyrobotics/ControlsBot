package org.usfirst.frc.team1648.robot;

/**
 * A file to hold all the important constants for the robot. Sort of like a settings panel.
 * 
 * @author Swag31415
 */
public class Constants {

	/**
	 * Directory where we keep or Motion Profiles
	 */
	public final static String
	profileDir = "/home/lvuser/";
	
	/**
	 * Controller Port Numbers
	 */
	public final static int
	XBOX_DRIVER_PORT = 0,
	XBOX_OPERATOR_PORT = 1,
	MONECT_PORT = 0;
	
	/**
	 * DriveTrain MotorController DeviceIDs
	 */
	public final static int
	DT_LEFT_FRONT_MOTOR_CONTROLLER_ID = 11,
	DT_LEFT_BACK_MOTOR_CONTROLLER_ID = 12,
	DT_RIGHT_FRONT_MOTOR_CONTROLLER_ID = 21,
	DT_RIGHT_BACK_MOTOR_CONTROLLER_ID = 22;
	
	/**
	 * Sensor Port Numbers
	 */
	public final static int
	GYRO_PORT = 0;
	
	/**
	 * Constants for DriveTrain Closed-Loop control
	 */
	public final static double
	DT_PPI = 45,
	DT_P_CONST = 3,
	DT_I_CONST = 0,
	DT_D_CONST = 1,
	DT_FEED_FOREWARD_CONST = 0,
	DT_RAMP_TIME = 0.1;
	public final static int
	DT_VEL_LIMIT = 450,
	DT_ACC_LIMIT = 45;
	
	/**
	 * Useful robot dimensions
	 */
	public final static double
	ROBOT_WIDTH_INCHES = 0,
	ROBOT_LENGTH_INCHES = 0,
	ROBOT_HEIGHT_INCHES = 0;

	public static final double 
	DT_TURNING_P_CONST = 0,
	DT_TURNING_D_CONST = 0;
	
}
