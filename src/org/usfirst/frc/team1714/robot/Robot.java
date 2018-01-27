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

public class Robot extends IterativeRobot {
	//Autonomous Selector Values
	private String locationSelected;
	private String actionSelected;
	private String pathSelected;
	
	private double initDelay;
	private SendableChooser<String> locationChooser = new SendableChooser<>();
	private SendableChooser<String> actionChooser = new SendableChooser<>();
	private SendableChooser<String> pathChooser = new SendableChooser<>();
	private SendableChooser<String> scoreChooser = new SendableChooser<>();

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
	

	@Override
	public void robotInit() {
		locationChooser.addDefault("Left", "left");
		locationChooser.addObject("Middle", "middle");
		locationChooser.addObject("Right", "right");
		actionChooser.addObject("Score on Switch", "switch");
		actionChooser.addObject("Score on Scale", "scale");
		actionChooser.addObject("Cross Line", "line");
		
		SmartDashboard.putData("Location:", locationChooser);
		SmartDashboard.putString("Auto Delay: ", "");
		
		autonomous = new Autonomous();
		driverControl = new DriverControl();
		driveTrain = new DriveTrain();
		intake = new Intake();
		lift = new Lift();
		winch = new Winch();
	}

	@Override
	public void autonomousInit() {
		locationSelected = locationChooser.getSelected();
		actionSelected = actionChooser.getSelected();
		pathSelected = pathChooser.getSelected();
		initDelay = Double.parseDouble(SmartDashboard.getString("Auto Delay: ", "0.0"));
		
		int i = 0;
		while (!autonomous.pollLocations()) {
			i++;
			if (i > 10000) {
				System.out.println("Switch/Scale locations not valid");
				break;
			}
		}
	}

	@Override
	public void autonomousPeriodic() {
		switch (locationSelected) {
			default:
			case "left":
				autonomous.updateLeft(actionSelected, pathSelected, initDelay);
				break;
			case "middle":
				autonomous.updateMiddle(actionSelected, pathSelected, initDelay);
				break;
			case "right":
				autonomous.updateRight(actionSelected, pathSelected, initDelay);
				break;
		}
	}

	@Override
	public void teleopPeriodic() {
		System.out.println(SmartDashboard.getString("Auto Delay: ", "0.0"));
	}

	@Override
	public void testPeriodic() {
	}
}
