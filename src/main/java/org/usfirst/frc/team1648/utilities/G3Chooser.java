package org.usfirst.frc.team1648.utilities;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * A simple to use SmartDashboard Chooser
 * 
 * @author Swag31415
 */
public class G3Chooser {
	// Declaring the chooser we'll be using throughout
	private SendableChooser<String> chooser;

	/**
	 * Makes a string based chooser with the choices specified
	 * 
	 * @param defaultChoice The choice that will be automatically ticked, it will be
	 *                      used if nothing is done
	 * @param choices       Any other choices you want
	 */
	public G3Chooser(String defaultChoice, String... choices) {
		// Initializes the chooser
		chooser = new SendableChooser<String>();

		// Adds all the choices
		chooser.addDefault(defaultChoice, defaultChoice);
		for (String choice : choices) {
			chooser.addObject(choice, choice);
		}
	}

	/**
	 * Sends the chooser to the SmartDashboard labeled with the provided label
	 * 
	 * @param chooserLabel The label placed on the chooser in the SmartDashboard
	 */
	public void sendToDashboard(String chooserLabel) {
		SmartDashboard.putData(chooserLabel, chooser);
	}

	/**
	 * Gets what is currently ticked on the Chooser in the SmartDashboard
	 * 
	 * @return Returns the String name of the selected choice on the Dashboard
	 */
	public String getChosen() {
		return chooser.getSelected();
	}

}
