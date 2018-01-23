package org.usfirst.frc.team1714.robot;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.AnalogPotentiometer;

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
	final int potPin = 0;
	final double speedUp = 1;
	final double speedDown = -0.7;
	final int potMax = 1;
	final int potMin = 0;
	
	VictorSP victor1;
	VictorSP victor2;
	
	AnalogPotentiometer pot;
	
	Winch() {
		victor1 = new VictorSP(victor1Pin);
		victor2 = new VictorSP(victor2Pin);
		
		pot = new AnalogPotentiometer(potPin);
		
	}
	
	void setVictors(double velocity) {
		victor1.set(velocity);
		victor2.set(velocity);
	}
	
	public void update(boolean winchUp, boolean winchDown) {
		if(winchUp && pot.get() < potMax) {
			setVictors(speedUp);
			
		}
		else if(winchDown && pot.get() > potMin) {
			setVictors(speedDown);
				
		}
		else {
			setVictors(0);
		}
	}
}
