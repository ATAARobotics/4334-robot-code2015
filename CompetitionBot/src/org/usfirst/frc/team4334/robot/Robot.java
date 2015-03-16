/**
 * 		BINDS: 
 * 		
 * 		Joystick 1:
 * 
 * 		Select = Reset elevator encoders
 * 		Start = Cam Manual (in case pot breaks)
 * 		
 * 		Left Thumbstick - Forward/Backward
 * 		Right Thumbstick - Turn Left/Right
 * 
 * 		LB = Arms Toggle
 * 		RB = Cam Manual toggler
 * 
 * 		A = Stinger	
 * 		B = Switch Gears
 * 	
 *		Joystick 2:
 *
 *		Start = Cam manual (forward only)
 *
 *		LB = Arms Toggle
 *
 * 		A = Cam Setpoint Toggle
 * 		X = Elevator Low
 * 		Y = Elevator One Tote
 *		
 *		Triggers = Elevator Manual
 *
 *		Left Stick = Arm Motors In/Out
 *		Right Stick = Arm Motors L/R
 *
 */

//THIS IS COMPETITION BOT CODE

package org.usfirst.frc.team4334.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is the official code for ATA's 2015 robot: "Elevation".
 */

// © [COPYRIGHT] Alberta Tech Alliance 2015. All rights reserved.

public class Robot extends IterativeRobot {
    
    //This function is run when the robot is first started up and should be
    //used for any initialization code.
     	
    
	Joystick joy;
	Joystick joy2;
	
	CANTalon canFL;
    CANTalon canBL;
    CANTalon canFR;
    CANTalon canBR;
    CANTalon canWinch;
    CANTalon canWinch2;
    Talon talKicker;
    Talon talArmLeft;
    Talon talArmRight;
    
    Encoder encoderL;
	Encoder encoderR;
	Encoder encoderElevator;
	
	AnalogInput pot1;
	Compressor comp;
    DoubleSolenoid gearShift;
    DoubleSolenoid leftArm;
    DoubleSolenoid rightArm;
    DoubleSolenoid flipper;
    DigitalInput limit1;
    DigitalInput limit2;
    
    String gearPos, gearPos2;
    
    double leftThumb2,rightThumb2;
    double leftTrig,rightTrig;
    double leftTrig2,rightTrig2;
    double degrees, potDegrees;
	double leftThumb,rightThumb;
	double q;
	double turnRad;
	double deadZ, deadZ2;
	
    boolean stillPressed;
    boolean stillPressed2;
    boolean stillPressed3;
    boolean stillPressed4;
    boolean stillPressed5;
    boolean stillPressed6;
    boolean stillPressed7;
    boolean stillPressed8;
    boolean stillPressed9;
    boolean elevatorMax;
    boolean elevatorMin;
    boolean elevatorManual;
    boolean camSetPoint = false;
	boolean gotoSpot, gotoSpot2, gotoSpot3;
	boolean gotoCam1 = true;
	boolean gotoCam2 = false;
	boolean camChange = false;
	boolean camActivate = false;
	boolean goOnce;
	
	int camMode;
	int leftR, rightR, elevatorR;
	int case1, case2, case3;
	int autoMode;
	int session;
	int threeToteMode;
	
