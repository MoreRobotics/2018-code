package org.usfirst.frc.team1714.robot;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.DigitalInput;


public class Intake {
//	intake pins
	final int mLeftPin = 0;
	final int mRightPin = 1;
	final int lsLeftPin = 0;
	final int lsRightPin = 1;
	
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
	
}
