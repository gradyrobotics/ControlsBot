package org.usfirst.frc.team1648.utilities;

public class G3TaskList {

	// An array to hold all the commands this List will do
	private G3Command[] commandList;

	// Used to understand what stage of the command we're currently on
	private int commandPos, subCommandPos;

	// Used to stop the thing once its been done once
	boolean hasRun;

	/**
	 * Makes a Task list that can do the given commands in the given order.
	 * 
	 * @param commands
	 *            the commands you want it to do (has to be of type G3Command)
	 */
	public G3TaskList(G3Command... commands) {
		// Moves the entered commands to the CommandList
		commandList = commands;

		// Initializes out position Integers
		reset();
	}

	/**
	 * Does all the commands in the order they were fed into the constructor
	 * 
	 * @return Returns true when done, false otherwise.
	 */
	public void run() {
		// Ensures the list only gets done once, even when put in a loop
		if (!hasRun) {
			// Cycles through every command, the position of which is marked by CommandPos
			if (commandList.length < commandPos) {

				// Checks to see if you've messed up
				if (subCommandPos > 3) {
					// you've messed up
				}

				// Goes through the individual steps of the commands
				switch (subCommandPos) {
				case (0): // Initialize
					commandList[commandPos].intitialize();
					subCommandPos++;
					break;
				case (1): // Run until done
					if (!commandList[commandPos].isDone()) {
						commandList[commandPos].run();
					} else {
						subCommandPos++;
					}
					break;
				case (2): // Close and move on
					commandList[commandPos].close();
					subCommandPos = 0;
					commandPos++;
					break;
				}
			} else {
				// Resets so its ready to do it all again at any time
				reset();

				// We're done!
				hasRun = true; // Stops it from running itself
			}
			// We're not done, but it'll cycle back!
			hasRun = false;
		}
	}

	/**
	 * Resets the TaskList and effectively moves it back to step 1
	 */
	public void reset() {
		hasRun = false;
		commandPos = 0;
		subCommandPos = 0;
	}

	/**
	 * Gets the current step of the TaskList its on
	 * 
	 * @return Returns the command currently being/set to be executed
	 */
	public int getCommandPos() {
		return commandPos;
	}

	/**
	 * Gets which step of the current command the TaskList is on
	 * 
	 * @return Returns the subCommand currently being run
	 */
	public int getSubCommandPos() {
		return subCommandPos;
	}

	/**
	 * Gets whether the TaskList has been run
	 * 
	 * @return Returns true when the TaskList has been run
	 */
	public boolean getHasRun() {
		return hasRun;
	}
}
