package org.usfirst.frc.team1648.subsystems;


import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GyroSensor {

	//private Gyro gyro;
	private ADXRS450_Gyro gyro;
	
	/**
	 * Creates a Gyro Sensor for the Spartan Board.
	 */
	public GyroSensor() {
		this.gyro = new ADXRS450_Gyro();
		gyro.calibrate();
	}
	
	
	/**
	 * Resets the gyro angle and the gyro rate to zero. I recommend you do this whenever you begin autonomous.
	 */
	public void gyroReset() {
		
		gyro.reset();
	}
	
	/**
	 * Puts the Gyro angle and rate in the smart Dashboard.
	 */
	public void putGyroData() {
		
		SmartDashboard.putNumber("Gyro Angle", gyro.getAngle());
		SmartDashboard.putNumber("Gyro Rate", gyro.getRate());
	}
	
	/**
	 * Returns a double between 1 and 360 which is the angle the robot is facing relative to the last time you reset the gyro.
	 * @return
	 */
	public double getAngle(){
		
		return gyro.getAngle();
	}
	
	/**
	 * Returns the rate, I don't really know what that is though.
	 * @return
	 */
	public double getRate(){
		
		return gyro.getRate();
	}
}