    public void robotInit()
    {
   
    canFL = new CANTalon(1);
	canBL = new CANTalon(2);
	canFR = new CANTalon(5);
    canBR = new CANTalon(6);
    canWinch = new CANTalon(3);
    canWinch2 = new CANTalon(4);
    talKicker = new Talon(0);
    talArmLeft = new Talon(1);
    talArmRight = new Talon(2);
    
    elevatorManual = false;
    camMode = 1;
   
    gearPos = "Gear Position:";
    
    joy = new Joystick(0);
    joy2 = new Joystick(1);
    
    comp  = new Compressor(0);
    comp.setClosedLoopControl(true);
    
    pot1 = new AnalogInput(0);  
    
    limit1 = new DigitalInput(3);
    limit2 = new DigitalInput(2);
    
    rightArm = new DoubleSolenoid(4, 5);
    rightArm.set(DoubleSolenoid.Value.kForward);
    leftArm = new DoubleSolenoid(6, 7);
    leftArm.set(DoubleSolenoid.Value.kForward);
    gearShift = new DoubleSolenoid(2, 3);
    gearShift.set(DoubleSolenoid.Value.kForward);
    flipper = new DoubleSolenoid(0, 1);
    flipper.set(DoubleSolenoid.Value.kReverse);
    
    encoderElevator = new Encoder(0, 1, true, EncodingType.k4X);
    encoderR = new Encoder(8, 9, true, EncodingType.k4X);
	encoderL = new Encoder(6, 7, true, EncodingType.k4X);
	encoderL.reset();
	encoderR.reset();
    encoderElevator.reset();
    autoMode = 6;
    goOnce = true;
    threeToteMode = 0;
    }

    
    
     //This function is called periodically [20 ms] during autonomous
    
    public void autonomousPeriodic()
    {
    	if(goOnce)
    	{
    		if(autoMode == 0)
    		{
    			goOnce = false;
    			Testing();
    		}
    		
    		if(autoMode == 1)
    		{
    			goOnce = false;
    			moveToZoneAuto();
    		}
    		
    		if(autoMode == 2)
    		{
    			goOnce = false;
    			oneToteAuto();
    		}
    		
    		if(autoMode == 3)
    		{
    			
    			goOnce = false;
    			oneBinAuto();
    			
    		}
    		
    		if(autoMode == 4)
    		{
    			goOnce = false;
    			nothingAuto();
    		}
    		
    		if(autoMode == 6)
    		{
    			goOnce = false;
    			binJackerAuto();     
    		}
    		
    		if(autoMode == 7)
    		{
    			
    		}
    	}
    }

    
     //This function is called periodically [20 ms] during operator control
    
	public void teleopPeriodic() 
    {
    	//cameraOn();
    	
		getSensors();
		
    	arcadeDrive();
    	
    	armMotors();
    	
    	elevator();
    		
    	buttonToggles();
    	
    	camFullManual();
    	
    	camSetpoint();
    	
    	elevatorOneTote();
    	
    	elevatorLow();
    	
    	smartDashboard();
    	
    	//cameraOff();
    	
    }

     //This function is called periodically during test mode
     
    public void testPeriodic() 
    {
    	
    }

//---------------------------------------------------------------------------------------------------------------------------------\\
   
    //Teleop methods
    
    public void smartDashboard()
    {
    	SmartDashboard.putNumber("Elevator Encoder", elevatorR); 
    	SmartDashboard.putNumber("Cam Potentiometer", potDegrees); 
    	SmartDashboard.putBoolean("High Limit Switch", elevatorMax);   
    	SmartDashboard.putBoolean("Low Limit Switch", elevatorMin);   
    	SmartDashboard.putString(gearPos, gearPos2);	
    }
    
    public void elevatorLow()
    {
    	if (joy2.getRawButton(3) == false) {stillPressed7 = false;}
    	
    	if (joy2.getRawButton(3) && (stillPressed7 == false))
    	{
    		gotoSpot2 = true;
    		gotoCam1 = false;
			gotoCam2 = true;
    		camActivate = true;
    		stillPressed7 = true;
    	}
    	
    	if (gotoSpot2)
    	{

    		leftArm.set(DoubleSolenoid.Value.kReverse);
			rightArm.set(DoubleSolenoid.Value.kReverse);
    		
    		if ((elevatorMin) && (elevatorR >= 1500))
    		{
    			canWinch.set(0.68);
    			canWinch2.set(0.68);
    		}
    		
    		else if((elevatorMin) && (elevatorR < 1500))
    		{
    			canWinch.set(0.3);
    			canWinch2.set(0.3);
    		}
    		
    		else 
    		{
    			gotoSpot2=false;
   			}
    	}
    }
    
