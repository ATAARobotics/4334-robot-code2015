package org.usfirst.frc.team4334.robot;

import edu.wpi.first.wpilibj.CANTalon;

public class Drive {
	static double DeadZone = 0.25;
	static double SpeedMultiplier = 1;
	static double TurnRad = 0.74;
	
	public static CANTalon canFL = new CANTalon(1); 
	public static CANTalon canBL = new CANTalon(2);
	public static CANTalon canFR = new CANTalon(5);
    public static CANTalon canBR = new CANTalon(6);
	
	private static int LeftValue(){
		int value = 2;
		
		if(-Robot.cole.getRawAxis(1) >=-DeadZone && -Robot.cole.getRawAxis(1) <= DeadZone){
			value = 0;
		}
		else if(-Robot.cole.getRawAxis(1) < -DeadZone){
			value = -1;
		}
		else if(-Robot.cole.getRawAxis(1) > DeadZone){
			value = 1;
		}
		
		return value;
	}
	private static int RightValue(){
		int value = 2;
		
		if(-Robot.cole.getRawAxis(4) >=-DeadZone && -Robot.cole.getRawAxis(4) <= DeadZone){
			value = 0;
		}
		else if(-Robot.cole.getRawAxis(4) < -DeadZone){
			value = -1;
		}
		else if(-Robot.cole.getRawAxis(4) > DeadZone){
			value = 1;
		}
		
		return value;
	}
	
	public static void drive(){
		int LeftThumb = LeftValue();
		int RightThumb = RightValue();
		
		//If left thumbstick is still
		
		if(LeftThumb == 0)
    	{
    		canFL.set(((-(Robot.cole.getRawAxis(4) * TurnRad))) * SpeedMultiplier);
    		canBL.set(((-(Robot.cole.getRawAxis(4) * TurnRad))) * SpeedMultiplier);
    		
    		canBR.set(((-(Robot.cole.getRawAxis(4) * TurnRad))) * SpeedMultiplier);
    		canFR.set(((-(Robot.cole.getRawAxis(4) * TurnRad))) * SpeedMultiplier);
    	}
    	
    	//If right thumbstick is still
    	
    	if(RightThumb == 0)
    	{
    		canFL.set(((-Robot.cole.getRawAxis(1))) * SpeedMultiplier);
    		canBL.set(((-Robot.cole.getRawAxis(1))) * SpeedMultiplier);
    		
    		canBR.set(((Robot.cole.getRawAxis(1))) * SpeedMultiplier);
    		canFR.set(((Robot.cole.getRawAxis(1))) * SpeedMultiplier);
    	}
    	
    	//If both thumbsticks are positive
    	
    	if(LeftThumb == 1 && RightThumb == 1)
    	{
    		canFL.set(((-Robot.cole.getRawAxis(1))) * SpeedMultiplier);
    		canBL.set(((-Robot.cole.getRawAxis(1))) * SpeedMultiplier);
    		
    		canBR.set(((Robot.cole.getRawAxis(1) - (Robot.cole.getRawAxis(4) * TurnRad))) * SpeedMultiplier);
    		canFR.set(((Robot.cole.getRawAxis(1) - (Robot.cole.getRawAxis(4) * TurnRad))) * SpeedMultiplier);
    	}
    	
    	//If left thumbstick is positive and right thumbstick is negative
    	
    	if(LeftThumb == 1 && RightThumb == -1)
    	{
    		canFL.set(((-(Robot.cole.getRawAxis(1) + (Robot.cole.getRawAxis(4) * TurnRad)))) * SpeedMultiplier);
    		canBL.set(((-(Robot.cole.getRawAxis(1) + (Robot.cole.getRawAxis(4) * TurnRad)))) * SpeedMultiplier);
		
    		canBR.set(((Robot.cole.getRawAxis(1))) * SpeedMultiplier);
    		canFR.set(((Robot.cole.getRawAxis(1))) * SpeedMultiplier);
    	}
    	
    	//If left thumbstick is negative and right thumbstick is positive
    	
    	if(LeftThumb == -1 && RightThumb == 1)
    	{
    		canFL.set(((-(Robot.cole.getRawAxis(1) + (Robot.cole.getRawAxis(4) * TurnRad)))) * SpeedMultiplier);
    		canBL.set(((-(Robot.cole.getRawAxis(1) + (Robot.cole.getRawAxis(4) * TurnRad)))) * SpeedMultiplier);
    		
    		canBR.set(((Robot.cole.getRawAxis(1))) * SpeedMultiplier);
    		canFR.set(((Robot.cole.getRawAxis(1))) * SpeedMultiplier);
    	}
    	
    	//If left thumbstick is negative and right thumbstick is negative
    	
    	if(LeftThumb == -1 && RightThumb == -1)
    	{
    		canFL.set(((-Robot.cole.getRawAxis(1))) * SpeedMultiplier);
    		canBL.set(((-Robot.cole.getRawAxis(1))) * SpeedMultiplier);
    		
    		canBR.set(((Robot.cole.getRawAxis(1) - (Robot.cole.getRawAxis(4) * TurnRad))) * SpeedMultiplier);
    		canFR.set(((Robot.cole.getRawAxis(1) - (Robot.cole.getRawAxis(4) * TurnRad))) * SpeedMultiplier);
    	}
    	else{
    		System.out.println("There was a problem (sent from Drive.class)");
    	}
	}
}
