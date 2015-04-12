package org.usfirst.frc.team4334.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Toggles {
	
	static boolean stillPressed3;
	static boolean stillPressed1;
	static boolean stillPressed6;
	static boolean stillPressed2;
	static boolean stillPressed5;
    static boolean stillPressed;
	
	public static void SpeedToggle(){
		if (Robot.cole.getRawButton(3) && (stillPressed3 == false))
		{
			if (Robot.speedMultiplier == 1)
			{
				Robot.speedMultiplier = 0.4;
				Robot.turnRad = 1.8;
				stillPressed3 = true;
			}
			else if (Robot.speedMultiplier == 0.4)
			{
				Robot.speedMultiplier = 1;
    			Robot.turnRad = 0.74;
    			stillPressed3 = true;
			}		
		}
	}
	
	public static void GearPrint(){
		if(Robot.gearShift.get() == DoubleSolenoid.Value.kForward)
    	{
    		Robot.gearPos2 = "High Gear";
    	}
		else if(Robot.gearShift.get() == DoubleSolenoid.Value.kReverse)
    	{
    		Robot.gearPos2 = "Low Gear";
    	}
	}
	
	public static void CamSetToggle(){
		if (Robot.miranda.getRawButton(1) == false) {stillPressed1 = false;}
    	
    	if (Robot.miranda.getRawButton(1) && (stillPressed1 == false))
    	{
    		if (Robot.gotoCam)
			{
				Robot.gotoCam = false;
				
			}
    		else if (!Robot.gotoCam)
    		{
    			Robot.gotoCam = true;
    		}
    		
    		Robot.camActivate = true;
    		stillPressed1 = true;
    	}
	}
	
	public static void CamModeToggle(){

		if (Robot.miranda.getRawButton(6) && (stillPressed6 == false))
    	{
    		if (Robot.camMode == 1)
    			{
    				Robot.camMode = 2;
    			}
    		else if(Robot.camMode == 2)
    		{
    			Robot.camMode = 1;
    		}
    	}
		stillPressed6 = true;
	}
	
	public static void GearShift(){
		if (Robot.cole.getRawButton(2) && stillPressed2)
    	{	
    		if (Robot.gearShift.get() == DoubleSolenoid.Value.kForward)
    			{
    			Robot.gearShift.set(DoubleSolenoid.Value.kReverse);  		
    			stillPressed2=true;
    			}
    		else
    		{
    			Robot.gearShift.set(DoubleSolenoid.Value.kForward);
    			stillPressed2=true;
    		}
    	}
	}
	
	public static void ArmToggle(){
		if (Robot.miranda.getRawButton(5) && stillPressed5 == false|| Robot.cole.getRawButton(5) && stillPressed5 == false)
    	{
    		if ((Robot.leftArm.get() == DoubleSolenoid.Value.kForward) && (Robot.rightArm.get() == DoubleSolenoid.Value.kForward))
   			{
   				Robot.leftArm.set(DoubleSolenoid.Value.kReverse);  
   				Robot.rightArm.set(DoubleSolenoid.Value.kReverse);
   				stillPressed5=true;
   			}
    		else 
    		{
    			Robot.leftArm.set(DoubleSolenoid.Value.kForward);
    			Robot.rightArm.set(DoubleSolenoid.Value.kForward);
    			stillPressed5=true;
    		}
    	}
	}
	
	public static void StingerToggle(){
		if (Robot.cole.getRawButton(1) == false) {stillPressed = false;}
		
		if (Robot.cole.getRawButton(1) && (stillPressed == false))
		{
			if (Robot.flipper.get() == DoubleSolenoid.Value.kForward)
			{
				Robot.flipper.set(DoubleSolenoid.Value.kReverse);  		
				stillPressed=true;
			}
			else
			{
				Robot.flipper.set(DoubleSolenoid.Value.kForward);
				stillPressed=true;
			}
		}	
	}
}
