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

import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PIDController;

/**
 * This is the official code for ATA's 2015 robot: "Elevation".
 */

// [COPYRIGHT] Alberta Tech Alliance 2015. All rights reserved.

public class Robot extends IterativeRobot 
{
    
    //This function is run when the robot is first started up and should be
    //used for any initialization code.
     	
    Preferences prefs;
   
	public Joystick joy;	//Xbox controllers
	public Joystick joy2;
	
	CameraServer camera;
	
	CANTalon canFL; // Talon SRXs
    CANTalon canBL;
    CANTalon canFR;
    CANTalon canBR;
    CANTalon canWinch;
    CANTalon canWinch2;
    Talon talKicker;	//Talon SRs
    Talon talArmLeft;
    Talon talArmRight;
    
    Encoder encoderL; //Encoders
	Encoder encoderR;
	Encoder encoderElevator;
	
	Timer sensorThreadAuto; 	//Threads
	Timer elevatorThreadAuto;
	Timer elevatorThread2Auto;	
	Timer sensorThread;
	Timer elevatorThread;
	Timer elevatorThread2;
	Timer elevatorThread3;
	Timer camThreadAuto;
	Timer camThread;
	
	PIDController camPID;
	PIDController FL;
	PIDController BL;
	PIDController FR;
	PIDController BR;

	AnalogInput pot1;		//Potentiometer, Compressor and solenoids
	Compressor comp;
    DoubleSolenoid gearShift;
    public static DoubleSolenoid leftArm;
    public static DoubleSolenoid rightArm;
    DoubleSolenoid flipper;
   
    DigitalInput limit1;	//Limit Switches
    DigitalInput limit2;
    
    String gearPos, gearPos2; // Strings for the smartdasboard gear positions
    
    double leftThumb2,rightThumb2; 	// Variables where second Xbox thumbstick values are stored
    double leftTrig,rightTrig;	   	// Variables where Xbox trigger values are stored
    double leftTrig2,rightTrig2;	// Variables where second Xbox trigger values are stored
    double degrees, potDegrees;		// Variables where Potentiometer values are stored
	double leftThumb,rightThumb;	// Variables where first Xbox thumbstick values are stored
	double turnRad, speedMultiplier;// Variables for turning radius and overall speed multiplier
	double deadZ, deadZ2;			// Variables that store deadzones
	double camSet1, camSet2;		// Variables that decide that setpoints the cam uses
	double leftRate, rightRate;
	
    boolean stillPressed;	//Booleans to stop button presses from repeating 20 x per second lol
    public static boolean	stillPressed2;
    boolean stillPressed3;
    boolean stillPressed4;
    boolean stillPressed5;
    boolean stillPressed6;
    boolean stillPressed7;
    boolean stillPressed8;
    boolean stillPressed9;
    boolean stillPressed10;
    boolean camChanger = true;
    boolean elevatorMax;	//Booleans for elevator limit switches
    boolean elevatorMin;
    static boolean elevatorManual;	//Boolean to decide whether manual elevator control is allowed
    boolean camSetPoint = false;
	static boolean gotoSpot;
	static boolean gotoSpot2;
	static boolean gotoSpot3;

	boolean gotoSpot4;
	boolean gotoCam1 = true;
	boolean gotoCam2 = false;
	boolean camChange = false;
	boolean camActivate = false;
	boolean goOnce, teleOpOnce; // Variables to allow auto and certain teleop funtions to run only once
	
	int camMode;	// Decide whether cam should use setpoint or manual mode
	int leftR, rightR, elevatorR;	// Variables that store encoder values. "R" means rotations not "right".
	int autoMode;	// Variable that decides which auto to use
	