    public void elevatorHigh()
    {
    	if (joy2.getRawButton(2) == false) {stillPressed9 = false;}
    	
    	if (joy2.getRawButton(2) && (stillPressed9 == false))
    	{
    		gotoSpot3 = true;
    		gotoCam1 = true;
			gotoCam2 = false;
    		camActivate = true;	
    		stillPressed9 = true;
    	}
    	
    	if (gotoSpot3)
    	{

    		leftArm.set(DoubleSolenoid.Value.kForward);
			rightArm.set(DoubleSolenoid.Value.kForward);
    		
    		if ((elevatorMax) && (elevatorR <= 13000))
    		{
    			canWinch.set(-0.8);
    			canWinch2.set(0.8);
    		}
    		else if((elevatorMax) && (elevatorR > 13000))
    		{
    			canWinch.set(-0.33);
    			canWinch2.set(0.33);
    		}
    		else {
    			gotoSpot2=false;
    		}
    	}
    }
    
    public void elevatorOneTote()
    {
    	if (joy2.getRawButton(4) == false) {stillPressed6 = false;}
    	
    	if (joy2.getRawButton(4) && (stillPressed6 == false))
    	{
    		gotoSpot = true;
    		gotoCam1 = true;
			gotoCam2 = false;
    		camActivate = true;
    		stillPressed6 = true;
    		
    	}
    	
    	if (gotoSpot)
    	{

    		leftArm.set(DoubleSolenoid.Value.kReverse);
			rightArm.set(DoubleSolenoid.Value.kReverse);
    		
    		if (elevatorR < 10500)
    		{
    			canWinch.set(-1);
    			canWinch2.set(-1);
    		}
    		else {
    			gotoSpot=false;
    		}
    	}
    }
    
    public void camFullManual()
    {
    	if(camMode == 2)
    	{
    		if(joy.getRawButton(8) == false)
    		{
    			talKicker.set(0);
    		}
    	
    		if(joy.getRawButton(8) == true)
    		{
    			talKicker.set(-1);
    		}

    	}
    }
    
    public void buttonToggles()
    
    {
    	//Smartdashboard gear position string changer
    	
    	if(gearShift.get() == DoubleSolenoid.Value.kForward)
    	{
    		gearPos2 = "High Gear";
    	}
    	if(gearShift.get() == DoubleSolenoid.Value.kReverse)
    	{
    		gearPos2 = "Low Gear";
    	}
    	
    	//Cam Setpoint Toggle
    	
    	if (joy2.getRawButton(1) == false) {stillPressed5 = false;}
    	
    	if (joy2.getRawButton(1) && (stillPressed5 == false))
    	{
    		if (gotoCam1)
			{
				gotoCam1 = false;
				
			}
    		else if (!gotoCam1)
    		{
    			gotoCam1 = true;
    		}
    		
    		camActivate = true;
    		stillPressed5 = true;
    	}
    
    	//Cam Mode Switching [RB]
    	
    	if (joy2.getRawButton(6) == false) {stillPressed8 = false;}
    	
    	if (joy2.getRawButton(6) && (stillPressed8 == false))
    	{
    		if (camMode == 1)
    			{
    				camMode = 2;
    			}
    		else if(camMode == 2)
    		{
    			camMode = 1;
    		}
    	}
    	
    	//Gear Shifting [Right Thumbstick Button]
    	
    	if (joy.getRawButton(2) == false) {stillPressed = false;}
    	
    	if (joy.getRawButton(2) && (stillPressed == false))
    	{	
    		if (gearShift.get() == DoubleSolenoid.Value.kForward)
    			{
    			gearShift.set(DoubleSolenoid.Value.kReverse);  		
    			stillPressed=true;
    			}
    		else
    		{
    			gearShift.set(DoubleSolenoid.Value.kForward);
    			stillPressed=true;
    		}
    	}
    	
    	//Arms Toggle [LB] Controller 1
    	
    	if (joy.getRawButton(5) == false) {stillPressed2 = false;}
    	
    	if (joy.getRawButton(5) && (stillPressed2 == false))
    	{
    		if ((leftArm.get() == DoubleSolenoid.Value.kForward) && (rightArm.get() == DoubleSolenoid.Value.kForward))
   			{
   				leftArm.set(DoubleSolenoid.Value.kReverse);  
   				rightArm.set(DoubleSolenoid.Value.kReverse);
   				stillPressed2=true;
   			}
    		else 
    		{
    			leftArm.set(DoubleSolenoid.Value.kForward);
    			rightArm.set(DoubleSolenoid.Value.kForward);
    			stillPressed2=true;
    		}
    	}
    	
    	//Arm Toggle [LB] Controller 2
    	if (joy2.getRawButton(5) == false) {stillPressed4 = false;}
		
    	if (joy2.getRawButton(5) && (stillPressed4 == false))
    	{
    		if ((leftArm.get() == DoubleSolenoid.Value.kForward) && (rightArm.get() == DoubleSolenoid.Value.kForward))
   			{
   				leftArm.set(DoubleSolenoid.Value.kReverse);  
   				rightArm.set(DoubleSolenoid.Value.kReverse);
   				stillPressed4=true;
   			}
    		else 
    		{
    			leftArm.set(DoubleSolenoid.Value.kForward);
    			rightArm.set(DoubleSolenoid.Value.kForward);
    			stillPressed4=true;
    		}
    	}
    	
    	//Stinger [RB]
    	
    		if (joy.getRawButton(1) == false) {stillPressed3 = false;}
    		
    		if (joy.getRawButton(1) && (stillPressed3 == false))
    		{
    			if (flipper.get() == DoubleSolenoid.Value.kForward)
    			{
    				flipper.set(DoubleSolenoid.Value.kReverse);  		
    				stillPressed3=true;
    			}
    			else
    			{
    				flipper.set(DoubleSolenoid.Value.kForward);
    				stillPressed3=true;
    			}
    		}	
    }
       
