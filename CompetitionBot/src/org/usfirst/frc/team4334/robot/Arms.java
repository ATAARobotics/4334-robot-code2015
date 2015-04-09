package org.usfirst.frc.team4334.robot;

public class Arms {
	
	static double deadZ = 0.17;
	
	static int leftValue;
	static int rightValue;
	private static int setLeft(){
		if(Robot.miranda.getRawAxis(1) > deadZ){
			leftValue = 1;
		}
		
		else if(Robot.miranda.getRawAxis(1) < -deadZ){
			leftValue = -1;
		}
		
		else if(Robot.miranda.getRawAxis(1) > -deadZ && Robot.miranda.getRawAxis(1) < deadZ){
			leftValue = 0;
		}
		return leftValue;
	}
	private static int setRight(){
		if(Robot.miranda.getRawAxis(4) > deadZ){
			rightValue = 1;
		}
		
		else if(Robot.miranda.getRawAxis(4) < -deadZ){
			rightValue = -1;
		}
		
		else if(Robot.miranda.getRawAxis(4) > -deadZ && Robot.miranda.getRawAxis(4) < deadZ){
			rightValue = 0;
		}
		return rightValue;
	}
	
	public static void armMoters(){
		leftValue = setLeft();
		rightValue = setRight();
		if(leftValue == 0) 
    	{
    		Robot.talArmLeft.set(-(Robot.miranda.getRawAxis(4)));

    		Robot.talArmRight.set(-(Robot.miranda.getRawAxis(4)));
    	}

    	//If right thumbstick is still

    	if(rightValue == 0) 
    	{
    		Robot.talArmLeft.set(Robot.miranda.getRawAxis(1));

    		Robot.talArmRight.set(-Robot.miranda.getRawAxis(1));
    	}

    	//If left thumbstick is positive and right thumbstick is positive

    	if(leftValue == 1 && rightValue == 1) 
    	{
    		Robot.talArmLeft.set(Robot.miranda.getRawAxis(1) - (Robot.miranda.getRawAxis(4) * 0.9));

    		Robot.talArmRight.set(-(Robot.miranda.getRawAxis(1)));
    	}

    	//If left thumbstick is positive and right thumbstick is negative

    	if(leftValue == 1 && rightValue == -1) 
    	{
    		Robot.talArmLeft.set(Robot.miranda.getRawAxis(1));

    		Robot.talArmRight.set(-(Robot.miranda.getRawAxis(1) + (Robot.miranda.getRawAxis(4) * 0.9)));
    	}

    	//If left thumbstick is negative and right thumbstick is positive

    	if(leftValue == -1 && rightValue == 1) 
    	{
    		Robot.talArmLeft.set(Robot.miranda.getRawAxis(1) + (Robot.miranda.getRawAxis(4) * 0.9));

    		Robot.talArmRight.set(-(Robot.miranda.getRawAxis(1)));
    	}

    	//If left thumbstick is negative and right thumbstick is negative

    	if(leftValue == -1 && rightValue == -1) 
    	{
    		Robot.talArmLeft.set(Robot.miranda.getRawAxis(1));

    		Robot.talArmRight.set(-(Robot.miranda.getRawAxis(1) - (Robot.miranda.getRawAxis(4) * 0.9))); 	
    	}
	}
}
