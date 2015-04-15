package org.usfirst.frc.team4334.robot;

import edu.wpi.first.wpilibj.SpeedController;

//AUTHOR: Jayden Chan

public class Drivetrain 
{
	public static void getArcade(SpeedController FR, SpeedController FL, SpeedController BR, SpeedController BL, 
								double rightThumb, double leftThumb, double speedMultiplier, double turnRad)
	{
		if((leftThumb == 0) && (rightThumb == 0))
		{
			FL.set(0);
    		BL.set(0);
    		
    		BR.set(0);
    		FR.set(0);
		}
		
		if((leftThumb == 0) && (rightThumb != 0))
		{
			FR.set(-((rightThumb * turnRad) * speedMultiplier));
			BR.set(-((rightThumb * turnRad) * speedMultiplier));
			
			FL.set(-((rightThumb * turnRad) * speedMultiplier));
			BL.set(-((rightThumb * turnRad) * speedMultiplier));
		}
		
		if((rightThumb == 0) && (leftThumb != 0))
		{
			FL.set(((-leftThumb)) * speedMultiplier);
    		BL.set(((-leftThumb)) * speedMultiplier);
    		
    		BR.set(((leftThumb)) * speedMultiplier);
    		FR.set(((leftThumb)) * speedMultiplier);
		}
		
		if((rightThumb > 0) && (leftThumb > 0))
		{
			FL.set((-leftThumb) * speedMultiplier);
    		BL.set((-leftThumb) * speedMultiplier);
    		
    		BR.set(((leftThumb - (rightThumb * turnRad))) * speedMultiplier);
    		FR.set(((leftThumb - (rightThumb * turnRad))) * speedMultiplier);
		}
		
		if((leftThumb > 0) && (rightThumb < 0))
		{
			FL.set(((-(leftThumb + (rightThumb * turnRad)))) * speedMultiplier);
    		BL.set(((-(leftThumb + (rightThumb * turnRad)))) * speedMultiplier);
		
    		BR.set(((leftThumb)) * speedMultiplier);
    		FR.set(((leftThumb)) * speedMultiplier);
		}
		
		if((leftThumb < 0) && (rightThumb > 0))
		{
			FL.set(((-(leftThumb + (rightThumb * turnRad)))) * speedMultiplier);
    		BL.set(((-(leftThumb + (rightThumb * turnRad)))) * speedMultiplier);
    		
    		BR.set(((leftThumb)) * speedMultiplier);
    		FR.set(((leftThumb)) * speedMultiplier);
		}
		
		if((leftThumb < 0) && (rightThumb < 0))
		{
			FL.set(((-(leftThumb + (rightThumb * turnRad)))) * speedMultiplier);
    		BL.set(((-(leftThumb + (rightThumb * turnRad)))) * speedMultiplier);
    		
    		BR.set(((leftThumb)) * speedMultiplier);
    		FR.set(((leftThumb)) * speedMultiplier);
		}
	}
	
	public static void getArcadeV2(SpeedController FR, SpeedController FL, SpeedController BR, SpeedController BL, 
			double rightThumb, double leftThumb, double speedMultiplier, double turnRad)
	{
		
		if(leftThumb != 0)
		{
				FL.set(-((leftThumb + (0.5 * (rightThumb * turnRad)) * speedMultiplier)));
	    		BL.set(-((leftThumb + (0.5 * (rightThumb * turnRad)) * speedMultiplier)));
	    		
	    		BR.set((leftThumb - (0.5 * (rightThumb * turnRad)) * speedMultiplier));
	    		FR.set((leftThumb - (0.5 * (rightThumb * turnRad)) * speedMultiplier));
		}
		
		else
		{
			FL.set(-(rightThumb * speedMultiplier));
	   		BL.set(-(rightThumb * speedMultiplier));
	    		
	    	BR.set(-(rightThumb * speedMultiplier));
	   		FR.set(-(rightThumb * speedMultiplier));
		
		}
	}
	
}
