package org.usfirst.frc.team1714.robot;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;


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
	final int extenderPin = 0;
	final int grasperPin = 0;
	
	Victor mLeft;
	Victor mRight;
	Solenoid extender;
	Solenoid grasper;
	
	public DigitalInput lsLeft;
	public DigitalInput lsRight;
	
	Intake() {
		mLeft = new Victor(mLeftPin);
		mRight = new Victor(mRightPin);
		extender = new Solenoid(extenderPin);
		grasper = new Solenoid(grasperPin);
		
		lsLeft = new DigitalInput(lsLeftPin);
		lsRight = new DigitalInput(lsRightPin);
		
	}
	
	void setVictors(double vel) {
		mLeft.set(vel);
		mRight.set(-vel);
	}
	
	public void update(boolean intakeIn, boolean intakeOut, boolean extended, boolean grasping) {
		if(intakeIn) {
			setVictors(speedIn);
		}
		else if(intakeOut) {
			setVictors(speedOut);
		}
		else {
			setVictors(0);
		}
		
		extender.set(extended);
		grasper.set(grasping);
	}
	
}
