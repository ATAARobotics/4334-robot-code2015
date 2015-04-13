package org.usfirst.frc.team4334.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Arms 
{
	
	public static void toggle(DoubleSolenoid left, DoubleSolenoid right)
	{
		if ((left.get() == DoubleSolenoid.Value.kForward) && (right.get() == DoubleSolenoid.Value.kForward))
		{
			left.set(DoubleSolenoid.Value.kReverse);  
			right.set(DoubleSolenoid.Value.kReverse);
		}
		else 
		{
			left.set(DoubleSolenoid.Value.kForward);
			right.set(DoubleSolenoid.Value.kForward);
		}
	}
	
	public static void open(DoubleSolenoid left, DoubleSolenoid right)
	{
		left.set(DoubleSolenoid.Value.kReverse);  
		right.set(DoubleSolenoid.Value.kReverse);
	}
	
	public static void close(DoubleSolenoid left, DoubleSolenoid right)
	{
		left.set(DoubleSolenoid.Value.kForward);  
		right.set(DoubleSolenoid.Value.kForward);
	}
}