    public void robotInit()
    {
    	
    	prefs = Preferences.getInstance();
   	
    	canFL = new CANTalon(1); // Declaring shit
    	canBL = new CANTalon(2);
    	canFR = new CANTalon(5);
    	canBR = new CANTalon(6);
    	canWinch = new CANTalon(3);
    	canWinch2 = new CANTalon(4);
    	talKicker = new Talon(0);
    	talArmLeft = new Talon(1);
    	talArmRight = new Talon(2);
    
    	sensorThread = new Timer();
    	elevatorThread = new Timer();
    	elevatorThread2 = new Timer();
    	elevatorThread3 = new Timer();
    	sensorThreadAuto = new Timer();
    	elevatorThreadAuto = new Timer();
    	elevatorThread2Auto = new Timer();
    	camThreadAuto = new Timer();
    	camThread = new Timer();
    
    	elevatorManual = false;
    	camMode = 1;
   
    	gearPos = "Gear Position:";
    
    	joy = new Joystick(0);
    	joy2 = new Joystick(1);
    
    	comp  = new Compressor(0);
    	comp.setClosedLoopControl(true); // Setting compressor to closed loop control (basically automatic)
    
    	pot1 = new AnalogInput(0);  
    
    	limit1 = new DigitalInput(3);
    	limit2 = new DigitalInput(2);
    
    	rightArm = new DoubleSolenoid(2, 3);
    	rightArm.set(DoubleSolenoid.Value.kForward);
    	leftArm = new DoubleSolenoid(4, 5);
    	leftArm.set(DoubleSolenoid.Value.kForward);
    	gearShift = new DoubleSolenoid(6, 7);
    	gearShift.set(DoubleSolenoid.Value.kForward);
    	flipper = new DoubleSolenoid(0, 1);
    	flipper.set(DoubleSolenoid.Value.kReverse);
    
    	encoderElevator = new Encoder(0, 1, true, EncodingType.k4X);
    	encoderR = new Encoder(6, 7, true, EncodingType.k4X);
    	encoderL = new Encoder(8, 9, true, EncodingType.k4X);
    	encoderL.reset();
    	encoderR.reset();
    	encoderElevator.reset(); 
    	teleOpOnce = true;
    	speedMultiplier = 1;
    	turnRad = 0.90;
    	goOnce = true;
        	
    	camPID = new PIDController(-8, 0, -1, pot1, talKicker);
    	FR = new PIDController(1, 0, 1, encoderR, canFR);
    	FL = new PIDController(-1, 0, -1, encoderR, canFL);
    	BR = new PIDController(1, 0, 1, encoderR, canBR);
    	BL = new PIDController(-1, 0, -1, encoderR, canBL);
    	
    	camSet1 = prefs.getDouble("Cam_In", 2.5);
    	camSet2 = prefs.getDouble("Cam_Out", 2.91);
    	deadZ = prefs.getDouble("DeadZone", 0.1);
    	autoMode = prefs.getInt("Auto_Mode", 0); // Determining which auto mode should be used from the preferences table on SmartDashboard
    }

    
    public void autonomousInit()
    {
    	
    }
    
    /**
    * This function is called periodically [20 ms] during autonomous
    */
    
    public void autonomousPeriodic()
    {
    	  if(goOnce) // Allows Auto to run only once instead of 20x per second
    	  {
       		elevatorThread2Auto.schedule(new TimerTask(){public void run(){elevatorLow();}}, 20, 20); //Starting Threads for auto
    		elevatorThreadAuto.schedule(new TimerTask(){public void run(){elevatorOneTote();}}, 20, 20);
    		sensorThread.schedule(new TimerTask(){public void run(){getSensors();}}, 20, 20);
    		camThreadAuto.schedule(new TimerTask(){public void run(){camSetpoint();}}, 20, 20);
    		
    		if(autoMode == 0)
    		{
    			goOnce = false;
    			nothingAuto();
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
    			Testing();
    		}
    		
    		if(autoMode == 6)
    		{
    			goOnce = false;
    			binJackerAuto();     
    		}
    		
    		if(autoMode == 7)
    		{
    			goOnce = false;
    			threeToteAuto();
    		}
    	}
  
    }

    
     //This function is called periodically [20 ms] during operator control
    
