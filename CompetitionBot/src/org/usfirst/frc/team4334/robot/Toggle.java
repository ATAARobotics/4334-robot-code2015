package org.usfirst.frc.team4334.robot;

public class Toggle 
{
	boolean pressed;
	public void button(boolean butoon, Runnable run){
		if(butoon == false){
			pressed = false;
		}
		if(butoon && pressed == false)
		{
			pressed = true;
			run.run();
		}
	}
}
