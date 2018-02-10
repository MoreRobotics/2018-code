/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1714.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode.PixelFormat;

public class Robot extends IterativeRobot {
	//Autonomous Selector Values
	private String locationSelected;
	private String actionSelected;
	private String lineSideSelected;
	
	private double initDelay;
	private SendableChooser<String> locationChooser = new SendableChooser<>();
	private SendableChooser<String> actionChooser = new SendableChooser<>();
	private SendableChooser<String> lineSideChooser = new SendableChooser<>();

	//Controller Values
	public double
		driveVelX,
		driveVelY,
		driveVelRotation,
		liftVel;
	public boolean
		liftTargetScale,
		liftTargetSwitch,
		liftTargetGround,
		winchUp,
		winchDown,
		intakeIn,
		intakeOut,
		extended,
		grasping;
	
	//Robot Classes
	Autonomous autonomous;
	DriverControl driverControl;
	DriveTrain driveTrain;
	Intake intake;
	Lift lift;
	Winch winch;
	
	UsbCamera cam;
	CameraServer camServer;

	@Override
	public void robotInit() {
		locationChooser.addDefault("Left", "left");
		locationChooser.addObject("Middle", "middle");
		locationChooser.addObject("Right", "right");
		actionChooser.addObject("Score on Switch", "switch");
		actionChooser.addObject("Score on Scale (NOT YET FUNCTIONAL)", "scale");
		actionChooser.addObject("Cross Line", "line");
		lineSideChooser.addObject("Left Side",  "L");
		lineSideChooser.addObject("Right Side", "R");
		
		SmartDashboard.putData("Location:", locationChooser);
		SmartDashboard.putString("Auto Delay: ", "");
		
		autonomous = new Autonomous();
		driverControl = new DriverControl();
		driveTrain = new DriveTrain();
		//intake = new Intake();
		lift = new Lift();
		//winch = new Winch();
		
		camServer = CameraServer.getInstance();
		cam = camServer.startAutomaticCapture();
		cam.setResolution(416, 240);
		cam.setFPS(30);		
	}

	@Override
	public void autonomousInit() {
		locationSelected = locationChooser.getSelected();
		actionSelected = actionChooser.getSelected();
		lineSideSelected = lineSideChooser.getSelected();
		initDelay = Double.parseDouble(SmartDashboard.getString("Auto Delay: ", "0.0"));
		
		int i = 0;
		while (!autonomous.pollLocations()) {
			i++;
			if (i > 10000) {
				System.out.println("Switch/Scale locations not valid");
				break;
			}
		}
		
		System.out.println(SmartDashboard.getString("Auto Delay: ", "0.0"));
	}

	@Override
	public void autonomousPeriodic() {
		switch (locationSelected) {
			default:
			case "left":
				autonomous.updateLeft(this, actionSelected, initDelay);
				break;
			case "middle":
				autonomous.updateMiddle(this, actionSelected, initDelay, lineSideSelected);
				break;
			case "right":
				autonomous.updateRight(this, actionSelected, initDelay);
				break;
		}
	}

	@Override
	public void teleopPeriodic() {
		driverControl.update(this);
		driveTrain.update(driveVelX, driveVelY, driveVelRotation);
		//intake.update(intakeIn, intakeOut, extended, grasping);
		lift.update(liftVel, liftTargetScale, liftTargetSwitch, liftTargetGround);
		//winch.update(winchUp, winchDown);
	}

	@Override
	public void testPeriodic() {
	}
}
