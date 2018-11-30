package org.usfirst.frc.team1648.utilities;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Creates an XboxController with predefined methods for finding the state/value
 * of Xbox controller buttons/axes
 * 
 * @author PratikKunapuli
 */
public class XboxController extends Joystick {
	// Xbox Controller Button mapping
	public final static int 
			A_Button = 1,
			B_Button = 2,
			X_Button = 3,
			Y_Button = 4,
			Left_Bumper = 5,
			Right_Bumper = 6,

			Back_Button = 7,
			Start_Button = 8,
			Left_Stick = 9,
			Right_Stick = 10,

			// Xbox Axes mapping::
			Axis_LeftX = 0,
			Axis_LeftY = 1,
			Axis_LeftTrigger = 2,
			Axis_RightTrigger = 3,
			Axis_RightX = 4,
			Axis_RightY = 5,
			// Not reccomended: buggy and unreliable
			Axis_DPad = 6;
			
	// Deadband on Joysticks & Triggers
	private final static double stickDeadband = 0.12;

	/**
	 * Constructs a new XboxController with a specified port
	 *
	 * @param port
	 */
	public XboxController(final int port) {
		super(port);
	}

	public boolean getStartButton() {
		return getRawButton(Start_Button);
	}

	public boolean getBackButton() {
		return getRawButton(Back_Button);
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
	 * returns true if the LeftTrigger is sufficiently pressed
	 */
	public boolean getLeftTrigger() {
		return (Math.abs(getRawAxis(Axis_LeftTrigger)) > stickDeadband);
	}

	/**
	 * returns true if the RightTrigger is sufficiently pressed
	 */
	public boolean getRightTrigger() {
		return (Math.abs(getRawAxis(Axis_RightTrigger)) > stickDeadband);
	}

	/**
	 * returns the current state of the Left_Stick
	 */
	public boolean getLeftStickClick() {
		return getRawButton(Left_Stick);
	}

	/**
	 * returns the current state of the Right_Stick
	 */
	public boolean getRightStickClick() {
		return getRawButton(Right_Stick);
	}

	/**
	 * returns the current value of the Axis_LeftX
	 */
	public double getLeftXAxis() {
		if (Math.abs(getRawAxis(Axis_LeftX)) > stickDeadband) {
			return getRawAxis(Axis_LeftX);
		} else {
			return 0;
		}
	}

	/**
	 * returns the current value of the Axis_LeftY
	 */
	public double getLeftYAxis() {
		if (Math.abs(getRawAxis(Axis_LeftY)) > stickDeadband) {
			return -getRawAxis(Axis_LeftY);
		} else {
			return 0;
		}
	}

	/**
	 * returns the current value of the Axis_RightX
	 */
	public double getRightXAxis() {
		if (Math.abs(getRawAxis(Axis_RightX)) > stickDeadband) {
			return getRawAxis(Axis_RightX);
		} else {
			return 0;
		}
	}

	/**
	 * returns the current value of the Axis_RightY
	 */
	public double getRightYAxis() {
		if (Math.abs(getRawAxis(Axis_RightY)) > stickDeadband) {
			return -getRawAxis(Axis_RightY);
		} else {
			return 0;
		}
	}

	/**
	 * returns the current value of the Axis_DPad (NOTE:: Very buggy, not
	 * recommended)
	 */
	public double getDPadAxis() {
		return getRawAxis(Axis_LeftX);
	}
}