package org.usfirst.frc.team1648.utilities;

/**
 * Really simple interface for commands, Only created because WpiLib's Command
 * class has some "unnecessary" requirements.
 * 
 * @author Swag31415
 */
public interface G3Command {

	/**
	 * This is run once before run() is run. Use it to initialize your command.
	 */
	public void intitialize();

	/**
	 * This is called repeatedly until isDone returns true. This is the main body of
	 * the command.
	 */
	public void run();

	/**
	 * Put a conditional that returns true if the command is done and false
	 * otherwise
	 * 
	 * @return Returns if the command is done or not
	 */
	public boolean isDone();

	/**
	 * This is run once after the command returns true. Use it to close anything
	 * that needs to be closed and reset anything that needs to be reset.
	 */
	public void close();
}
