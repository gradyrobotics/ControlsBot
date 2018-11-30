package org.usfirst.frc.team1648.utilities;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Creates a MonectController with predefined methods for finding the
 * state/value of Monect controller buttons/axes
 * 
 * @author Swag31415
 */
public class MonectController extends Joystick {
	// Monect Controller Button mapping
	public final static int 
			A_Button = 3,
			B_Button = 2,
			X_Button = 4,
			Y_Button = 1,
			Left_Bumper = 5,
			Right_Bumper = 6,

			// Monect Axes mapping::
			Axis_LeftX = 0,
			Axis_LeftY = 1,
			Axis_RightX = 3,
            Axis_RightY = 4,
            Axis_YRotate = 2,
            Axis_XRotate = 5;

	// Deadband on Joysticks & Triggers
	private final static double stickDeadband = 0.2;

	/**
	 * Constructs a new MonectController with a specified port
	 *
	 * @param port
	 */
	public MonectController(final int port) {
		super(port);
	}

	// Control Sticks::
	/**
	 * returns the current state of the A Button
	 */
	public boolean getAButton() {
		return getRawButton(A_Button);
	}

	/**
	 * returns the current state of the B Button
	 */
	public boolean getBButton() {
		return getRawButton(B_Button);
	}

	/**
	 * returns the current state of the X_Button
	 */
	public boolean getXButton() {
		return getRawButton(X_Button);
	}

	/**
	 * returns the current state of the Y_Button
	 */
	public boolean getYButton() {
		return getRawButton(Y_Button);
	}

	/**
	 * returns the current state of the Left_Bumper
	 */
	public boolean getLB() {
		return getRawButton(Left_Bumper);
	}

	/**
	 * returns the current state of the Right_Bumper
	 */
	public boolean getRB() {
		return getRawButton(Right_Bumper);
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

	/**
	 * returns the current value of the Axis_YRotate
	 */
	public double getYRotate() {
		if (Math.abs(getRawAxis(Axis_YRotate)) > stickDeadband) {
			return getRawAxis(Axis_YRotate);
		} else {
			return 0;
		}
	}

	/**
	 * returns the current value of the Axis_XRotate
	 */
	public double getXRotate() {
		if (Math.abs(getRawAxis(Axis_XRotate)) > stickDeadband) {
			return getRawAxis(Axis_XRotate);
		} else {
			return 0;
		}
	}
}