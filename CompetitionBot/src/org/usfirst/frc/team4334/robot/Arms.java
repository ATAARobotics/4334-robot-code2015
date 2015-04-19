package org.usfirst.frc.team4334.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Arms 
{
	static DoubleSolenoid left;
	static DoubleSolenoid right;
	public Arms(DoubleSolenoid Left, DoubleSolenoid Right){
		left = Left;
		right = Right;
	}
	
	public void toggle()
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
	
	public void open()
	{
		left.set(DoubleSolenoid.Value.kReverse);  
		right.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void close()
	{
		left.set(DoubleSolenoid.Value.kForward);  
		right.set(DoubleSolenoid.Value.kForward);
	}
	
	public void armMotors()
	    {
	    	//Arm motors
	    	
	    	//Assign xbox values to variables
	    	
			Robot.leftThumb2 = (Robot.joy2.getRawAxis(1));
	    	Robot.rightThumb2 = (Robot.joy2.getRawAxis(4));
	    	
	    	Robot.deadZ2 = 0.17;
	    	
	    	//If left thumbstick is still

	    	if((Robot.leftThumb2>-Robot.deadZ2) && (Robot.leftThumb2<Robot.deadZ2)) 
	    	{
	    		Robot.talArmLeft.set(-(Robot.rightThumb2));

	    		Robot.talArmRight.set(-(Robot.rightThumb2));
	    	}

	    	//If right thumbstick is still

	    	if((Robot.rightThumb2>-Robot.deadZ2) && (Robot.rightThumb2<Robot.deadZ2)) 
	    	{
	    		Robot.talArmLeft.set(Robot.leftThumb2);

	    		Robot.talArmRight.set(-Robot.leftThumb2);
	    	}

	    	//If left thumbstick is positive and right thumbstick is positive

	    	if((Robot.leftThumb2>Robot.deadZ2) && (Robot.rightThumb2>Robot.deadZ2)) 
	    	{
	    		Robot.talArmLeft.set(Robot.leftThumb2 - (Robot.rightThumb2 * 0.9));

	    		Robot.talArmRight.set(-(Robot.leftThumb2));
	    	}

	    	//If left thumbstick is positive and right thumbstick is negative

	    	if((Robot.leftThumb2>Robot.deadZ2) && (Robot.rightThumb2<-Robot.deadZ2)) 
	    	{
	    		Robot.talArmLeft.set(Robot.leftThumb2);

	    		Robot.talArmRight.set(-(Robot.leftThumb2 + (Robot.rightThumb2 * 0.9)));
	    	}

	    	//If left thumbstick is negative and right thumbstick is positive

	    	if((Robot.leftThumb2<-Robot.deadZ2) && (Robot.rightThumb2>Robot.deadZ2)) 
	    	{
	    		Robot.talArmLeft.set(Robot.leftThumb2 + (Robot.rightThumb2 * 0.9));

	    		Robot.talArmRight.set(-(Robot.leftThumb2));
	    	}

	    	//If left thumbstick is negative and right thumbstick is negative

	    	if((Robot.leftThumb2<-Robot.deadZ2) && (Robot.rightThumb2<-Robot.deadZ2)) 
	    	{
	    		Robot.talArmLeft.set(Robot.leftThumb2);

	    		Robot.talArmRight.set(-(Robot.leftThumb2 - (Robot.rightThumb2 * 0.9))); 	
	    	}
	    }
	
}
