package org.usfirst.frc.team1714.robot;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Autonomous {
	final int d1 = 0; //distance from starting position to "near path"
	final int d2 = 0; //distance from starting position to "far path"
	final int d3 = 0; //distance from d1 to switch scoring position
	final int d4 = 0; //the total "width" of either the near or far path
	final int d5 = 0; //the distance from middle starting position to right starting position
	final int d6 = d4 - d5; //the distance from middle starting position to left starting position
	
	// 20 inches
	final double switchDetectDistance = 635;
	final double driverWallDistance = 0;
	// 30 inches
	final double sideWallDistance = 762;
	final double driveSpeed = 0.75;
	final double turnSpeed = 0.5;
	final double extendTime = 1;
	final double switchFarWallDist = 0;

	int mainStage = 0;
	
	char switchLocation = 0;
	char scaleLocation = 0;
	
	boolean timerStarted = false;
	double startTime;
	
	final double turnAroundMax = 0;
	final double turnAroundMin = 0;
	
	// the constant controlling how far the robot drives after reaching the switch (when going the far path)
	final double switchFarTimeConst = 2;
	// the distance between the robot's front US sensor and the wall (at end of far path)
	final double scaleFartherTimeConst = 3;
	
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
				robot.driveVelX = driveSpeed;
				if(!timerStarted) {
					startTime = Timer.getFPGATimestamp();
					timerStarted = true;
				}
				else {
					if(Timer.getFPGATimestamp() - startTime > 5) {
						robot.driveVelX = 0;
						timerStarted = false;
						mainStage = 5;
					}
				}
			}
			else if(actionSelected == "switch")
			{
				robot.extended = true;
				robot.liftTargetGround = true;
				if(switchLocation == 'L') {
					if(!timerStarted) {
						startTime = Timer.getFPGATimestamp();
						timerStarted = true;
					}
					// drive until we're next to the switch plate
					if(robot.driveTrain.usFront.getRangeMM() > switchDetectDistance) {
						robot.driveVelX = driveSpeed;
					}
					else if (Timer.getFPGATimestamp() - startTime > 3) {
						robot.driveVelX = 0;
						timerStarted = false;
						mainStage = 2;
					}
				}
				else if(switchLocation == 'R') {
					// drive until we're next to the switch plate
					if(robot.driveTrain.usFront.getRangeMM() > switchDetectDistance) {
						robot.driveVelX = driveSpeed;
					}
					else {
						// once we are, drive forward a little bit more
						if(!timerStarted) {
							startTime = Timer.getFPGATimestamp();
							timerStarted = true;
						}
						else {
							if(Timer.getFPGATimestamp() - startTime > (switchFarTimeConst/driveSpeed)) {
								robot.driveVelX = 0;
								timerStarted = false;
								mainStage++;
							}
						}
					}
				}
			}
		break;
		case 2:
			if(actionSelected == "switch" && switchLocation == 'R') {
				robot.extended = true;
				robot.liftTargetSwitch = true;
				robot.driveVelY = driveSpeed;
				if(!timerStarted) {
					startTime = Timer.getFPGATimestamp();
					timerStarted = true;
				}
				if(robot.driveTrain.usFront.getRangeMM() < switchFarWallDist && Timer.getFPGATimestamp() - startTime > 3) {
					robot.driveVelY = 0;
					timerStarted = false;
					mainStage++;
				}
			}
			else if(actionSelected == "switch" && switchLocation == 'L') {
				robot.liftTargetGround = false;
				robot.liftTargetSwitch = true;
				if(!timerStarted) {
					startTime = Timer.getFPGATimestamp();
					timerStarted = true;
				}
				else if (Timer.getFPGATimestamp() - startTime > 8) {
					robot.driveVelX = 0;
					timerStarted = false;
					mainStage = 4;
				}
			}
		break;
		case 3:
			if(actionSelected == "switch" && switchLocation == 'R') {
				// ROTATE & Drive until D2 - D3
				if(robot.driveTrain.gyro.getAngle() > turnAroundMin && robot.driveTrain.gyro.getAngle() < turnAroundMax) {
					robot.driveVelRotation = 0;
					if(robot.driveTrain.usFront.getRangeMM() > switchDetectDistance) {
						robot.driveVelX = -driveSpeed;
					}
					else {
						robot.driveVelX = 0;
						mainStage = 6;
					}
				}
				else {
					robot.driveVelRotation = turnSpeed;
				}
			}
		break;
		// the stage where we score
		case 4:
			if(actionSelected == "switch") {
				if(updateCubeSwitch(robot)) {
					mainStage++;
				}
			}
			else if(actionSelected == "scale") {
				if(updateCubeScale(robot)) {
					mainStage++;
				}
			}
		break;
		case 5:
			// set everything to 0
			robot.driveVelY = 0;
			robot.driveVelX = 0;
			robot.driveVelRotation = 0;
		break;
		case 6:
			if(switchLocation == 'L')
			{
				robot.driveVelY = -driveSpeed;
			}
			else if(switchLocation == 'R')
			{
				robot.driveVelY = driveSpeed;
			}
			if(robot.driveTrain.usFront.getRangeMM() > 700) {
				robot.extended = true;
				mainStage++;
			}
		break;
		case 7:
			if(!timerStarted) {
				startTime = Timer.getFPGATimestamp();
				timerStarted = true;
			}
			else {
				if(Timer.getFPGATimestamp() - startTime > 1) {
					if(switchLocation == 'L')
					{
						robot.driveVelY = driveSpeed;
					}
					else if(switchLocation == 'R')
					{
						robot.driveVelY = -driveSpeed;
					}
					timerStarted = false;
					mainStage++;
				}
			}
			robot.liftTargetSwitch = true;
		break;
		case 8:
			if(robot.driveTrain.usFront.getRangeMM() < 200) {
				robot.driveVelY = 0;
				mainStage = 4;
			}
		break;
		}
	}
	
	
	//NOTE: WHEN RUNNING MIDDLE AUTO, THE ROBOT SHOULD START BACKWARDS
	void updateMiddle(Robot robot, String actionSelected, double delay, String lineSideSelected) {
		switch(mainStage) {
		case 0:
			if(initialDelay(delay)) {
				mainStage++;
			}
		break;
		case 1:
			// Drive until D1
			if(robot.driveTrain.usFront.getRangeMM() < driverWallDistance) {
				robot.driveVelX = driveSpeed;
			}
			else {
				robot.driveVelX = 0;
				mainStage++;
			}
		break;
		case 2:
			if(lineSideSelected == "L") {
				//drive to d6
				if(robot.driveTrain.usRight.getRangeMM() > sideWallDistance) {
					robot.driveVelY = -driveSpeed;
				}
				else {
					robot.driveVelY = 0;
					mainStage++;
				}
			}
			else if(lineSideSelected == "R") {
				// drive to d5
				if(robot.driveTrain.usLeft.getRangeMM() > sideWallDistance) {
					robot.driveVelY = driveSpeed;
				}
				else {
					robot.driveVelY = 0;
					mainStage++;
				}
			}
		break;
		case 3:
			// drive to d3 - d1
			if(lineSideSelected == "L") {
				if(robot.driveTrain.usLeft.getRangeMM() > switchDetectDistance) {
					robot.driveVelX = driveSpeed;
				}
				else {
					robot.driveVelX = 0;
					mainStage++;
				}
			}
			else if(lineSideSelected == "R") {
				if(robot.driveTrain.usRight.getRangeMM() > switchDetectDistance) {
					robot.driveVelX = driveSpeed;
				}
				else {
					robot.driveVelX = 0;
					mainStage++;
				}
			}
		break;
		case 4:
			// set everything to 0
			robot.driveVelY = 0;
			robot.driveVelX = 0;
		break;
		}
	}
	
	void updateRight(Robot robot, String actionSelected, double delay) {
		switch(mainStage) {
		case 0:
			if(initialDelay(delay)) {
				mainStage++;
			}
		break;
		case 1:
			if(actionSelected == "line") {
				// drive until we're next to the switch plate
				robot.driveVelX = driveSpeed;
				if(!timerStarted) {
					startTime = Timer.getFPGATimestamp();
					timerStarted = true;
				}
				else {
					if(Timer.getFPGATimestamp() - startTime > 5) {
						robot.driveVelX = 0;
						timerStarted = false;
						mainStage = 5;
					}
				}
			}
			else if(actionSelected == "switch")
			{
				robot.extended = true;
				robot.liftTargetSwitch = true;
				if(switchLocation == 'R') {
					if(!timerStarted) {
						startTime = Timer.getFPGATimestamp();
						timerStarted = true;
					}
					// drive until we're next to the switch plate
					if(robot.driveTrain.usFront.getRangeMM() > switchDetectDistance) {
						robot.driveVelX = driveSpeed;
					}
					else if (Timer.getFPGATimestamp() - startTime > 3) {
						robot.driveVelX = 0;
						mainStage = 4;
					}
				}
				else if(switchLocation == 'L') {
					// drive until we're next to the switch plate
					if(robot.driveTrain.usFront.getRangeMM() > switchDetectDistance) {
						robot.driveVelX = driveSpeed;
					}
					else {
						// once we are, drive forward a little bit more
						if(!timerStarted) {
							startTime = Timer.getFPGATimestamp();
							timerStarted = true;
						}
						else {
							if(Timer.getFPGATimestamp() - startTime > (switchFarTimeConst/driveSpeed)) {
								robot.driveVelX = 0;
								timerStarted = false;
								mainStage++;
							}
						}
					}
				}
			}
			else if(actionSelected == "scale") {
				// drive until we're next to the switch plate
				if(robot.driveTrain.usFront.getRangeMM() > switchDetectDistance) {
					robot.driveVelX = driveSpeed;
				}
				else {
					// once we are, drive forward a little bit more
					if(!timerStarted) {
						startTime = Timer.getFPGATimestamp();
						timerStarted = true;
					}
					else {
						if(Timer.getFPGATimestamp() - startTime > (switchFarTimeConst/driveSpeed)) {
							robot.driveVelX = 0;
							timerStarted = false;
							mainStage++;
						}
					}
				}
			}
		break;
		case 2:
			if(actionSelected == "switch" && switchLocation == 'L') {
				// drive until D4
				robot.extended = true;
				robot.liftTargetSwitch = true;
				robot.driveVelY = driveSpeed;
				if(!timerStarted) {
					startTime = Timer.getFPGATimestamp();
					timerStarted = true;
				}
				if(robot.driveTrain.usFront.getRangeMM() < switchFarWallDist && Timer.getFPGATimestamp() - startTime > 3) {
					robot.driveVelY = 0;
					mainStage++;
				}
			}
			else if (actionSelected == "scale") {
				robot.driveVelX = driveSpeed;
				if(!timerStarted) {
					startTime = Timer.getFPGATimestamp();
					timerStarted = true;
				}
				else {
					if(scaleLocation == 'L') {
						if(robot.driveTrain.usFront.getRangeMM() > sideWallDistance) {
							robot.driveVelY = -driveSpeed;
						}
						else {
							robot.driveVelY = 0;
							mainStage++;
						}
					}
					if(scaleLocation == 'R') {
						if(Timer.getFPGATimestamp() - startTime > (scaleFartherTimeConst/driveSpeed)) {
							robot.driveVelX = 0;
							timerStarted = false;
							mainStage = 4;
						}
					}
				}
			}
		break;
		case 3:
			if(actionSelected == "switch" && switchLocation == 'L') {
				// Drive until D2 - D3
				if(robot.driveTrain.gyro.getAngle() > 3600 - turnAroundMin && robot.driveTrain.gyro.getAngle() < turnAroundMax) {
					robot.driveVelRotation = 0;
					if(robot.driveTrain.usFront.getRangeMM() > switchDetectDistance) {
						robot.driveVelX = -driveSpeed;
					}
					else {
						robot.driveVelX = 0;
						mainStage++;
					}
				}
				else {
					robot.driveVelRotation = turnSpeed;
				}
			}
			else if(actionSelected == "scale" && scaleLocation == 'L') {
				if(robot.driveTrain.gyro.getAngle() > 3600 - turnAroundMin && robot.driveTrain.gyro.getAngle() < turnAroundMax) {
					robot.driveVelRotation = 0;
					if(!timerStarted) {
						startTime = Timer.getFPGATimestamp();
						timerStarted = true;
					}
					else {
						if(Timer.getFPGATimestamp() - startTime > (scaleFartherTimeConst/driveSpeed)) {
							robot.driveVelX = 0;
							timerStarted = false;
							mainStage++;
						}
						else {
							robot.driveVelX = driveSpeed;
						}
					}
				}
				else {
					robot.driveVelRotation = -turnSpeed;
				}
				mainStage++;
			}
		break;
		// the stage where we score
		case 4:
			if(actionSelected == "switch") {
				if(updateCubeSwitch(robot)) {
					mainStage++;
				}
			}
			else if(actionSelected == "scale") {
				if(updateCubeScale(robot)) {
					mainStage++;
				}
			}
		break;
		case 5:
			// set everything to 0
			robot.driveVelY = 0;
			robot.driveVelX = 0;
			robot.driveVelRotation = 0;
		break;
		}
	}
	
	
	double forgiveness = 0;
	boolean updateCubeSwitch(Robot robot) {
		// lower lift, place cube (at height appropriate for switch)
		//if(robot.lift.pot.get() > robot.lift.targetHeightSwitch - forgiveness && robot.lift.pot.get() < robot.lift.targetHeightSwitch + forgiveness) {
		robot.extended = true;
		if(!timerStarted) {
			startTime = Timer.getFPGATimestamp();
			timerStarted = true;
		}
		else {
			if(Timer.getFPGATimestamp() - startTime > extendTime) {
				robot.liftTargetSwitch = true;
				if(robot.lift.pot.get() > robot.lift.targetHeightSwitch - forgiveness && robot.lift.pot.get() < robot.lift.targetHeightSwitch + forgiveness) {
					robot.grasping = false;
					return true;
				}
			}
		}
		//}
		return false;
	}
	
	boolean updateCubeScale(Robot robot) {
		//TODO: FIX THIS IF STATEMENT
		// lower lift, place cube (at height appropriate for scale)
		robot.extended = true;
		if(!timerStarted) {
			startTime = Timer.getFPGATimestamp();
			timerStarted = true;
		}
		else {
			if(Timer.getFPGATimestamp() - startTime > extendTime) {
				robot.liftTargetScale = true;
				if(robot.lift.pot.get() > robot.lift.targetHeightScale - forgiveness && robot.lift.pot.get() < robot.lift.targetHeightScale + forgiveness) {
					robot.grasping = false;
					return true;
				}
			}
		}
		//}
		return false;
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
