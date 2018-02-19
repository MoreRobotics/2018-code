package org.usfirst.frc.team1714.robot;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Lift {
	/*
	 * 
	 * 
	 *  THESE ARE PLACEHOLDER VALUES, CHANGE THEM 
	 * 
	 * 
	 */
	
	// Lift Pins
	final int victorsPin = 6;

	final int lsLowPin = 1;
	final int lsHighPin = 0;
	final int potPin = 2;
	
	VictorSP victors;
	
	public DigitalInput lsHigh;
	public DigitalInput lsLow;
	public AnalogPotentiometer pot;
	
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
		victors = new VictorSP(victorsPin);
		
		lsHigh = new DigitalInput(lsHighPin);
		lsLow = new DigitalInput(lsLowPin);
		pot = new AnalogPotentiometer(potPin);
	}
	
	void setVictors(double vel) {
		victors.set(vel);
	}
	
	public void update(double liftVel, boolean liftTargetScale, boolean liftTargetSwitch, boolean liftTargetGround, boolean extended) {
		// debug puts
		SmartDashboard.putNumber("lift pot value: ", pot.get());
		SmartDashboard.putBoolean("LiftHigh: ", lsHigh.get());
		SmartDashboard.putBoolean("LiftLow: ", lsLow.get());
		
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
		
		if(extended) {
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
				if((pot.get() > targetHeight + targetHeightDeadzone) || (pot.get() < targetHeight - targetHeightDeadzone)) {
					double difference = (targetHeight - pot.get());
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
		else {
			setVictors(0);
		}
	}
}
