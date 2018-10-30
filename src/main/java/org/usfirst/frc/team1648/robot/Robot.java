/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1648.robot;

import java.io.FileNotFoundException;

import org.usfirst.frc.team1648.robot.commands.DriveDistance;
import org.usfirst.frc.team1648.robot.commands.TurnToAngle;
import org.usfirst.frc.team1648.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1648.utilities.G3Chooser;
import org.usfirst.frc.team1648.utilities.G3TaskList;
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

	// Declaring SubSystems
	DriveTrain driveTrain;

	// Declaring Choosers
	G3Chooser autonChooser, teleChooser;

	// Declaring TaskLists
	G3TaskList boxDrive;
	
	// A counter to handle steps in autonomous modes
	int autonStep;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		// Initializing Controllers
		xboxDriver = new XboxController(Constants.XBOX_DRIVER_PORT);
		xboxOperator = new XboxController(Constants.XBOX_OPERATOR_PORT);

		// Initializing SubSystems
		driveTrain = new DriveTrain();

		// Initializing Choosers
		autonChooser = new G3Chooser("Default", "PID Tuning Mode", "Drive 3ft", "Something Else");
		teleChooser = new G3Chooser("Tank Drive", "Arcade Drive", "Something Else");

		// Sending Choosers
		autonChooser.sendToDashboard("Autonomous mode Chooser");	
		teleChooser.sendToDashboard("Tele-Operated mode Chooser");
	}

	/**
	 * This function is run when autonomous started up and should be used for any
	 * initialization code.
	 */
	@Override
	public void autonomousInit() {
		
		// Moving to step 0
		autonStep = 0;
		
		// Making an autonTaskList that drives in a box
		boxDrive = new G3TaskList(new DriveDistance(driveTrain, 10),
				new TurnToAngle(driveTrain, 90),
				new DriveDistance(driveTrain, 10),
				new TurnToAngle(driveTrain, 180),
				new DriveDistance(driveTrain, 10),
				new TurnToAngle(driveTrain, 270),
				new DriveDistance(driveTrain, 10),
				new TurnToAngle(driveTrain, 0));
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
		case ("Drive 3ft"):
			DriveDistance driveDistance = new DriveDistance(driveTrain, 12);
			if (!driveDistance.isDone()) {
					driveDistance.run();
				} else {
					driveDistance.close();
				}
			break;
		case ("Something Else"):
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
		case ("Arcade Drive"):
			driveTrain.setPercentOutput(xboxDriver.getLeftYAxis() + xboxDriver.getRightXAxis(),
					xboxDriver.getLeftYAxis() - xboxDriver.getRightXAxis());
			break;
		case ("Something Else"):
			break;
		}
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		System.out.println(driveTrain.getLeftDist());
		try {
			driveTrain.runProfile(0);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}