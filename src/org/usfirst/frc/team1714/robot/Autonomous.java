package org.usfirst.frc.team1714.robot;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

public class Autonomous {
	final int d1 = 0; //distance from starting position to "near path"
	final int d2 = 0; //distance from starting position to "far path"
	final int d3 = 0; //distance from d1 to switch scoring position
	final int d4 = 0; //the total "width" of either the near or far path
	final int d5 = 0; //the distance from middle starting position to right starting position
	final int d6 = d4 - d5; //the distance from middle starting position to left starting position
	
	int mainStage = 0;
	
	char switchLocation = 0;
	char scaleLocation = 0;
	
	// this loop handles all routines that begin with the robot in the left position
	void updateLeft(String actionSelected, String pathSelected, double delay) {
		
		switch(mainStage) {
		case 0:
			if(initialDelay(delay)) {
				mainStage++;
			}
		break;
		case 1:
			if(actionSelected == "line") {
				// TODO: drive until D3
				mainStage = 5;
			}
			else if(actionSelected == "switch")
			{
				if(switchLocation == 'L') {
					// TODO: drive until D3
					mainStage = 4;
				}
				else if(switchLocation == 'R') {
					if(pathSelected == "near") {
						// TODO: drive until D1
						mainStage++;
					}
					else if(pathSelected == "far") {
						// TODO: drive until D2
						mainStage++;
					}
				}
			}
			else if(actionSelected == "scale") {
				//TODO
			}
		break;
		case 2:
			if(actionSelected == "switch" && switchLocation == 'R') {
				// TODO: drive until D4
			}
			else if (actionSelected == "scale") {
				
			}
		break;
		case 3:
			if(actionSelected == "switch" && switchLocation == 'R') {
				if(pathSelected == "near") {
					// TODO: Drive until D3 - D1
				}
				else if(pathSelected == "far") {
					// TODO: Drive until D2 - D3
				}
			}
		break;
		// the stage where we score
		case 4:
			if(actionSelected == "switch") {
				if(updateCubeSwitch()) {
					mainStage++;
				}
			}
			else if(actionSelected == "scale") {
				if(updateCubeScale()) {
					mainStage++;
				}
			}
		break;
		case 5:
			// set everything to 0
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
	
	boolean updateCubeSwitch() {
		//lower lift, place cube (at height appropriate for switch)
		return true;
	}
	
	boolean updateCubeScale() {
		//lower lift, place cube (at height appropriate for scale)
		return true;
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
	
	boolean pollLocations() {
		switchLocation = DriverStation.getInstance().getGameSpecificMessage().charAt(0);
		scaleLocation = DriverStation.getInstance().getGameSpecificMessage().charAt(1);
		if((switchLocation == 'R' || switchLocation == 'L') && (scaleLocation == 'R' || scaleLocation == 'L')) {
			return true;
		}
		else {
			return false;
		}
	}
}
