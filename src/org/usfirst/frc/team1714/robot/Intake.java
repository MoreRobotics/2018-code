package org.usfirst.frc.team1714.robot;
import edu.wpi.first.wpilibj.Victor;
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
	final int mLeftPin = 4;
	final int mRightPin = 5;
	final double speedIn = 1;
	final double speedOut = -1;
	final int extenderPin = 0;
	final int grasperPin = 1;
	
	Victor mLeft;
	Victor mRight;
	Solenoid extender;
	Solenoid grasper;
	
	Intake() {
		mLeft = new Victor(mLeftPin);
		mRight = new Victor(mRightPin);
		extender = new Solenoid(extenderPin);
		grasper = new Solenoid(grasperPin);
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
