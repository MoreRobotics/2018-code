package org.usfirst.frc.team1714.robot;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;


public class Lift {
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
	
	Lift(){
		victor1 = new VictorSP(victor1Pin);
		victor2 = new VictorSP(victor2Pin);
		
		lsHigh = new DigitalInput(lsHighPin);
		lsLow = new DigitalInput(lsLowPin);
		encoder = new Encoder(encoderPin1,encoderPin2);
	}
	
	public void update()
}
