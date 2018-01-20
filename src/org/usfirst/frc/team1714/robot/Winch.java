package org.usfirst.frc.team1714.robot;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Encoder;

public class Winch {
	/*
	 * 
	 * 
	 *  THESE ARE PLACEHOLDER VALUES, CHANGE THEM 
	 * 
	 * 
	 */
	
	final int victor1Pin = 0;
	final int victor2Pin = 1;
	final int encoderPin1 = 0;
	final int encoderPin2 = 0;
	final double maxSpeedUp = 1;
	final double maxSpeedDown = -0.7;
	final int encoderMax = 1;
	final int encoderMin = 0;
	
	VictorSP victor1;
	VictorSP victor2;
	
	Encoder encoder;
	
	Winch() {
		victor1 = new VictorSP(victor1Pin);
		victor2 = new VictorSP(victor2Pin);
		
		encoder = new Encoder(encoderPin1,encoderPin2);
		
	}
	
	void setVictors(double velocity) {
		victor1.set(velocity);
		victor2.set(velocity);
	}
	
	public void update(boolean winchUp, boolean winchDown) {
		if(winchUp && encoder.get() < encoderMax) {
			setVictors(maxSpeedUp);
			
		}
		else if(winchDown && encoder.get() < encoderMin) {
			setVictors(maxSpeedDown);
				
		}
		else {
			setVictors(0);
		}
	}
}
