package org.usfirst.frc.team4334.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedController;

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
	
	public static void getArcadeArms(SpeedController right, SpeedController left, double rightThumb, double leftThumb, double speedMultiplier, double turnRad)
	{
		
		if(leftThumb != 0)
		{
			left.set(-((leftThumb + (0.5 * (rightThumb * turnRad)) * speedMultiplier)));
	    		
	    	right.set((leftThumb - (0.5 * (rightThumb * turnRad)) * speedMultiplier));
		}
		
		else
		{
			left.set(-(rightThumb * speedMultiplier));
	    		
	    	right.set(-(rightThumb * speedMultiplier));
		
		}
	}
}
