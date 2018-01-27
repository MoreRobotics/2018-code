package org.usfirst.frc.team1714.robot;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;

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
	final int lockPin = 0;
	final int encPin1 = 0;
	final int encPin2 = 1;
	final int encMax = 1;
	final int encMin = 0;
	final double speedUp = 1;
	final double speedDown = -0.7;
	
	VictorSP victor1;
	VictorSP victor2;
	Solenoid lock;
	
	Encoder enc;
	
	Winch() {
		victor1 = new VictorSP(victor1Pin);
		victor2 = new VictorSP(victor2Pin);
		
		enc = new Encoder(encPin1, encPin2);
		lock = new Solenoid(lockPin);
		lock.set(false);
	}
	
	void setVictors(double velocity) {
		victor1.set(velocity);
		victor2.set(velocity);
	}
	
	public void update(boolean winchUp, boolean winchDown) {
		if(winchUp && enc.get() < encMax && !lock.get()) {
			setVictors(speedUp);
			
		}
		else if(winchDown && enc.get() > encMin && !lock.get()) {
			setVictors(speedDown);
				
		}
		else {
			if(enc.get() >= encMax) {
				lock.set(true);
			}
			setVictors(0);
		}
	}
}
