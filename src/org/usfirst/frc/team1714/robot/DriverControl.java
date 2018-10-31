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
		if(mode == "one") {
			robot.driveVelX = -joystickRight.getY();
			robot.driveVelY = joystickRight.getX();
			robot.driveVelRotation = joystickRight.getZ();
		}
		else if (mode == "two"){
			if(hand == "R") {
				robot.driveVelX = -joystickRight.getY();
				robot.driveVelY = joystickRight.getX();
				robot.driveVelRotation = joystickLeft.getX();
			}
			else if(hand == "L") {
				robot.driveVelX = -joystickLeft.getY();
				robot.driveVelY = joystickLeft.getX();
				robot.driveVelRotation = joystickRight.getX();
			}
		}
		
		robot.liftVel = -xbox.getY(Hand.kLeft);
		
		if(xbox.getAButton()) {
			robot.intakeVel = 0.5;
		}
		else if(xbox.getYButton()) {
			robot.intakeVel = -1;
		}
		else if(xbox.getXButton() || xbox.getBButton()) {
			robot.intakeVel = 0;
		}
		else if(Math.abs(xbox.getY(Hand.kRight)) > 0.05 ) {
			robot.intakeVel = xbox.getY(Hand.kRight);
		}
		else {
			robot.intakeVel = 0;
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
		
		if(joystickRight.getRawButton(7)) {
			robot.resetGyro = true;
			robot.gyroOffset = 0;
		}
		else {
			robot.resetGyro = false;
		}
		
		if(joystickLeft.getRawButton(3) || joystickRight.getRawButton(2)) {
			robot.robotCentric = true;
		}
		else {
			robot.robotCentric = false;
		}
		
		if(joystickRight.getRawButton(10))
		{			
			robot.compressorEnable = true;
		}		
		else
		{
			robot.compressorEnable = false;
		}
		
	}
}
