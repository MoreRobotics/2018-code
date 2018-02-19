package org.usfirst.frc.team1714.robot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;



public class DriverControl {
	/*
	 * 
	 * 
	 *  THESE ARE PLACEHOLDER VALUES, CHANGE THEM 
	 * 
	 * 
	 */
		
	final int xboxPort = 2;
	int joystickLeftPort = 0;
	int joystickRightPort = 1;
	
	XboxController xbox;
	Joystick joystickLeft;
	Joystick joystickRight;
	
	DriverControl(){
		xbox = new XboxController(xboxPort);
		joystickLeft = new Joystick(joystickLeftPort);
		joystickRight = new Joystick(joystickRightPort);
	}
	
	public void update(Robot robot, String hand, String mode) {
		/*if (hand == "R") {
			if(mode == "two") {
				joystickLeftPort = 1;
				joystickRightPort = 0;
			}
			else if(mode == "one") {
				joystickLeftPort = 4;
				joystickRightPort = 0;
			}
		}
		else if(hand == "L") {
			if(mode == "two") {
				joystickLeftPort = 0;
				joystickRightPort = 1;
			}
			else if(mode == "one") {
				joystickLeftPort = 1;
				joystickRightPort = 4;
			}
		}
		*/
		//if(mode == "two") {
			robot.driveVelX = -joystickRight.getY();
			robot.driveVelY = joystickRight.getX();
			robot.driveVelRotation = joystickRight.getZ();
		//}
		/*else if(mode == "one") {
			robot.driveVelX = joystickRight.getX();
			robot.driveVelY = joystickRight.getY();
			robot.driveVelRotation = joystickRight.getZ();
		}*/
		
		robot.liftVel = xbox.getY(Hand.kLeft);
		
		if(xbox.getAButton()) {
			robot.intakeIn = true;
			robot.intakeOut = false;
		}
		else if(xbox.getYButton()) {
			robot.intakeIn = false;
			robot.intakeOut = true;
		}
		else if(xbox.getXButton() || xbox.getBButton()) {
			robot.intakeIn = false;
			robot.intakeOut = false;
		}
		
		if(xbox.getPOV() == 0) {
			robot.winchUp = true;
			robot.winchDown = false;
		}
		else if (xbox.getPOV() == 180) {
			robot.winchUp = false;
			robot.winchDown = true;
		}
		else {
			robot.winchUp = false;
			robot.winchDown = false;
		}
		
		if(xbox.getBumper(Hand.kLeft)) {
			robot.extended = true;
		}
		else if(xbox.getTriggerAxis(Hand.kLeft) > 0.7 ){
			robot.extended = false;
		}
		
		if(xbox.getBumper(Hand.kRight)) {
			robot.grasping = true;
		}
		else if(xbox.getTriggerAxis(Hand.kRight) > 0.7) {
			robot.grasping = false;
		}
	}
}
