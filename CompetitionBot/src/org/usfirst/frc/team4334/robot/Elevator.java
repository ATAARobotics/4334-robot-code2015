package org.usfirst.frc.team4334.robot;

import edu.wpi.first.wpilibj.SpeedController;

public class Elevator 
{
	public static void manualControl(SpeedController left, SpeedController right, boolean limitTop, boolean limitBottom, double trigLeft, double trigRight)
	{
		if((limitTop) && (limitBottom))
		{
			Robot.elevatorManual = true;
			Robot.gotoSpot=false;
			Robot.gotoSpot2 = false;
			Robot.gotoSpot3 = false;
			left.set(trigRight - trigLeft);
			right.set(trigRight - trigLeft);
		}
		else
		{
			left.set(0);
			right.set(0);
		}
	}
}
