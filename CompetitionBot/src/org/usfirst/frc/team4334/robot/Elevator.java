package org.usfirst.frc.team4334.robot;

public class Elevator {
	
	Robot main = new Robot();
	Cam cam = new Cam();
	
	private void GoUp(double speed){
		main.elevatorMoter.set(-speed);
		main.elevatorMoter.set(-speed);
	}
	
	private void GoDown(double speed){
		main.elevatorMoter.set(speed);
		main.elevatorMoter2.set(speed);
	}
	public void manual(){
		if (main.limit2.get() == false || main.miranda.getRawAxis(2) < 0.01 && main.miranda.getRawAxis(3) < 0.01 || main.miranda.getRawAxis(2) > 0.01 && main.miranda.getRawAxis(3) > 0.01){
			main.elevatorMoter.set(0);
			main.elevatorMoter2.set(0);
		}
		else{
			if(main.miranda.getRawAxis(2) > 0.01){
				GoDown(main.miranda.getRawAxis(2));
			}
			else if(main.miranda.getRawAxis(3) > 0.01){
				GoUp(main.miranda.getRawAxis(3));
			}
		}
	}
	
	public void GotoSetpoint(int SetPoint){
		if(main.elevatorR > SetPoint){
			GoDown(0.6);
		}
		else{
			GoUp(0.6);
		}
		cam.Move(1);
	}
}
