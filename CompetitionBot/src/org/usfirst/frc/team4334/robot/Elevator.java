package org.usfirst.frc.team4334.robot;

import edu.wpi.first.wpilibj.SpeedController;

public class Elevator {
	
	public Elevator(SpeedController left, SpeedController right)
	{
		
	}
	
	private static void GoUp(double speed){
		
		Robot.elevatorMoter.set(-speed);
		Robot.elevatorMoter.set(-speed);
	}
	
	private static void GoDown(double speed){
		Robot.elevatorMoter.set(speed);
		Robot.elevatorMoter2.set(speed);
	}
	public static void manual(){
		if (Robot.limit2.get() == false || Robot.miranda.getRawAxis(2) < 0.01 && Robot.miranda.getRawAxis(3) < 0.01 || Robot.miranda.getRawAxis(2) > 0.01 && Robot.miranda.getRawAxis(3) > 0.01){
			Robot.elevatorMoter.set(0);
			Robot.elevatorMoter2.set(0);
		}
		else{
			if(Robot.miranda.getRawAxis(2) > 0.01){
				GoDown(Robot.miranda.getRawAxis(2));
			}
			else if(Robot.miranda.getRawAxis(3) > 0.01){
				GoUp(Robot.miranda.getRawAxis(3));
			}
		}
	}
	
	public static void GotoSetpoint(int SetPoint){
		if(Robot.elevatorR > SetPoint){
			GoDown(0.6);
		}
		else{
			GoUp(0.6);
		}
		Cam.Move(1);
	}
}
