package org.usfirst.frc.team1648.utilities;

import java.util.HashMap;

public interface G3SubSystem {

	/**
	 * Gets a HashMap with the SubSystem's data
	 * 
	 * @return Returns a String, Double HashMap with data prepared for the
	 *         SmartDashboard
	 */
	public HashMap<String, Double> getData();
}
