package org.usfirst.frc.team1714.robot;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SPI;

public class DriveTrain {
	/*
	 * 
	 * 
	 *  THESE ARE PLACEHOLDER VALUES, CHANGE THEM 
	 * 
	 * 
	 */
	
	// Drive Train Pins
	final int mFrontLeftPin = 0; 
	final int mFrontRightPin = 1;
	final int mRearLeftPin = 2;  
	final int mRearRightPin = 3;
	//Gyro Final Int Here
	final int usLeftInPin = 3;
	final int usLeftOutPin = 2;
	final int usRightInPin = 8;
	final int usRightOutPin = 9;
	final int usFrontInPin = 7;
	final int usFrontOutPin = 6;
	
	Spark mFrontLeft; 
	Spark mFrontRight;
	Spark mRearLeft;
	Spark mRearRight;
	
	public ADXRS450_Gyro gyro;
	public Ultrasonic usLeft;
	public Ultrasonic usRight;
	public Ultrasonic usFront;
	
	public boolean resetAntiDriftGyroAngle = false;
	private double antiDriftGyroAngle;
	private double antiDriftVelRotation;
	private final double antiDriftConstant = 15;
	private final double joystickDeadzone = 0.025;
	
	MecanumDrive mecanum;
	
	DriveTrain() {
		mFrontLeft = new Spark(mFrontLeftPin);
		mFrontRight = new Spark(mFrontRightPin);
		mRearLeft = new Spark(mRearLeftPin);
		mRearRight = new Spark(mRearRightPin);
		mFrontLeft.setInverted(true);
		mFrontRight.setInverted(true);
		mRearLeft.setInverted(true);
		mRearRight.setInverted(true);

		gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS2);
		antiDriftGyroAngle = gyro.getAngle();
		usLeft = new Ultrasonic(usLeftOutPin, usLeftInPin);
		usRight = new Ultrasonic(usRightOutPin, usRightInPin);
		usFront = new Ultrasonic(usFrontOutPin, usFrontInPin);
		usLeft.setAutomaticMode(true);
		usRight.setAutomaticMode(true);
		usFront.setAutomaticMode(true);
		
		mecanum = new MecanumDrive(mFrontLeft, mRearLeft, mFrontRight, mRearRight);
	}
	
	public void update(double driveVelX, double driveVelY, double driveVelRotation, boolean resetGyro, boolean robotCentric, double gyroOffset) {
		SmartDashboard.putNumber("right us", usRight.getRangeMM());
		SmartDashboard.putNumber("left us", usLeft.getRangeMM());
		SmartDashboard.putNumber("front us", usFront.getRangeMM());
		SmartDashboard.putNumber("gyro value", gyro.getAngle());

		
		
		if(resetAntiDriftGyroAngle) {
			antiDriftGyroAngle = gyro.getAngle();
			resetAntiDriftGyroAngle = false;
		}
		
		if(resetGyro) {
			gyro.calibrate();
		}
		
		if(driveVelRotation > -joystickDeadzone && driveVelRotation < joystickDeadzone) {
			//This is neither a direct nor an inverse relationship y=(x/k) - Yonathan
			antiDriftVelRotation = (antiDriftGyroAngle - gyro.getAngle()) / antiDriftConstant;
		}
		else {
			antiDriftVelRotation = driveVelRotation;
			antiDriftGyroAngle = gyro.getAngle();
		}
		
		if(!robotCentric) {
			mecanum.driveCartesian(driveVelY, driveVelX, antiDriftVelRotation, -(gyro.getAngle() + gyroOffset));
		}
		else if(robotCentric) {
			mecanum.driveCartesian(driveVelY, driveVelX, antiDriftVelRotation);
		}
	}
}
