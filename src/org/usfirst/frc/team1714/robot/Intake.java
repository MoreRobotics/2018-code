package org.usfirst.frc.team1714.robot;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.DigitalInput;


public class Intake {
	/*
	 * 
	 * 
	 *  THESE ARE PLACEHOLDER VALUES, CHANGE THEM 
	 * 
	 * 
	 */
	
//	intake pins
	final int mLeftPin = 0;
	final int mRightPin = 1;
	final int lsLeftPin = 0;
	final int lsRightPin = 1;
	final double speedIn = 1;
	final double speedOut = -1;
	
	Victor mLeft;
	Victor mRight;
	
	public DigitalInput lsLeft;
	public DigitalInput lsRight;
	
	Intake() {
		mLeft = new Victor(mLeftPin);
		mRight = new Victor(mRightPin);
		
		lsLeft = new DigitalInput(lsLeftPin);
		lsRight = new DigitalInput(lsRightPin);
		
	}
	
	void setVictors(double vel) {
		mLeft.set(vel);
		mRight.set(-vel);
	}
	
	public void update(boolean intakeIn, boolean intakeOut) {
		if(intakeIn) {
			setVictors(speedIn);
		}
		else if(intakeOut) {
			setVictors(speedOut);
		}
		else {
			setVictors(0);
		}
	}
	
}