    public void camSetpoint()
    {
    	if ((camActivate) && (camMode == 1))
    	{
    		if(gotoCam1)
    		{

        		if (potDegrees < 3.117)
        		{
        			talKicker.set(-1);
        		}
     
        		else 
        		{
        			talKicker.set(0);
        			camActivate=false;
        		}
    		}
    		
    		if(!gotoCam1)
    		{

    			if (potDegrees > 2.72)
        		{
        			talKicker.set(1);
        		}
        		
        		else 
        		{
        			talKicker.set(0);
        			camActivate=false;
        		}
    		}
    		
    	}
    }
    
    public void arcadeDrive()
    {
    	q = (joy.getRawAxis(4));
    	
    	leftThumb = -(joy.getRawAxis(1));
    	
    	rightThumb = q;
     	
    	deadZ = 0.25;
    	
    	turnRad = 0.76;
    	
    	//If left thumbstick is still
    	
    	if((leftThumb < deadZ) && (leftThumb > -deadZ))
    	{
    		canFL.set(-(rightThumb * turnRad));
    		canBL.set(-(rightThumb * turnRad));
    		
    		canBR.set(-(rightThumb * turnRad));
    		canFR.set(-(rightThumb * turnRad));
    	}
    	
    	//If right thumbstick is still
    	
    	if((rightThumb < deadZ) && (rightThumb > -deadZ))
    	{
    		canFL.set(-leftThumb);
    		canBL.set(-leftThumb);
    		
    		canBR.set(leftThumb);
    		canFR.set(leftThumb);
    	}
    	
    	//If both thumbsticks are positive
    	
    	if((leftThumb > deadZ) && (rightThumb > deadZ))
    	{
    		canFL.set(-leftThumb);
    		canBL.set(-leftThumb);
    		
    		canBR.set(leftThumb - (rightThumb * turnRad));
    		canFR.set(leftThumb - (rightThumb * turnRad));
    	}
    	
    	//If left thumbstick is positive and right thumbstick is negative
    	
    	if((leftThumb > deadZ) && (rightThumb < -deadZ))
    	{
    		canFL.set(-(leftThumb + (rightThumb * turnRad)));
    		canBL.set(-(leftThumb + (rightThumb * turnRad)));
		
    		canBR.set(leftThumb);
    		canFR.set(leftThumb);
    	}
    	
    	//If left thumbstick is negative and right thumbstick is positive
    	
    	if((leftThumb < -deadZ) && (rightThumb > deadZ))
    	{
    		canFL.set(-(leftThumb + (rightThumb * turnRad)));
    		canBL.set(-(leftThumb + (rightThumb * turnRad)));
    		
    		canBR.set(leftThumb);
    		canFR.set(leftThumb);
    	}
    	
    	//If left thumbstick is negative and right thumbstick is negative
    	
    	if((leftThumb < -deadZ) && (rightThumb < -deadZ))
    	{
    		canFL.set(-leftThumb);
    		canBL.set(-leftThumb);
    		
    		canBR.set(leftThumb - (rightThumb * turnRad));
    		canFR.set(leftThumb - (rightThumb * turnRad));
    	}

    }
       
