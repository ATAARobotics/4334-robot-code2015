package org.usfirst.frc.team4334.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

//AUTHOR: Jayden Chan

public class Solenoid 
{
	public static void toggle(DoubleSolenoid piston)
	{
		if(piston.get() == DoubleSolenoid.Value.kForward)
		{
			piston.set(DoubleSolenoid.Value.kReverse);
		}
		else
		{
			piston.set(DoubleSolenoid.Value.kForward);
		}
	}
	
}
