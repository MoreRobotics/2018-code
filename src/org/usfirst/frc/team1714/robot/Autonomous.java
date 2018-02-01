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
	
	// TODO: FIX THESE PLACEHOLDERS
	final double switchDetectDistance = 0;
	final double driveSpeed = 0.75;
	
	int mainStage = 0;
	
	char switchLocation = 0;
	char scaleLocation = 0;
	
	boolean timerStarted = false;
	double startTime;
	
	// the constant controlling how far the robot drives after reaching the switch (when going the far path)
	final double switchFarTimeConst = 2;
	// the distance between the robot's front US sensor and the wall (at end of far path)
	final double switchFarWallDist = 0;
	
	// this loop handles all routines that begin with the robot in the left position
	void updateLeft(Robot robot, String actionSelected, double delay) {
		// for time based segments, use time = constant/speed
		// (inverse variation) - yonathan
		// constant = desired time at highest speed
		// constant is invalid when weight changes
		
		switch(mainStage) {
		case 0:
			if(initialDelay(delay)) {
				mainStage++;
			}
		break;
		case 1:
			if(actionSelected == "line") {
				// drive until we're next to the switch plate
				if(robot.driveTrain.usFront.getRangeMM() > switchDetectDistance) {
					robot.driveVelY = driveSpeed;
				}
				else {
					robot.driveVelY = 0;
					// jump to cube placement
					mainStage = 5;
				}
			}
			else if(actionSelected == "switch")
			{
				if(switchLocation == 'L') {
					// drive until we're next to the switch plate
					if(robot.driveTrain.usFront.getRangeMM() > switchDetectDistance) {
						robot.driveVelY = driveSpeed;
					}
					else {
						robot.driveVelY = 0;
						mainStage = 4;
					}
				}
				else if(switchLocation == 'R') {
					// drive until we're next to the switch plate
					if(robot.driveTrain.usFront.getRangeMM() > switchDetectDistance) {
						robot.driveVelY = driveSpeed;
					}
					else {
						// once we are, drive forward a little bit more
						if(!timerStarted) {
							startTime = Timer.getFPGATimestamp();
							timerStarted = true;
						}
						else {
							if(Timer.getFPGATimestamp() - startTime > (switchFarTimeConst/driveSpeed)) {
								robot.driveVelY = 0;
								timerStarted = false;
								mainStage++;
							}
						}
					}
				}
			}
			else if(actionSelected == "scale") {
				//TODO
			}
		break;
		case 2:
			if(actionSelected == "switch" && switchLocation == 'R') {
				robot.driveVelY = driveSpeed;
				if(robot.driveTrain.usFront.getRangeMM() > switchFarWallDist) {
					robot.driveVelY = 0;
					mainStage++;
				}
			}
			else if (actionSelected == "scale") {
				//TODO
			}
		break;
		case 3:
			if(actionSelected == "switch" && switchLocation == 'R') {
				// TODO: ROTATE & Drive until D2 - D3
				if(robot.driveTrain.gyro.)
			}
			// TODO: scale code here?
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
	
	void updateMiddle(String actionSelected, double delay, String lineSideSelected) {
		switch(mainStage) {
		case 0:
			if(initialDelay(delay)) {
				mainStage++;
			}
		break;
		case 1:
			// TODO: Drive until D1
		break;
		case 2:
			if(lineSideSelected == "L") {
				//TODO: drive to d6
				mainStage++;
			}
			else if(lineSideSelected == "R") {
				//TODO: drive to d5
				mainStage++;
			}
		break;
		case 3:
			//TODO: drive to d3 - d1
		break;
		case 4:
			// set everything to 0
		break;
		}
	}
	
	void updateRight(String actionSelected, double delay) {
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
					// TODO: drive until D2
					mainStage++;
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
				// TODO: Drive until D2 - D3
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
	
	boolean updateCubeSwitch() {
		//TODO: lower lift, place cube (at height appropriate for switch)
		return true;
	}
	
	boolean updateCubeScale() {
		//TODO: lower lift, place cube (at height appropriate for scale)
		return true;
	}
	
	//this function will start a timer when first called, then return false until the passed amount of time has passed.
	//we can use it at the start of each auto update loop to allow the drivers to set a delay at the start of each match
	boolean initialDelay(double delayTime) {
		if(!timerStarted) {
			startTime = Timer.getFPGATimestamp();
			timerStarted = true;
			return false;
		}
		else {
			if(Timer.getFPGATimestamp() - startTime > delayTime) {
				timerStarted = false;
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
