package org.usfirst.frc.team4334.robot;

public class Cam {
	Robot main = new Robot();
	
	public void Move(int mode){
		if(mode == 1){
			while(main.potDegrees < 2.91){
				main.talKicker.set(-1);
			}
			main.talKicker.set(0);
			}
		
		else if(mode == 2){
			while(main.potDegrees < 2.5){
				main.talKicker.set(-1);
			}
			main.talKicker.set(0);
			}
		}
	
	
	public void manual(){
		if(main.camMode == 2)
    	{
    		if(main.cole.getRawButton(8) == false)
    		{
    			main.talKicker.set(0);
    		}
    	
    		else if(main.cole.getRawButton(8) == true)
    		{
    			main.talKicker.set(-1);
    		}
    	}
	}
}