	public void teleopPeriodic() 
    {
		if(teleOpOnce) // Everything that should only be run once goes in here
		{
			elevatorThread.schedule(new TimerTask(){public void run(){elevatorOneTote();}}, 20, 20); // Starting threads
			elevatorThread2.schedule(new TimerTask(){public void run(){elevatorLow();}}, 20, 20);
			elevatorThread3.schedule(new TimerTask(){public void run(){elevatorALittleUp();}}, 20, 20);
			sensorThread.schedule(new TimerTask(){public void run(){getSensors();}}, 20, 20);
			
			//camPID.enable();
			camPID.setSetpoint(2.5);
			
		
			teleOpOnce = false; // Ending if statement so it only runs once
		}
		
		//camera = CameraServer.getInstance();
		//camera.setQuality(50);
		//camera.startAutomaticCapture("cam0");

    	getJoy();
    	
    	//Temporary Arm Motors
    	armMotors();
    	
    	Drivetrain.getArcadeV2(canFR, canFL, canBR, canBL, rightThumb, leftThumb, speedMultiplier, turnRad);
    
    	//This does not work
    	//Arms.getArcadeArms(talArmRight, talArmLeft, rightThumb2, leftThumb2, 1, 1);
    	
    	Elevator.manualControl(canWinch, canWinch2, elevatorMax, elevatorMin, leftTrig, rightTrig);
    	
    	//elevator();
    	
    	buttonToggles();
    	
    	camFullManual();
    	
    	camSetpoint();
    	
    	smartDashboard();
    	
    	Winch.manualControl(leftTrig2, rightTrig2, talKicker);
    	
    }

     //This function is called periodically during test mode
     
    public void testPeriodic() 
    {
    	
    }

//----------------------------------------------------------------------------------------------------------------------------------\\
   
    //Teleop methods
    
    public void getJoy()
    {
    	if((-joy.getRawAxis(1) <= deadZ) && (-joy.getRawAxis(1) >= -deadZ))
    	{
    		leftThumb = 0;
    	}
    	
    	else
    	{
    		leftThumb = -joy.getRawAxis(1);
    	}
    	
    	if((joy.getRawAxis(4) <= deadZ) && (joy.getRawAxis(4) >= -deadZ))
    	{
    		rightThumb = 0;
    	}
    	
    	else
    	{
    		rightThumb = joy.getRawAxis(4);
    	}
    	

    	leftTrig = (joy2.getRawAxis(3));
    	rightTrig = (joy2.getRawAxis(2));
    	leftTrig2 = (joy.getRawAxis(2) * 2);
    	rightTrig2 = (joy.getRawAxis(3) * 2);
    	
    	
    }
    
