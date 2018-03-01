package org.usfirst.frc.team1714.robot;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Winch {
	/*
	 * 
	 * 
	 *  THESE ARE PLACEHOLDER VALUES, CHANGE THEM 
	 * 
	 * 
	 */
	
	final int victorsPin = 8;
	final int lockPin1 = 4;
	final int lockPin2 = 5;
	final int encPin1 = 11;
	final int encPin2 = 12;
	final int encMax = 1;
	final int encMin = 0;
	final double speedUp = 1;
	final double speedDown = -0.7;
	
	VictorSP victors;
	DoubleSolenoid lock;
	
	Encoder enc;
	
	Winch() {
		victors = new VictorSP(victorsPin);
		
		enc = new Encoder(encPin1, encPin2);
		lock = new DoubleSolenoid(lockPin1, lockPin2);
		lock.set(Value.kReverse);
	}
	
	void setVictors(double velocity) {
		victors.set(velocity);
	}
	
	public void update(boolean winchUp, boolean winchDown) {
		//debug puts
		SmartDashboard.putNumber("enc value: ", enc.get());
		
		if(winchUp && enc.get() < encMax && lock.get() != Value.kForward) {
			setVictors(speedUp);
			
		}
		else if(winchDown && enc.get() > encMin && lock.get() != Value.kForward) {
			setVictors(speedDown);
				
		}
		else {
			// if we winch far enough, engage the lock
			if(enc.get() >= encMax) {
				lock.set(Value.kForward);
			}
			setVictors(0);
		}
	}
}
