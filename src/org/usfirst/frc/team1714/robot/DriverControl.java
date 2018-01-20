package org.usfirst.frc.team1714.robot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick;



public class DriverControl {
	final int xboxPort = 2;
	final int joystickLeftPort = 0;
	final int joystickRightPort = 1;
	
	XboxController xbox;
	Joystick joystickLeft;
	Joystick joystickRight;
	
	DriverControl(){
		xbox = new XboxController(xboxPort);
		joystickLeft = new Joystick(joystickLeftPort);
		joystickRight = new Joystick(joystickRightPort);
	}
}
