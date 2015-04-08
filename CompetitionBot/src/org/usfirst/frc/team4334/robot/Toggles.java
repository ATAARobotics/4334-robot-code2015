package org.usfirst.frc.team4334.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Toggles {
	
	Robot main = new Robot();
	
	boolean stillPressed3;
	boolean stillPressed1;
	boolean stillPressed6;
	boolean stillPressed2;
	boolean stillPressed5;
    boolean stillPressed;
	
	public void SpeedToggle(){
		if (main.cole.getRawButton(3) == false) {stillPressed3 = false;}
    	
		if (main.cole.getRawButton(3) && (stillPressed3 == false))
		{
			if (main.speedMultiplier == 1)
			{
				main.speedMultiplier = 0.4;
				main.turnRad = 1.8;
				stillPressed3 = true;
			}
			else if (main.speedMultiplier == 0.4)
			{
				main.speedMultiplier = 1;
    			main.turnRad = 0.74;
    			stillPressed3 = true;
			}		
		}
	}
	
	public void GearPrint(){
		if(main.gearShift.get() == DoubleSolenoid.Value.kForward)
    	{
    		main.gearPos2 = "High Gear";
    	}
		else if(main.gearShift.get() == DoubleSolenoid.Value.kReverse)
    	{
    		main.gearPos2 = "Low Gear";
    	}
	}
	
	public void CamSetToggle(){
		if (main.miranda.getRawButton(1) == false) {stillPressed1 = false;}
    	
    	if (main.miranda.getRawButton(1) && (stillPressed1 == false))
    	{
    		if (main.gotoCam1)
			{
				main.gotoCam1 = false;
				
			}
    		else if (!main.gotoCam1)
    		{
    			main.gotoCam1 = true;
    		}
    		
    		main.camActivate = true;
    		stillPressed1 = true;
    	}
	}
	
	public void CamModeToggle(){
		if (main.miranda.getRawButton(6) == false) {stillPressed6 = false;}
		if (main.miranda.getRawButton(6) && (stillPressed6 == false))
    	{
    		if (main.camMode == 1)
    			{
    				main.camMode = 2;
    			}
    		else if(main.camMode == 2)
    		{
    			main.camMode = 1;
    		}
    	}
		stillPressed6 = true;
	}
	
	public void GearShift(){
		if (main.cole.getRawButton(2) == false) {stillPressed2 = false;}
		if (main.cole.getRawButton(2))
    	{	
    		if (main.gearShift.get() == DoubleSolenoid.Value.kForward)
    			{
    			main.gearShift.set(DoubleSolenoid.Value.kReverse);  		
    			stillPressed2=true;
    			}
    		else
    		{
    			main.gearShift.set(DoubleSolenoid.Value.kForward);
    			stillPressed2=true;
    		}
    	}
	}
	
	public void ArmToggle(){
		if (main.cole.getRawButton(5) == false && main.miranda.getRawButton(5) == false) {stillPressed5 = false;}
		if (main.miranda.getRawButton(5) && main.stillPressed5 == false|| main.cole.getRawButton(5) && stillPressed5 == false)
    	{
    		if ((main.leftArm.get() == DoubleSolenoid.Value.kForward) && (main.rightArm.get() == DoubleSolenoid.Value.kForward))
   			{
   				main.leftArm.set(DoubleSolenoid.Value.kReverse);  
   				main.rightArm.set(DoubleSolenoid.Value.kReverse);
   				stillPressed5=true;
   			}
    		else 
    		{
    			main.leftArm.set(DoubleSolenoid.Value.kForward);
    			main.rightArm.set(DoubleSolenoid.Value.kForward);
    			stillPressed5=true;
    		}
    	}
	}
	public void StingerToggle(){
		if (main.cole.getRawButton(1) == false) {stillPressed = false;}
		
		if (main.cole.getRawButton(1) && (stillPressed == false))
		{
			if (main.flipper.get() == DoubleSolenoid.Value.kForward)
			{
				main.flipper.set(DoubleSolenoid.Value.kReverse);  		
				stillPressed=true;
			}
			else
			{
				main.flipper.set(DoubleSolenoid.Value.kForward);
				stillPressed=true;
			}
		}	
	}
}
