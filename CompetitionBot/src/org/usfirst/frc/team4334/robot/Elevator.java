package org.usfirst.frc.team4334.robot;

import edu.wpi.first.wpilibj.SpeedController;

public class Elevator 
{
	public static void manualControl(SpeedController left, SpeedController right, boolean limitTop, boolean limitBottom, double trigLeft, double trigRight)
	{
		if(limitTop && limitBottom){
			left.set(trigRight-trigLeft);
			right.set(trigRight-trigLeft);
		}
		else if(limitBottom == false){
			left.set(trigLeft - (trigLeft*2));
			right.set(trigLeft - (trigLeft*2));
		}
		else if(limitTop == false){
			left.set(trigRight);
			right.set(trigRight);
		}
	}
}
