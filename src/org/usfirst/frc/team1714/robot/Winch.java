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
	
	final int victorsPin = 7;
	final int lockPin = 2;
	final int encPin1 = 11;
	final int encPin2 = 12;
	final int encMax = 1;
	final int encMin = 0;
	final double speedUp = 1;
	final double speedDown = -0.7;
	
	VictorSP victors;
	Solenoid lock;
	
	Encoder enc;
	
	Winch() {
		victors = new VictorSP(victorsPin);
		
		enc = new Encoder(encPin1, encPin2);
		lock = new Solenoid(lockPin);
		lock.set(false);
	}
	
	void setVictors(double velocity) {
		victors.set(velocity);
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
