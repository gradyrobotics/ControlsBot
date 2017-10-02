package org.usfirst.frc.team1648.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class EncoderSensor {

	private Encoder encoder;
	/**
	 * This is an encoder, give the two ports and grab your encoder!
	 * 
	 * @param portA
	 * @param portB
	 */
	
	private final double PULSES_PER_ROTATION = 120; //put on floor and get a better value
	//private final double WHEEL_DIAMETER = .1016; //inches
	private final double WHEEL_DIAMETER = 4; //inches
	public EncoderSensor(int portA, int portB) {
		
		encoder = new Encoder(portA, portB);
		encoder.reset(); // sets the current position as 0 for the encoder.
		encoder.setDistancePerPulse( (1/PULSES_PER_ROTATION) * WHEEL_DIAMETER * Math.PI); 
		//the conversion factor used for the getDistance function. Turns encoder input into meters.
	}
	
	/**
	 * Gets the count which is the number of clicks from zero
	 * 
	 * @return
	 */
	public int getCount(){
		return encoder.get();
	}
	
	/**
	 * Input what you want the encoder to be called in SmartDasboard and it will output the count, distance and the rate there.
	 * @param key
	 */
	public void putEncoderData(String key) {
		SmartDashboard.putString(key + " Rate: ", encoder.getRate() + "");
		SmartDashboard.putString(key + " Count: ", getCount() + "");
		SmartDashboard.putString(key + " Distance: ", encoder.getDistance() + "");
		
		
	}
	
	public double getDistance() {
		return encoder.getDistance();
	}
	
	
}
