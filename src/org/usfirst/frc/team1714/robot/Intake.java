package org.usfirst.frc.team1714.robot;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



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
	final double speedIn = 0.5;
	final double speedOut = -1;
	final int extenderPin1 = 2;
	final int extenderPin2 = 3;
	final int grasperPin1 = 0;
	final int grasperPin2 = 1;
	
	final int maxRetractHeight = 0;
	
	Victor mLeft;
	Victor mRight;
	DoubleSolenoid extender;
	DoubleSolenoid grasper;
	
	Intake() {
		mLeft = new Victor(mLeftPin);
		mRight = new Victor(mRightPin);
		extender = new DoubleSolenoid(extenderPin1, extenderPin2);
		grasper = new DoubleSolenoid(grasperPin1, grasperPin2);
	}
	
	void setVictors(double vel) {
		mLeft.set(vel);
		mRight.set(vel);
	}
	
	public void update(double intakeVel, boolean extended, boolean grasping, Lift lift) {
		setVictors(intakeVel);
		
		if(extended ) {
			extender.set(Value.kReverse);
		}
		else if(!extended/* && lift.pot.get() > maxRetractHeight*/) {
			extender.set(Value.kForward);
		}
				
		if(grasping) {
			grasper.set(Value.kForward);
		}
		else if(!grasping) {
			grasper.set(Value.kReverse);
		}
		SmartDashboard.putBoolean("Grasping?", grasping);
	}
	
}
