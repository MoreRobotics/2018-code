package org.usfirst.frc.team1714.robot;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;


public class Lift {
	/*
	 * 
	 * 
	 *  THESE ARE PLACEHOLDER VALUES, CHANGE THEM 
	 * 
	 * 
	 */
	
	// Lift Pins
	final int victor1Pin = 0;
	final int victor2Pin = 1;
	
	final int lsHighPin = 0;
	final int lsLowPin = 1;
	final int encoderPin1 = 0;
	final int encoderPin2 = 0;
	
	VictorSP victor1;
	VictorSP victor2;
	
	public DigitalInput lsHigh;
	public DigitalInput lsLow;
	public Encoder encoder;
	
	boolean targetMode = false;
	final int targetHeightScale = 0;
	final int targetHeightSwitch = 0;
	final int targetHeightGround = 0;
	final int targetHeightDeadzone = 5;
	final double slowingDistance = 50;
	final double maxSpeedUp = 1;
	final double maxSpeedDown = -0.7;
	int targetHeight = targetHeightGround;
	
	Lift(){
		victor1 = new VictorSP(victor1Pin);
		victor2 = new VictorSP(victor2Pin);
		
		lsHigh = new DigitalInput(lsHighPin);
		lsLow = new DigitalInput(lsLowPin);
		encoder = new Encoder(encoderPin1,encoderPin2);
	}
	
	void setVictors(double vel) {
		victor1.set(vel);
		victor2.set(vel);
	}
	
	public void update(double liftVel, boolean liftTargetScale, boolean liftTargetSwitch, boolean liftTargetGround) {
		if(liftTargetScale) {
			targetMode = true;
			targetHeight = targetHeightScale;
		}
		else if(liftTargetSwitch) {
			targetMode = true;
			targetHeight = targetHeightSwitch;
		}
		else if(liftTargetGround) {
			targetMode = true;
			targetHeight = targetHeightGround;
		}
		
		if(liftVel != 0) {
			targetMode = false;
		}
		
		if(!targetMode) { 
			if((liftVel < 0 && !lsLow.get()) || (liftVel > 0 && !lsHigh.get())) {
				setVictors(0);
			}
			else {
				if(liftVel > maxSpeedUp) {
					setVictors(maxSpeedUp);
				}
				else if(liftVel < maxSpeedDown) {
					setVictors(maxSpeedDown);
				}
				else {
					setVictors(liftVel);
				}
			}
		}
		else {
			if((encoder.get() > targetHeight + targetHeightDeadzone) || (encoder.get() < targetHeight - targetHeightDeadzone)) {
				int difference = (targetHeight - encoder.get());
				double velocity = difference / slowingDistance;
				if(velocity > maxSpeedUp) {
					velocity = maxSpeedUp;
				}
				else if(velocity < maxSpeedDown) {
					velocity = maxSpeedDown;
				}
				setVictors(velocity);
			}
			else {
				setVictors(0);
			}
		}
	}
}
