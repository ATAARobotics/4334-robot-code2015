package org.usfirst.frc.team4334.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Auto {
	Encoder encoderL;
	Encoder encoderR;
	SpeedController canFL;
	SpeedController canBL;
	SpeedController canFR;
	SpeedController canBR;
	Talon leftTal;
	Talon rightTal;
	public Auto(Encoder leftEncoder, Encoder rightEncoder, SpeedController FrontLeft, SpeedController BackLeft, SpeedController FrontRight, SpeedController BackRight, Talon LeftTal, Talon RightTal){
		encoderL = leftEncoder;
		encoderR = rightEncoder;
		canFL = FrontLeft;
		canBL = BackLeft;
		canFR = FrontRight;
		canBR = BackRight;
		leftTal = LeftTal;
		rightTal = RightTal;
	}
	
	public void drive(int distance, double power)
    {
    	encoderR.reset();
		encoderL.reset();
    	
		System.out.println("Drive Start");
		
		while(Math.abs(encoderR.getDistance()) < distance)
		{
			canFL.set(-power);
			canBL.set(-power);
			
			canBR.set(power);
			canFR.set(power);
			
			System.out.println(encoderR.get());
		}
		
		System.out.println("Drive Done");
		
    	canFL.set(0);
		canBL.set(0);
		
		canBR.set(0);
		canFR.set(0);
		
		encoderR.reset();
		encoderL.reset();
    }
	
	public void setTurn(double turnDegrees, double power)
    {
    	//Sets the drivetrain motors to the given power to make a right angle turn
    	
    	encoderL.reset();
    	encoderR.reset();
    	
    	System.out.println("Turn Starts");
    	
    	while(Math.abs(encoderR.getDistance()) < (turnDegrees * (550/90)))
    	{
    		canFL.set(power);
    		canBL.set(power);
    		
    		canBR.set(power);
    		canFR.set(power);
    		
    		System.out.println(encoderR.get());
    	}
    	
    	canFL.set(0);
		canBL.set(0);
		
		canBR.set(0);
		canFR.set(0);
		
		System.out.println("Turn Done");
		
		encoderL.reset();
		encoderR.reset();
    }
	
	public void moveArms(int time, int power)
    {
    	//Moves arms given power
    	
    	leftTal.set(-power);
    	rightTal.set(power);
    		
    	//Waits for the given time
    	
    	try {
    			Thread.sleep(time);
    	} catch (InterruptedException e) {
    		
   			Thread.currentThread().interrupt();
   		}
    	
    	//Stops
    	
    	leftTal.set(0);
		rightTal.set(0);
    }
	
	public void moveArmswhileDrive(int distance, double power, int powerArms)
    {
    	//Moves arms given power
    	encoderR.reset();
		encoderL.reset();
		
    	leftTal.set(-powerArms);
    	rightTal.set(powerArms);
    	
		while(Math.abs(encoderR.get()) < distance)
		{
			canFL.set(-power);
			canBL.set(-power);
			
			canBR.set(power);
			canFR.set(power);
			
			leftTal.set(-powerArms);
	    	rightTal.set(powerArms);
		}
		
		//Stops
    	canFL.set(0);
		canBL.set(0);
		
		canBR.set(0);
		canFR.set(0);
		
    	leftTal.set(0);
		rightTal.set(0);
		
		encoderR.reset();
		encoderL.reset();
    	
    }
	
}
