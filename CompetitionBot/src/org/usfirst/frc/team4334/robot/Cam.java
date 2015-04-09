package org.usfirst.frc.team4334.robot;

public class Cam {

	public static void Move(int mode){
		if(mode == 1){
			while(Robot.potDegrees < 2.91){
				Robot.talKicker.set(-1);
			}
			Robot.talKicker.set(0);
			}
		
		else if(mode == 2){
			while(Robot.potDegrees < 2.5){
				Robot.talKicker.set(-1);
			}
			Robot.talKicker.set(0);
			}
		}
	
	
	public static void manual(){
		if(Robot.camMode == 2)
    	{
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
}