    public void smartDashboard()
    {
    	//Printing info for the smartdashboard
    	
    	SmartDashboard.putNumber("Elevator Encoder", elevatorR); 
    	SmartDashboard.putNumber("Cam Potentiometer", potDegrees); 
    	SmartDashboard.putBoolean("High Limit Switch", elevatorMax);   
    	SmartDashboard.putBoolean("Low Limit Switch", elevatorMin);   
    	SmartDashboard.putString(gearPos, gearPos2);
    	SmartDashboard.putNumber("Speed Multiplier", speedMultiplier);
    	SmartDashboard.putNumber("Turn Multiplier", turnRad);
    	SmartDashboard.putNumber("Left encoder Rate", leftRate);
    	SmartDashboard.putNumber("Right encoder Rate", rightRate);
    	SmartDashboard.putNumber("Cam In", camSet1);
    	SmartDashboard.putNumber("Cam Out", camSet2);
    	SmartDashboard.putNumber("Auto Mode", autoMode);
    	SmartDashboard.putBoolean("cam thing", camChanger);
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

    		leftArm.set(DoubleSolenoid.Value.kForward);
			rightArm.set(DoubleSolenoid.Value.kForward);
    		
    		if ((elevatorMin) && (elevatorR >= 1200))
    		{
    			canWinch.set(0.8);
    			canWinch2.set(0.8);
    		}
    		
    		else if((elevatorMin) && (elevatorR < 1400))
    		{
    			canWinch.set(0.4);
    			canWinch2.set(0.4);
    		}
    		
    		else 
    		{
    			canWinch.set(0);
    			canWinch2.set(0);
    			gotoSpot2=false;
   			}
    	}
    }
    
    public void elevatorALittleUp()
    {
    	if (joy2.getRawButton(2) == false) {stillPressed9 = false;}
    	
    	if (joy2.getRawButton(2) && (stillPressed9 == false))
    	{
    		gotoSpot3 = true;
    		stillPressed9 = true;
    	}
    	
    	if (gotoSpot3)
    	{
    		if (elevatorR < 1500)
    		{
    			canWinch.set(-0.55);
    			canWinch2.set(-0.55);
    		}
    		
    		else 
    		{
    			canWinch.set(0);
    			canWinch2.set(0);
    			gotoSpot3=false;
    		}
    	}
    }
    
    public void elevatorHigh()
    {
    	
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
    
    		leftArm.set(DoubleSolenoid.Value.kForward);
			rightArm.set(DoubleSolenoid.Value.kForward);
    		
    		if ((elevatorR < 10160) && (elevatorMax))
    		{
    			canWinch.set(-1);
    			canWinch2.set(-1);
    		}
    		else 
    		{
    			canWinch.set(0);
    			canWinch2.set(0);
    			gotoSpot=false;
    		}
    	}
    }
    
    public void camFullManual()
    {
    	//If cam manual is allowed, use the select button to move it in only one direction
    	
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
    	if(joy.getRawButton(7) == true)
    	{
    		encoderElevator.reset();
    		encoderR.reset();
    		encoderL.reset();
    	}
    	
    	//Arcade mode speed switcher
    	
    	if (joy.getRawButton(3) == false) {stillPressed10 = false;}
    	
    	if (joy.getRawButton(3) && (stillPressed10 == false))
    	{
    		if (speedMultiplier == 1)
			{
				speedMultiplier = 0.4;
				turnRad = 1.8;
				stillPressed10 = true;
				
			}
    		else if (speedMultiplier == 0.4)
    		{
    			speedMultiplier = 1;
    			turnRad = 0.74;
    			stillPressed10 = true;
    		}
    		
    	}
    	
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
    	
    /**
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
    	**/
    	
    	if (joy2.getRawButton(1) == false) {stillPressed5 = false;}
    	
    	if (joy2.getRawButton(1) && (stillPressed5 == false))
    	{
    		if (camChanger)
			{
				camPID.setSetpoint(2.91);
				camChanger = false;
			}
    		else if (!camChanger)
    		{
    			camPID.setSetpoint(2.5);
    			camChanger = true;
       		}
    
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
    		Solenoid.toggle(gearShift);
    		stillPressed = true;
    	}
    	
    	//Arms Toggle [LB] Controller 1
    	
    	if (joy.getRawButton(5) == false) {stillPressed2 = false;}
    	
    	if (joy.getRawButton(5) && (stillPressed2 == false))
    	{
    		Arms.toggle(leftArm, rightArm);
    		stillPressed2 = true;
    	}
    	
    	//Arm Toggle [LB] Controller 2
    	if (joy2.getRawButton(5) == false) {stillPressed4 = false;}
		
    	if (joy2.getRawButton(5) && (stillPressed4 == false))
    	{
    		Arms.toggle(leftArm, rightArm);
    		stillPressed4 = true;
    	}
    	
    	//Stinger [RB]
    	
    		if (joy.getRawButton(1) == false) {stillPressed3 = false;}
    		
    		if (joy.getRawButton(1) && (stillPressed3 == false))
    		{
    			Solenoid.toggle(flipper);
    			stillPressed3 = true;
    		}	
    }
       
    public void camSetpoint()
    {
    	//If cam is in setpoint mode, switch positions using the pot
    	
    	if ((camActivate) && (camMode == 1))
    	{
    		if(gotoCam1)
    		{

        		if (potDegrees < camSet2)
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

    			if (potDegrees > camSet1)
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

    public void armMotors()
    {
    	//Arm motors
    	
    	//Assign xbox values to variables
    	
		leftThumb2 = -(joy2.getRawAxis(1));
    	rightThumb2 = -(joy2.getRawAxis(4));
    	
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


//----------------------------------------------------------------------------------------------------------------------------------\\
   
    //Auto Mode methods
    
    public void setPID(int distance)
    {
    	FR.setSetpoint(distance);
    	FL.setSetpoint(distance);
    	BR.setSetpoint(distance);
    	BL.setSetpoint(distance);
    }
    
    public void getSensors()
    {
    	//Gets the absolute value of the drivetrain encoders
    	
    	leftR = Math.abs(encoderL.get());
    	rightR = Math.abs(encoderR.get());
    	rightRate = encoderR.getRate();
    	leftRate = encoderL.getRate();
    	
    	//Gets the regular values of everything else
    	
    	elevatorR = (encoderElevator.get());
    	potDegrees = pot1.getVoltage();
    	elevatorMin = limit2.get();
    	elevatorMax = limit1.get();
    	
    	//Prints them to the smartdashboard
    	
    	SmartDashboard.putNumber("Left Encoder", leftR);
    	SmartDashboard.putNumber("Right Encoder", rightR);
    	SmartDashboard.putNumber("Elevator Encoder", elevatorR);
    }
    
    public void drive(int distance, double power)
    {
    	encoderR.reset();
		encoderL.reset();
    	
		System.out.println("Drive Start");
		
		while(Math.abs(encoderR.getDistance()) < distance)
		{
			canFL.set(-power);
			canBL.set(-power);
			
			canBR.set(power);
			canFR.set(power);
			
			System.out.println(encoderR.get());
		}
		
		System.out.println("Drive Done");
		
    	canFL.set(0);
		canBL.set(0);
		
		canBR.set(0);
		canFR.set(0);
		
		encoderR.reset();
		encoderL.reset();
	
    }
   
    public void setTurn(double turnDegrees, double power)
    {
    	//Sets the drivetrain motors to the given power to make a right angle turn
    	
    	encoderL.reset();
    	encoderR.reset();
    	
    	System.out.println("Turn Starts");
    	
    	while(Math.abs(encoderR.getDistance()) < (turnDegrees * (550/90)))
    	{
    		canFL.set(power);
    		canBL.set(power);
    		
    		canBR.set(power);
    		canFR.set(power);
    		
    		System.out.println(encoderR.get());
    	}
    	
    	canFL.set(0);
		canBL.set(0);
		
		canBR.set(0);
		canFR.set(0);
		
		System.out.println("Turn Done");
		
		encoderL.reset();
		encoderR.reset();
    
    }
    
    public void moveArms(int time, int power)
    {
    	//Moves arms given power
    	
    	talArmLeft.set(-power);
    	talArmRight.set(power);
    		
    	//Waits for the given time
    	
    	try {
    			Thread.sleep(time);
    	} catch (InterruptedException e) {
    		
   			Thread.currentThread().interrupt();
   		}
    	
    	//Stops
    	
    	talArmLeft.set(0);
		talArmRight.set(0);
    }
    
    public void moveArmswhileDrive(int distance, double power, int powerArms)
    {
    	//Moves arms given power
    	encoderR.reset();
		encoderL.reset();
		
    	talArmLeft.set(-powerArms);
    	talArmRight.set(powerArms);
    	
		while(Math.abs(encoderR.get()) < distance)
		{
			canFL.set(-power);
			canBL.set(-power);
			
			canBR.set(power);
			canFR.set(power);
			
			talArmLeft.set(-powerArms);
	    	talArmRight.set(powerArms);
		}
		
		//Stops
    	canFL.set(0);
		canBL.set(0);
		
		canBR.set(0);
		canFR.set(0);
		
    	talArmLeft.set(0);
		talArmRight.set(0);
		
		encoderR.reset();
		encoderL.reset();
    	
    }
  
    public void wait(int Milliseconds)
    {
    	//Pauses the thread for the given amount of time
    	
    	try {
			Thread.sleep(Milliseconds);
		} catch (InterruptedException e) {
			
			Thread.currentThread().interrupt();
		}
    }
    
    public synchronized void elevatorUp()
    {
    	gotoSpot = true;
		gotoCam1 = true;
		gotoCam2 = false;
		camActivate = true;
	
    }
    
    public synchronized void elevatorDown()
    {
    	gotoSpot2 = true;
    	gotoCam1 = false;
		gotoCam2 = true;
		camActivate = true;
    }
    
    public synchronized void camIn(){
    	gotoCam1 = false;
		gotoCam2 = true;
		camActivate = true;
    }
    
    
//----------------------------------------------------------------------------------------------------------------------------------\\
    
    //Auto Modes
    
    public void Testing()
    {
    	FR.enable();
		FL.enable();
		BR.enable();
		BL.enable();
    	setPID(1000);
    }
    
    public void moveToZoneAuto()
    {
    	drive(750, -0.5);
    	
    	elevatorThreadAuto.cancel();
    	elevatorThread2Auto.cancel();
    	sensorThreadAuto.cancel();
    }
    
    public void oneToteAuto()
    {
    	Arms.close(leftArm, rightArm);
    	
    	setTurn(48.5, -1);
    	
    	drive(1100, 0.5);
    	
    	Arms.open(leftArm, rightArm);
    	
    	elevatorThreadAuto.cancel();
    	elevatorThread2Auto.cancel();
    	sensorThreadAuto.cancel();
    }
    
    public void oneBinAuto()
    {
    	Arms.close(leftArm, rightArm);
    	
    	setTurn(90, 1);
    	
    	drive(1400, 0.5);
    	
    	Arms.open(leftArm, rightArm);
    	
    	elevatorThreadAuto.cancel();
    	elevatorThread2Auto.cancel();
    	sensorThreadAuto.cancel();
    }
    
    public void nothingAuto()
    {
    	elevatorThreadAuto.cancel();
    	elevatorThread2Auto.cancel();
    	sensorThreadAuto.cancel();
    }
    
    public void threeToteAuto()
    { 
    		//Start
    	encoderR.reset();
    	elevatorUp();	    	
    	Arms.open(leftArm, rightArm);
    	wait(1100);
    		//Turn 1
  		setTurn(31, -0.5);
  		wait(10);
    		//Drive 1 w/ anti-coast
  		drive(800, 0.65);
  		wait(10);
    	drive(80, -0.65);
    	wait(10);
    		//Turn 2
  	    setTurn(35.955, 0.5);
    	wait(10);
    		//Drive 2 w/ arms and FUCKING INTAKE BITCH
    	moveArmswhileDrive (1200, 0.40, -1);
   	   	moveArms(500, -1);
     	Arms.close(leftArm, rightArm);
      	moveArms(1000, -1);
	    wait(10);
	    	//Tote 2 gets picked up	 
	    camIn();
	    wait(1000);
	    elevatorDown();
	    wait(3000);
	    elevatorUp();
	    wait(1100);
    		//Turn 3
	    setTurn(40, -0.65);
  	   	wait(10);
    		//Drive 3 w/ anti-coast
   		drive(800, 0.65);
       	wait(10);
   		drive(80, -0.65);
     	wait(10);
    		//Turn 4
    	setTurn(30, 0.65);
    	wait(10);
    		//Drive 4 w/ arms and intake
    	moveArmswhileDrive (1200, 0.40, -1);
    	moveArms(500, -1);
       	Arms.close(leftArm, rightArm);
       	moveArms(1000, -1);
        wait(10);
    		//End
    }

    public void binJackerAuto()
    {
    	drive(598, -0.7);
		
		wait(900);
		
    	Solenoid.toggle(flipper);
    	
    	wait(1000);
    	
    	drive(800, 1);
    	
		wait(700);
		
    	Solenoid.toggle(flipper);
    	
    	elevatorThreadAuto.cancel();
    	elevatorThread2Auto.cancel();
    	sensorThreadAuto.cancel();
    }
    
}