    public void armMotors()
    {
    	//Arm motors
    	
		leftThumb2=(joy2.getRawAxis(1));
    	rightThumb2=(joy2.getRawAxis(4));
    	
    	deadZ2 = 0.17;
    	
    	//If left thumbstick is still

    	if((leftThumb2>-deadZ2) && (leftThumb2<deadZ2)) 
    	{
    		talArmLeft.set(-(rightThumb2));

    		talArmRight.set(-(rightThumb2));
    	}

    	//If right thumbstick is still

    	if((rightThumb2>-deadZ2) && (rightThumb2<deadZ2)) 
    	{
    		talArmLeft.set(leftThumb2);

    		talArmRight.set(-leftThumb2);
    	}

    	//If left thumbstick is positive and right thumbstick is positive

    	if((leftThumb2>deadZ2) && (rightThumb2>deadZ2)) 
    	{
    		talArmLeft.set(leftThumb2 - (rightThumb2 * 0.9));

    		talArmRight.set(-(leftThumb2));
    	}

    	//If left thumbstick is positive and right thumbstick is negative

    	if((leftThumb2>deadZ2) && (rightThumb2<-deadZ2)) 
    	{
    		talArmLeft.set(leftThumb2);

    		talArmRight.set(-(leftThumb2 + (rightThumb2 * 0.9)));
    	}

    	//If left thumbstick is negative and right thumbstick is positive

    	if((leftThumb2<-deadZ2) && (rightThumb2>deadZ2)) 
    	{
    		talArmLeft.set(leftThumb2 + (rightThumb2 * 0.9));

    		talArmRight.set(-(leftThumb2));
    	}

    	//If left thumbstick is negative and right thumbstick is negative

    	if((leftThumb2<-deadZ2) && (rightThumb2<-deadZ2)) 
    	{
    		talArmLeft.set(leftThumb2);

    		talArmRight.set(-(leftThumb2 - (rightThumb2 * 0.9))); 	
    	}
    }
       
    public void elevator()
    	
    {
    	
    	//Elevator Motors [Y = Up B = Down]
    	
     		if((joy2.getRawAxis(3) > 0.09) && (joy2.getRawAxis(2) > 0.09))
        	{
        		canWinch.set(0);
        		canWinch2.set(0);
        	}
     		
     		if((joy2.getRawAxis(3) < 0.09) && (joy2.getRawAxis(2) < 0.09))
        	{
        		canWinch.set(0);
        		canWinch2.set(0);
        	}
        	
        	if((joy2.getRawAxis(3) > 0.09) && (joy2.getRawAxis(2) < 0.09) && (elevatorMax == true))
        	{
        		elevatorManual = true;
        		gotoSpot=false;
        		canWinch.set(-(joy2.getRawAxis(3)));
        		canWinch2.set(-(joy2.getRawAxis(3)));
        	}
        	
        	if((joy2.getRawAxis(3) < 0.1) && (joy2.getRawAxis(2) > 0.1) && (elevatorMin == true))
        	{
        		elevatorManual = true;
        		gotoSpot=false;
        		canWinch.set(joy2.getRawAxis(2));
        		canWinch2.set(joy2.getRawAxis(2));
        }
    	
    	if(joy.getRawButton(7) == true)
    	{
    		encoderElevator.reset();
    	}
    }

//----------------------------------------------------------------------------------------------------------------------------------\\
   
