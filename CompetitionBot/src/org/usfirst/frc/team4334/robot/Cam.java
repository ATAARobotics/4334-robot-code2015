package org.usfirst.frc.team4334.robot;

public class Cam {

	public static void Auto(boolean mode){
		if(mode){
			while(Robot.potDegrees < 2.91){
				Robot.talKicker.set(-1);
			}
			Robot.talKicker.set(0);
			}
		
		else{
			while(Robot.potDegrees < 2.5){
				Robot.talKicker.set(-1);
			}
			Robot.talKicker.set(0);
			}
		}
	
	
	public static void Manual(){	
    		if(Robot.cole.getRawButton(8) == false)
    		{
    			Robot.talKicker.set(0);
    		}
    	
    		else if(Robot.cole.getRawButton(8) == true)
    		{
    			Robot.talKicker.set(-1);
    		}
	}
}
