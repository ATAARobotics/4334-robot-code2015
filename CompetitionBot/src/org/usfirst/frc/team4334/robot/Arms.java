package org.usfirst.frc.team4334.robot;

public class Arms {
	Robot main = new Robot();
	
	double deadZ = 0.17;
	
	int leftValue;
	int rightValue;
	private int setLeft(){
		if(main.miranda.getRawAxis(1) > deadZ){
			leftValue = 1;
		}
		
		else if(main.miranda.getRawAxis(1) < -deadZ){
			leftValue = -1;
		}
		
		else if(main.miranda.getRawAxis(1) > -deadZ && main.miranda.getRawAxis(1) < deadZ){
			leftValue = 0;
		}
		return leftValue;
	}
	private int setRight(){
		if(main.miranda.getRawAxis(4) > deadZ){
			rightValue = 1;
		}
		
		else if(main.miranda.getRawAxis(4) < -deadZ){
			rightValue = -1;
		}
		
		else if(main.miranda.getRawAxis(4) > -deadZ && main.miranda.getRawAxis(4) < deadZ){
			rightValue = 0;
		}
		return rightValue;
	}
	
	public void armMoters(){
		leftValue = setLeft();
		rightValue = setRight();
		if(leftValue == 0) 
    	{
    		main.talArmLeft.set(-(main.miranda.getRawAxis(4)));

    		main.talArmRight.set(-(main.miranda.getRawAxis(4)));
    	}

    	//If right thumbstick is still

    	if(rightValue == 0) 
    	{
    		main.talArmLeft.set(main.miranda.getRawAxis(1));

    		main.talArmRight.set(-main.miranda.getRawAxis(1));
    	}

    	//If left thumbstick is positive and right thumbstick is positive

    	if(leftValue == 1 && rightValue == 1) 
    	{
    		main.talArmLeft.set(main.miranda.getRawAxis(1) - (main.miranda.getRawAxis(4) * 0.9));

    		main.talArmRight.set(-(main.miranda.getRawAxis(1)));
    	}

    	//If left thumbstick is positive and right thumbstick is negative

    	if(leftValue == 1 && rightValue == -1) 
    	{
    		main.talArmLeft.set(main.miranda.getRawAxis(1));

    		main.talArmRight.set(-(main.miranda.getRawAxis(1) + (main.miranda.getRawAxis(4) * 0.9)));
    	}

    	//If left thumbstick is negative and right thumbstick is positive

    	if(leftValue == -1 && rightValue == 1) 
    	{
    		main.talArmLeft.set(main.miranda.getRawAxis(1) + (main.miranda.getRawAxis(4) * 0.9));

    		main.talArmRight.set(-(main.miranda.getRawAxis(1)));
    	}

    	//If left thumbstick is negative and right thumbstick is negative

    	if(leftValue == -1 && rightValue == -1) 
    	{
    		main.talArmLeft.set(main.miranda.getRawAxis(1));

    		main.talArmRight.set(-(main.miranda.getRawAxis(1) - (main.miranda.getRawAxis(4) * 0.9))); 	
    	}
	}
}
