package org.usfirst.frc.team1714.robot;
import edu.wpi.first.wpilibj.Timer;

public class Autonomous {
	final int d1 = 0; //distance from starting position to "near path"
	final int d2 = 0; //distance from starting position to "far path"
	final int d3 = 0; //distance from d1 to switch scoring position
	final int d4 = 0; //the total "width" of either the near or far path
	final int d5 = 0; //the distance from middle starting position to right starting position
	final int d6 = d4 - d5; //the distance from middle starting position to left starting position
	
	int mainStage = 0;
	
	// this loop handles all routines that begin with the robot in the left position
	void updateLeft(String actionSelected, String pathSelected, double delay) {
		switch(mainStage) {
		case 0:
			if(initialDelay(delay)) {
				mainStage++;
			}
		break;
		case 1:
			
		break;
		}
	}
	
	void updateMiddle(String actionSelected, String pathSelected, double delay) {
		switch(mainStage) {
		case 0:
			if(initialDelay(delay)) {
				mainStage++;
			}
		break;
		case 1:
			
		break;
		}
	}
	
	void updateRight(String actionSelected, String pathSelected, double delay) {
		switch(mainStage) {
		case 0:
			if(initialDelay(delay)) {
				mainStage++;
			}
		break;
		case 1:
			
		break;
		}
	}
	
	void updateCubeSwitch() {
		//lower lift, place cube (at height appropriate for switch)
	}
	
	void updateCubeScale() {
		//lower lift, place cube (at height appropriate for scale)
	}
	
	//this function will start a timer when first called, then return false until the passed amount of time has passed.
	//we can use it at the start of each auto update loop to allow the drivers to set a delay at the start of each match
	boolean running;
	double startTime;
	boolean initialDelay(double delayTime) {
		if(!running) {
			startTime = Timer.getFPGATimestamp();
			running = true;
			return false;
		}
		else {
			if(Timer.getFPGATimestamp() - startTime > delayTime) {
				return true;
			}
			else {
				return false;
			}
		}
	}
}
