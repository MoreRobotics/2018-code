package org.usfirst.frc.team1714.robot;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

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
	final int usLeftInPin = 0;
	final int usRightInPin = 1;
	final int usFrontInPin = 2;
	final int usLeftOutPin = 3;
	final int usRightOutPin = 4;
	final int usFrontOutPin = 5;
	
	Spark mFrontLeft; 
	Spark mFrontRight;
	Spark mRearLeft;
	Spark mRearRight;
	
	public ADXRS450_Gyro gyro;
	public Ultrasonic usLeft;
	public Ultrasonic usRight;
	public Ultrasonic usFront;
	
	MecanumDrive mecanum;
	
	DriveTrain() {
		mFrontLeft = new Spark(mFrontLeftPin);
		mFrontRight = new Spark(mFrontRightPin);
		mFrontLeft = new Spark(mRearLeftPin);
		mFrontLeft = new Spark(mRearRightPin);
		
		gyro = new ADXRS450_Gyro();
		usLeft = new Ultrasonic(usLeftOutPin, usLeftInPin);
		usRight = new Ultrasonic(usRightOutPin, usRightInPin);
		usFront = new Ultrasonic(usFrontOutPin, usFrontInPin);
		
		mecanum = new MecanumDrive(mFrontLeft, mRearLeft, mFrontRight, mRearRight);
	}
	
	public void update(double driveVelX, double driveVelY, double driveVelRotation) {
		mecanum.driveCartesian(driveVelY, driveVelX, driveVelRotation, gyro.getAngle());
	}
}