    //Auto Mode methods  
    
    public void getSensors()
    {
    	leftR = Math.abs(encoderR.get());
    	rightR = Math.abs(encoderL.get());
    	elevatorR = encoderElevator.get();
    	potDegrees = pot1.getVoltage();
    	elevatorMin = limit2.get();
    	elevatorMax = limit1.get();
    	
    	SmartDashboard.putNumber("Left Encoder", leftR);
    	SmartDashboard.putNumber("Right Encoder", rightR);
    }
    
    public void drive(int distance, double power)
    {
    	canFL.set(-power);
		canBL.set(-power);
		
		canBR.set(power);
		canFR.set(power);
		
		try {
			Thread.sleep(distance);
		} catch (InterruptedException e) {
			
			Thread.currentThread().interrupt();
		}
    	
    	canFL.set(0);
		canBL.set(0);
	
    }
    
    public void armsClose()
    {
    	leftArm.set(DoubleSolenoid.Value.kForward);  
		rightArm.set(DoubleSolenoid.Value.kForward);
    }
    
    public void armsOpen()
    {
    	leftArm.set(DoubleSolenoid.Value.kReverse);  
		rightArm.set(DoubleSolenoid.Value.kReverse);
    }
    
    public void leftTurn90(double power)
    {
    	while(rightR < 730)
    	{
    		getSensors();
    		
    		canFL.set(power);
    		canBL.set(power);
    		
    		canBR.set(power);
    		canFR.set(power);
    	}
    	
    	canFL.set(0);
		canBL.set(0);
		
		canBR.set(0);
		canFR.set(0);
    	
    	encoderR.reset();
    	encoderL.reset();
    }
    
    public void rightTurn90(double power)
    {
    	while(rightR < 730)
    	{
    		getSensors();
    		
    		canFL.set(-power);
    		canBL.set(-power);
    		
    		canBR.set(-power);
    		canFR.set(-power);
    	}
    	
    	canFL.set(0);
		canBL.set(0);
		
		canBR.set(0);
		canFR.set(0);
    	
    	encoderR.reset();
    	encoderL.reset();
    }
    
    public void leftTurn45(double power)
    {
    	while(rightR < 365)
    	{
    		getSensors();
    		
    		canFL.set(power);
    		canBL.set(power);
    		
    		canBR.set(power);
    		canFR.set(power);
    	}
    	
    	canFL.set(0);
		canBL.set(0);
		
		canBR.set(0);
		canFR.set(0);
    	
    	encoderR.reset();
    	encoderL.reset();
    }
    
    public void rightTurn45(double power)
    {
    	while(rightR < 365)
    	{
    		getSensors();
    		
    		canFL.set(-power);
    		canBL.set(-power);
    		
    		canBR.set(-power);
    		canFR.set(-power);
    	}
    	
    	canFL.set(0);
		canBL.set(0);
		
		canBR.set(0);
		canFR.set(0);
    	
    	encoderR.reset();
    	encoderL.reset();
    }
    
    public void antiCoast()
    {
    	for(int i = 0; i < 3; i++)
    	{
    		canFL.set(-1);
    		canBL.set(-1);
    		
    		canBR.set(1);
    		canFR.set(1);
    	}
    	
    	canFL.set(0);
		canBL.set(0);
		
		canBR.set(0);
		canFR.set(0);
    	
		encoderR.reset();
    	encoderL.reset();
		
    }

    public void moveArms(int loopCount, int power)
    {
    	for(int i = 0; i < loopCount; i++)
    	{
    		talArmLeft.set(power);
    		talArmRight.set(-power);
    	}
    	
    	talArmLeft.set(0);
		talArmRight.set(0);
    }
    
    public void stingerOut()
    {
    	flipper.set(DoubleSolenoid.Value.kForward);
    }
    
