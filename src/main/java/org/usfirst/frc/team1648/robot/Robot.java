/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1648.robot;

import java.io.FileNotFoundException;
import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team1648.robot.commands.DriveDistance;
import org.usfirst.frc.team1648.robot.commands.RunDTProfile;
import org.usfirst.frc.team1648.robot.commands.TurnToAngle;
import org.usfirst.frc.team1648.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1648.utilities.G3Chooser;
import org.usfirst.frc.team1648.utilities.G3Command;
import org.usfirst.frc.team1648.utilities.G3TaskList;
import org.usfirst.frc.team1648.utilities.MonectController;
import org.usfirst.frc.team1648.utilities.VJoyController;
import org.usfirst.frc.team1648.utilities.XboxController;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation.
 * 
 * @author Swag31415
 */
public class Robot extends IterativeRobot {

	// Declaring Controllers
	XboxController xboxDriver, xboxOperator;
	MonectController monect;
	VJoyController vJoy;

	// Declaring SubSystems
	DriveTrain driveTrain;

	// Declaring Choosers
	G3Chooser autonChooser, teleChooser;

	// Declaring TaskLists
	G3TaskList boxDrive;

	// Declaring Commands
	G3Command runProfile;
	G3Command driveDist;

	// Declaring any seperate threads
	TimerTask barfTask;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		// Initializing Controllers
		xboxDriver = new XboxController(Constants.XBOX_DRIVER_PORT);
		xboxOperator = new XboxController(Constants.XBOX_OPERATOR_PORT);
		monect = new MonectController(Constants.MONECT_PORT);
		vJoy = new VJoyController(Constants.VJOY_PORT);

		// Initializing SubSystems
		driveTrain = new DriveTrain();

		// Initializing Choosers
		autonChooser = new G3Chooser("Default", "PID Tuning Mode", "DriveDist", "Something Else");
		teleChooser = new G3Chooser("Tank Drive", "Tank Drive Monect", "Arcade Drive", "Arcade Drive VJoy",
				"Gyro Drive", "Something Else");

		// Sending Choosers
		autonChooser.sendToDashboard("Autonomous mode Chooser");
		teleChooser.sendToDashboard("Tele-Operated mode Chooser");

		// Starting the separate thread
		Timer barfTimer = new Timer();
		barfTask = new TimerTask() {
			@Override
			public void run() {
				barfData();
			}
		};
		// Starting to barf data every 500ms
		barfTimer.schedule(barfTask, 0, 500);
	}

	/**
	 * This function is run when autonomous started up and should be used for any
	 * initialization code.
	 */
	@Override
	public void autonomousInit() {
		// Making an autonTaskList that drives in a box
		boxDrive = new G3TaskList(new DriveDistance(driveTrain, 10), new TurnToAngle(driveTrain, 90),
				new DriveDistance(driveTrain, 10), new TurnToAngle(driveTrain, 180), new DriveDistance(driveTrain, 10),
				new TurnToAngle(driveTrain, 270), new DriveDistance(driveTrain, 10), new TurnToAngle(driveTrain, 0));
		runProfile = new RunDTProfile(driveTrain, 4);
		driveDist = new DriveDistance(driveTrain, 36);
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		switch (autonChooser.getChosen()) { // Switches between Autonmomous Modes
		case ("Default"):
			boxDrive.run(); // Drives the robot in a box
			break;
		case ("PID Tuning Mode"):
			break;
		case ("DriveDist"):
			driveDist.run();
			break;
		case ("Something Else"):
			runProfile.run();
			break;
		}
	}

	/**
	 * This function is run when Tele-OP started up and should be used for any
	 * initialization code.
	 */
	@Override
	public void teleopInit() {
	}

	/**
	 * This function is called periodically during Tele-OP.
	 */
	@Override
	public void teleopPeriodic() {
		switch (teleChooser.getChosen()) { // Switches between Tele-Operated Modes
		case ("Tank Drive"):
			driveTrain.setPercentOutput(xboxDriver.getLeftYAxis(), xboxDriver.getRightYAxis());
			break;
		case ("Tank Drive Monect"):
			driveTrain.setPercentOutput(monect.getLeftYAxis(), monect.getRightYAxis());
			break;
		case ("Arcade Drive"):
			driveTrain.setPercentOutput(xboxDriver.getLeftYAxis() + xboxDriver.getRightXAxis(),
					xboxDriver.getLeftYAxis() - xboxDriver.getRightXAxis());
			break;
		case ("Arcade Drive VJoy"):
			double turnWeight = 0.2;
			double power = 1;
			driveTrain.setPercentOutput(power * (vJoy.getRightYAxis() + (turnWeight * vJoy.getRightXAxis())),
					power * (vJoy.getRightYAxis() - (turnWeight * vJoy.getRightXAxis())));
			break;
		case ("Gyro Drive"):
			driveTrain.setPercentOutput(monect.getYRotate() + monect.getXRotate(),
					monect.getYRotate() - monect.getXRotate());
			break;
		case ("Something Else"):
			driveTrain.setPercentOutput(.1, .1);
			break;
		}
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		try {
			driveTrain.runProfile(1);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Barfs data to the SmartDashboard
	 */
	public void barfData() {
		SmartDashboard.putNumber("Left Displacement", driveTrain.getLeftDist());
		SmartDashboard.putNumber("Right Displacement", driveTrain.getRightDist());
		SmartDashboard.putNumber("Left Velocity", driveTrain.getLeftVelocity());
		SmartDashboard.putNumber("Right Velocity", driveTrain.getRightVelocity());
		SmartDashboard.putNumber("Angle", driveTrain.getAngularVelocity());
		SmartDashboard.putNumber("Angular Velocity", driveTrain.getAngle());
	}
}