package org.usfirst.frc.team1648.subsystems;

import edu.wpi.first.wpilibj.Encoder;

public class EncoderSensor {

	private Encoder encoder;
	/**
	 * This is an encoder, give the two ports and grab your encoder!
	 * 
	 * @param portA
	 * @param portB
	 */
	public EncoderSensor(int portA, int portB) {
		
		encoder = new Encoder(portA, portB, false, Encoder.EncodingType.k4X);
		encoder.reset();
		encoder.setMaxPeriod(0.1);
		encoder.setMinRate(10);
		encoder.setDistancePerPulse(5);
		encoder.setSamplesToAverage(7);
		encoder.setDistancePerPulse((6*Math.PI)/128);
	}
	
	/**
	 * Gets the rate which is the speed at which the encoder spins.
	 * 
	 * @return
	 */
	public double getRate(){
		
		return encoder.getRate();
	}
	
	/**
	 * Gets the count which is the number of clicks from zero
	 * 
	 * @return
	 */
	public int getCount(){
		return encoder.getRaw();
	}
	
}
