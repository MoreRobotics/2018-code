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
		intakeOut;
	
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
		SmartDashboard.putData("Location:", locationChooser);
		
		autonomous = new Autonomous();
		driverControl = new DriverControl();
		driveTrain = new DriveTrain();
		intake = new Intake();
		lift = new Lift();
		winch = new Winch();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		locationSelected = locationChooser.getSelected();
		actionSelected = actionChooser.getSelected();
		pathSelected = pathChooser.getSelected();
	}

	@Override
	public void autonomousPeriodic() {
		switch (locationSelected) {
			default:
			case "left":
				//autonomous.updateLeft(actionSelected, pathSelected);
				break;
			case "middle":
				//autonomous.updateMiddle(actionSelected, pathSelected);
				break;
			case "right":
				//autonomous.updateRight(actionSelected, pathSelected);
				break;
		}
	}

	@Override
	public void teleopPeriodic() {
	}

	@Override
	public void testPeriodic() {
	}
}
