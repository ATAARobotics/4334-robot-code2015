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
    		canFL.set(((-(RightThumb * TurnRad))) * SpeedMultiplier);
    		canBL.set(((-(RightThumb * TurnRad))) * SpeedMultiplier);
    		
    		canBR.set(((-(RightThumb * TurnRad))) * SpeedMultiplier);
    		canFR.set(((-(RightThumb * TurnRad))) * SpeedMultiplier);
    	}
    	
    	//If right thumbstick is still
    	
    	if(RightThumb == 0)
    	{
    		canFL.set(((-LeftThumb)) * SpeedMultiplier);
    		canBL.set(((-LeftThumb)) * SpeedMultiplier);
    		
    		canBR.set(((LeftThumb)) * SpeedMultiplier);
    		canFR.set(((LeftThumb)) * SpeedMultiplier);
    	}
    	
    	//If both thumbsticks are positive
    	
    	if(LeftThumb == 1 && RightThumb == 1)
    	{
    		canFL.set(((-LeftThumb)) * SpeedMultiplier);
    		canBL.set(((-LeftThumb)) * SpeedMultiplier);
    		
    		canBR.set(((LeftThumb - (RightThumb * TurnRad))) * SpeedMultiplier);
    		canFR.set(((LeftThumb - (RightThumb * TurnRad))) * SpeedMultiplier);
    	}
    	
    	//If left thumbstick is positive and right thumbstick is negative
    	
    	if(LeftThumb == 1 && RightThumb == -1)
    	{
    		canFL.set(((-(LeftThumb + (RightThumb * TurnRad)))) * SpeedMultiplier);
    		canBL.set(((-(LeftThumb + (RightThumb * TurnRad)))) * SpeedMultiplier);
		
    		canBR.set(((LeftThumb)) * SpeedMultiplier);
    		canFR.set(((LeftThumb)) * SpeedMultiplier);
    	}
    	
    	//If left thumbstick is negative and right thumbstick is positive
    	
    	if(LeftThumb == -1 && RightThumb == 1)
    	{
    		canFL.set(((-(LeftThumb + (RightThumb * TurnRad)))) * SpeedMultiplier);
    		canBL.set(((-(LeftThumb + (RightThumb * TurnRad)))) * SpeedMultiplier);
    		
    		canBR.set(((LeftThumb)) * SpeedMultiplier);
    		canFR.set(((LeftThumb)) * SpeedMultiplier);
    	}
    	
    	//If left thumbstick is negative and right thumbstick is negative
    	
    	if(LeftThumb == -1 && RightThumb == -1)
    	{
    		canFL.set(((-LeftThumb)) * SpeedMultiplier);
    		canBL.set(((-LeftThumb)) * SpeedMultiplier);
    		
    		canBR.set(((LeftThumb - (RightThumb * TurnRad))) * SpeedMultiplier);
    		canFR.set(((LeftThumb - (RightThumb * TurnRad))) * SpeedMultiplier);
    	}
    	else{
    		System.out.println("There was a problem (sent from Drive.class)");
    	}
	}
}