    public void stingerIn()
    {
    	flipper.set(DoubleSolenoid.Value.kReverse);
    }
    
//----------------------------------------------------------------------------------------------------------------------------------\\
    
    //Auto Modes
    
    public void Testing()
    {
    	
    }
    
    public void moveToZoneAuto()
    {
    	drive(400, -0.5);
    }
    
    public void oneToteAuto()
    {
    	armsClose();
    	
    	rightTurn90(0.7);
    	
    	drive(1800, 0.5);
    	
    	moveArms(5, 1);
    	
    	armsOpen();
    }
    
    public void oneBinAuto()
    {
    	armsClose();
    	
    	leftTurn90(0.8);
    	
    	drive(1800, 0.6);
    	
    	moveArms(5, 1);
    	
    	armsOpen();
    }
    
    public void nothingAuto()
    {
    	
    }
    
    public void threeToteAuto()
    {
    	if(threeToteMode == 0)
    	{
    		gotoSpot = true;
    		gotoCam1 = true;
			gotoCam2 = false;
    		camActivate = true;
    		threeToteMode = 1;
    	}
    	
    	if(threeToteMode == 1)
    	{
    		rightTurn45(0.7);
    		threeToteMode = 2;
    	}
    	
    	if(threeToteMode == 2)
    	{
    		drive(400, 0.5);
    		threeToteMode = 3;
    	}
    	
    	if(threeToteMode == 3)
    	{
    		leftTurn90(0.7);
    		threeToteMode = 4;
    	}
    	
    	if(threeToteMode == 4)
    	{
    		drive(550, 0.5);
    		threeToteMode = 5;
    	}
    	
    	if(threeToteMode == 5)
    	{
    		armsClose();	
    		moveArms(5, 1);
    		threeToteMode = 6;
    	}
    	
    	if(threeToteMode == 6)
    	{
    		gotoSpot = true;
    		gotoCam1 = true;
			gotoCam2 = false;
    		camActivate = true;
    		rightTurn90(0.7);
    		threeToteMode = 7;
    	}
    	
    	if(threeToteMode == 7)
    	{
    		drive(400, 0.5);
    		threeToteMode = 8;
    	}
    	
    	if(threeToteMode == 8)
    	{
    		leftTurn90(0.7);
    		threeToteMode = 9;
    	}
    	
    	if(threeToteMode == 9)
    	{
    		drive(550, 0.5);
    		threeToteMode = 10;
    	}
    	
    	if(threeToteMode == 10)
    	{
    		armsClose();
    		moveArms(5, 1);
    		threeToteMode = 11;
    	}
    	
    	if(threeToteMode == 11)
    	{
    		gotoSpot = true;
    		gotoCam1 = true;
			gotoCam2 = false;
    		camActivate = true;
    		rightTurn90(0.7);
    		threeToteMode = 12;
    	}
    			
    }
 
    public void binJackerAuto()
    {
    	canFL.set(0.7);
		canBL.set(0.7);
		
		canBR.set(-0.7);
		canFR.set(-0.7);
    	
		try {
			Thread.sleep(598);
		} catch (InterruptedException e) {
			
			Thread.currentThread().interrupt();
		}
		
		canFL.set(0);
		canBL.set(0);
		
		canBR.set(0);
		canFR.set(0);
		
		try {
			Thread.sleep(1200);
		} catch (InterruptedException e) {
			
			Thread.currentThread().interrupt();
		}
		
    	stingerOut();
    	
    	try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			
			Thread.currentThread().interrupt();
		}
    	
    	canFL.set(-1);
		canBL.set(-1);
		
		canBR.set(1);
		canFR.set(1);
		
		try {
			Thread.sleep(800);
		} catch (InterruptedException e) {
			
			Thread.currentThread().interrupt();
		}
		
		canFL.set(0);
		canBL.set(0);
		
		canBR.set(0);
		canFR.set(0);
    	
		try {
			Thread.sleep(700);
		} catch (InterruptedException e) {
			
			Thread.currentThread().interrupt();
		}
		
    	stingerIn();
    }
    
}
