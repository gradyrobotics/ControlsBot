package org.usfirst.frc.team1648.utilities;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Creates a VJoy with predefined methods for finding the state/value
 * of VJoy controller buttons/axes
 * 
 * @author Swag31415
 */
public class VJoyController extends Joystick {
	// VJoy Controller Button mapping
	public final static int 
		Button_1 = 1,
		Button_2 = 2,
		Button_3 = 3,
		Button_4 = 4,

		// VJoy Axes mapping::
		Axis_LeftX = 2,
		Axis_LeftY = 3,
		Axis_RightX = 0,
        Axis_RightY = 1;

	/**
	 * Constructs a new VJoyController with a specified port
	 *
	 * @param port
	 */
	public VJoyController(final int port) {
		super(port);
	}

	// Control Sticks::
	/**
	 * returns the current state of the 1 Button
	 */
	public boolean getAButton() {
		return getRawButton(Button_1);
	}

	/**
	 * returns the current state of the 2 Button
	 */
	public boolean getBButton() {
		return getRawButton(Button_2);
	}

	/**
	 * returns the current state of the 3_Button
	 */
	public boolean getXButton() {
		return getRawButton(Button_3);
	}

	/**
	 * returns the current state of the 4_Button
	 */
	public boolean getYButton() {
		return getRawButton(Button_4);
	}

	/**
	 * returns the current value of the Axis_LeftX
	 */
	public double getLeftXAxis() {
		return getRawAxis(Axis_LeftX);
	}

	/**
	 * returns the current value of the Axis_LeftY
	 */
	public double getLeftYAxis() {
		return getRawAxis(Axis_LeftY);
	}

	/**
	 * returns the current value of the Axis_RightX
	 */
	public double getRightXAxis() {
		return getRawAxis(Axis_RightX);
	}

	/**
	 * returns the current value of the Axis_RightY
	 */
	public double getRightYAxis() {
		return getRawAxis(Axis_RightY);
    }
}