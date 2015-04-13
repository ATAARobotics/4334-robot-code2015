package org.usfirst.frc.team4334.robot;

import edu.wpi.first.wpilibj.SpeedController;

//AUTHOR: Jayden Chan

public class Winch 
{
	public static void manualControl(double left, double right, SpeedController motor)
	{
		motor.set(right - left);
	}
	
}
