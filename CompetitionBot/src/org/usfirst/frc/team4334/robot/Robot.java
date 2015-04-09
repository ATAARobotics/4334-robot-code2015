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
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is the official code for ATA's 2015 robot: "Elevation".
 */

// [COPYRIGHT] Alberta Tech Alliance 2015. All rights reserved.

public class Robot extends IterativeRobot 
{
    
    //This function is run when the robot is first started up and should be
    //used for any initialization code.
	
	public static Preferences prefs;
   
	public static Joystick cole;	//Xbox controllers
	public static Joystick miranda;
	
	public static CameraServer camera;
	
	public static CANTalon canFL; // Talon SRXs
	public static CANTalon canBL;
	public static  CANTalon canFR;
    public static CANTalon canBR;
    public static CANTalon elevatorMoter;
    public static CANTalon elevatorMoter2;
    public static Talon talKicker;	//Talon SRs
    public static Talon talArmLeft;
    public static Talon talArmRight;
    
    public static Encoder encoderL; //Encoders
    public static Encoder encoderR;
    public static Encoder encoderElevator;
	
    public static Timer sensorThreadAuto; 	//Threads
    public static Timer elevatorThreadAuto;
    public static Timer elevatorThread2Auto;	
    public static Timer sensorThread;
    public static Timer elevatorThread;
    public static Timer elevatorThread2;
    public static Timer elevatorThread3;
    public static Timer camThreadAuto;
    public static Timer camThread;

    public static AnalogInput pot1;		//Potentiometer, Compressor and solenoids
    public static Compressor comp;
    public static  DoubleSolenoid gearShift;
    public static DoubleSolenoid leftArm;
    public static DoubleSolenoid rightArm;
    public static DoubleSolenoid flipper;
   
    public static DigitalInput limit1;	//Limit Switches
    public static DigitalInput limit2;
    
    public static String gearPos, gearPos2; // Strings for the smartdasboard gear positions
    
    public static double leftThumb2,rightThumb2; 	// Variables where second Xbox thumbstick values are stored
    public static double leftTrig,rightTrig;	   	// Variables where Xbox trigger values are stored
    public static double leftTrig2,rightTrig2;	// Variables where second Xbox trigger values are stored
    public static double degrees, potDegrees;		// Variables where Potentiometer values are stored
    public static double leftThumb,rightThumb;	// Variables where first Xbox thumbstick values are stored
    public static double turnRad, speedMultiplier;// Variables for turning radius and overall speed multiplier
    public static double deadZ, deadZ2;			// Variables that store deadzones
    public static double camSet1, camSet2;		// Variables that decide that setpoints the cam uses
    public static double leftRate, rightRate;
	
    public static boolean stillPressed;	//Booleans to stop button presses from repeating 20 x per second lol
    public static boolean stillPressed2;
    public static boolean stillPressed3;
    public static boolean stillPressed4;
    public static boolean stillPressed5;
    public static boolean stillPressed6;
    public static boolean stillPressed7;
    public static boolean stillPressed8;
    public static boolean stillPressed9;
    public static boolean stillPressed10;
    public static boolean elevatorMax;	//Booleans for elevator limit switches
    public static boolean elevatorMin;
    public static boolean elevatorManual;	//Boolean to decide whether manual elevator control is allowed
    public static boolean camSetPoint = false;
    public static boolean gotoSpot, gotoSpot2, gotoSpot3, gotoSpot4;
    public static boolean gotoCam1 = true;
    public static boolean gotoCam2 = false;
    public static boolean camChange = false;
    public static boolean camActivate = false;
    public static boolean goOnce, teleOpOnce; // Variables to allow auto and certain teleop funtions to run only once
	
    public static int camMode;	// Decide whether cam should use setpoint or manual mode
    public static int leftR, rightR, elevatorR;	// Variables that store encoder values. "R" means rotations not "right".
	public static int autoMode;	// Variable that decides which auto to use
	
    public void robotInit()
    {
    	
    	prefs = Preferences.getInstance();
   	
    	canFL = new CANTalon(1); // Declaring shit
    	canBL = new CANTalon(2);
    	canFR = new CANTalon(5);
    	canBR = new CANTalon(6);
    	elevatorMoter = new CANTalon(3);
    	elevatorMoter2 = new CANTalon(4);
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
    
    	cole = new Joystick(0);
    	miranda = new Joystick(1);
    
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
    	autoMode = 1;
    	speedMultiplier = 1;
    	turnRad = 0.74;
    	goOnce = true;
    	
    	camSet1 = prefs.getDouble("Cam_Out", 2.91);
    	camSet2 = prefs.getDouble("Cam_In", 2.5);
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
		
			teleOpOnce = false; // Ending if statement so it only runs once
		}

		//camera = CameraServer.getInstance();
		//camera.setQuality(50);
		//camera.startAutomaticCapture("cam0");

    	arcadeDrive();
    	
    	armMotors();
    	
    	Elevator.manual();
    	
    	buttonToggles();
    	
    	camFullManual();
    	
    	camSetpoint();
    	
    	smartDashboard();
    	
    	if(cole.getRawButton(7) == true)
    	{
    		encoderElevator.reset();
    		encoderR.reset();
    		encoderL.reset();
    	}
    	
    }

     //This function is called periodically during test mode

	public void testPeriodic() 
    {
    	
    }

//----------------------------------------------------------------------------------------------------------------------------------\\
   
    //Teleop methods
    
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
    }
    
    public void elevatorLow()
    {
    	if (miranda.getRawButton(3) == false) {stillPressed7 = false;}
    	
    	if (miranda.getRawButton(3) && (stillPressed7 == false))
    	{
    		gotoSpot2 = true;
    		gotoCam1 = false;
			gotoCam2 = true;
    		camActivate = true;
    		stillPressed7 = true;

    		leftArm.set(DoubleSolenoid.Value.kForward);
			rightArm.set(DoubleSolenoid.Value.kForward);
    		
    		if ((elevatorMin) && (elevatorR >= 1400))
    		{
    			elevatorMoter.set(0.6);
    			elevatorMoter2.set(0.6);
    		}
    		
    		else if((elevatorMin) && (elevatorR < 1400))
    		{
    			elevatorMoter.set(0.2);
    			elevatorMoter2.set(0.2);
    		}
    		
    		else 
    		{
    			elevatorMoter.set(0);
    			elevatorMoter2.set(0);
    			gotoSpot2=false;
   			}
    	}
    }
    
    public void elevatorALittleUp()
    {
    	if (miranda.getRawButton(2) == false) {stillPressed9 = false;}
    	
    	if (miranda.getRawButton(2) && (stillPressed9 == false))
    	{
    		gotoSpot3 = true;
    		stillPressed9 = true;
    	}
    	
    	if (gotoSpot3)
    	{
    		if (elevatorR < 1500)
    		{
    			elevatorMoter.set(-0.55);
    			elevatorMoter2.set(-0.55);
    		}
    		
    		else 
    		{
    			elevatorMoter.set(0);
    			elevatorMoter2.set(0);
    			gotoSpot3=false;
    		}
    	}
    }
    
    public void elevatorHigh()
    {
    	
    }
    
    public void elevatorOneTote()
    {
    	if (miranda.getRawButton(4) == false) {stillPressed6 = false;}
    	
    	if (miranda.getRawButton(4) && (stillPressed6 == false))
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
    		
    		if ((elevatorR < 10768) && (elevatorMax))
    		{
    			elevatorMoter.set(-1);
    			elevatorMoter2.set(-1);
    		}
    		else 
    		{
    			elevatorMoter.set(0);
    			elevatorMoter2.set(0);
    			gotoSpot=false;
    		}
    	}
    }
    
    public void camFullManual()
    {
    	//If cam manual is allowed, use the select button to move it in only one direction
    }
    
    public void buttonToggles(){
    }
       
    public void camSetpoint()
    {
    	//If cam is in setpoint mode, switch positions using the pot
    	
    	if ((camActivate) && (camMode == 1))
    	{
    	if(gotoCam1)
    		{

        		if (potDegrees < 2.91)
        		{
        			talKicker.set(-1);
        		}
     
        		else 
        		{
        			talKicker.set(0);
        			camActivate=false;
        		}
    		}
    		
    		else if(!gotoCam1)
    		{

    			if (potDegrees > 2.5)
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
    	//Assign the xbox values to variables
    	
    	rightThumb = cole.getRawAxis(4);
    	
    	leftThumb = -(cole.getRawAxis(1));
    	
    	//Define the speed multiplier, deadzones and turning radius multiplier
     	
    	deadZ = 0.25;
    	
    	//If left thumbstick is still
    	
    	if((leftThumb < deadZ) && (leftThumb > -deadZ))
    	{
    		canFL.set(((-(rightThumb * turnRad))) * speedMultiplier);
    		canBL.set(((-(rightThumb * turnRad))) * speedMultiplier);
    		
    		canBR.set(((-(rightThumb * turnRad))) * speedMultiplier);
    		canFR.set(((-(rightThumb * turnRad))) * speedMultiplier);
    	}
    	
    	//If right thumbstick is still
    	
    	if((rightThumb < deadZ) && (rightThumb > -deadZ))
    	{
    		canFL.set(((-leftThumb)) * speedMultiplier);
    		canBL.set(((-leftThumb)) * speedMultiplier);
    		
    		canBR.set(((leftThumb)) * speedMultiplier);
    		canFR.set(((leftThumb)) * speedMultiplier);
    	}
    	
    	//If both thumbsticks are positive
    	
    	if((leftThumb > deadZ) && (rightThumb > deadZ))
    	{
    		canFL.set(((-leftThumb)) * speedMultiplier);
    		canBL.set(((-leftThumb)) * speedMultiplier);
    		
    		canBR.set(((leftThumb - (rightThumb * turnRad))) * speedMultiplier);
    		canFR.set(((leftThumb - (rightThumb * turnRad))) * speedMultiplier);
    	}
    	
    	//If left thumbstick is positive and right thumbstick is negative
    	
    	if((leftThumb > deadZ) && (rightThumb < -deadZ))
    	{
    		canFL.set(((-(leftThumb + (rightThumb * turnRad)))) * speedMultiplier);
    		canBL.set(((-(leftThumb + (rightThumb * turnRad)))) * speedMultiplier);
		
    		canBR.set(((leftThumb)) * speedMultiplier);
    		canFR.set(((leftThumb)) * speedMultiplier);
    	}
    	
    	//If left thumbstick is negative and right thumbstick is positive
    	
    	if((leftThumb < -deadZ) && (rightThumb > deadZ))
    	{
    		canFL.set(((-(leftThumb + (rightThumb * turnRad)))) * speedMultiplier);
    		canBL.set(((-(leftThumb + (rightThumb * turnRad)))) * speedMultiplier);
    		
    		canBR.set(((leftThumb)) * speedMultiplier);
    		canFR.set(((leftThumb)) * speedMultiplier);
    	}
    	
    	//If left thumbstick is negative and right thumbstick is negative
    	
    	if((leftThumb < -deadZ) && (rightThumb < -deadZ))
    	{
    		canFL.set(((-leftThumb)) * speedMultiplier);
    		canBL.set(((-leftThumb)) * speedMultiplier);
    		
    		canBR.set(((leftThumb - (rightThumb * turnRad))) * speedMultiplier);
    		canFR.set(((leftThumb - (rightThumb * turnRad))) * speedMultiplier);
    	}

    }
       
    public void armMotors()
    {
    	
    }
       


//----------------------------------------------------------------------------------------------------------------------------------\\
   
    //Auto Mode methods
    
    public void getSensors()
    {
    	//Gets the absolute value of the drivetrain encoders
    	
    	leftR = Math.abs(encoderL.get());
    	rightR = Math.abs(encoderR.get());
    	rightRate = encoderR.getRate();
    	leftRate = encoderL.getRate();
    	
    	//Gets the regular values of everything else
    	
    	elevatorR = (-encoderElevator.get());
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
    	
		while(rightR < distance)
		{
			canFL.set(-power);
			canBL.set(-power);
			
			canBR.set(power);
			canFR.set(power);
		}
		//Stops
		
    	canFL.set(0);
		canBL.set(0);
		
		canBR.set(0);
		canFR.set(0);
		
		encoderR.reset();
		encoderL.reset();
	
    }
    
    public void armsClose()
    {
    	//Sets the arms solenoids to open
    	
    	leftArm.set(DoubleSolenoid.Value.kReverse);  
		rightArm.set(DoubleSolenoid.Value.kReverse);
    	
    }
    
    public void armsOpen()
    {
    	//Sets the arms solenoids to closed
    	leftArm.set(DoubleSolenoid.Value.kForward);  
		rightArm.set(DoubleSolenoid.Value.kForward);
    	
    }
    
    public void setTurn(double turnDegrees, double power)
    {
    	//Sets the drivetrain motors to the given power to make a right angle turn
    	
    	encoderL.reset();
    	encoderR.reset();
    	
    	while(rightR < (turnDegrees * (550/90)))
    	{
    		canFL.set(power);
    		canBL.set(power);
    		
    		canBR.set(power);
    		canFR.set(power);
    	}
    	
    	canFL.set(0);
		canBL.set(0);
		
		canBR.set(0);
		canFR.set(0);
		
		encoderL.reset();
		encoderR.reset();
    
    }
    
    public void moveArms(int time, int power)
    {
    	//Moves arms ar given power
    	
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
    
    public void stingerOut()
    {
    	//Sets the stinger solenoid to out
    	
    	flipper.set(DoubleSolenoid.Value.kForward);
    }
    
    public void stingerIn()
    {
    	//Sets the stinger solenoid to in
    	
    	flipper.set(DoubleSolenoid.Value.kReverse);
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
    
    public void elevatorUp()
    {
    	gotoSpot = true;
		gotoCam1 = true;
		gotoCam2 = false;
		camActivate = true;
	
    }
    
    public void elevatorDown()
    {
    	gotoSpot2 = true;
		gotoCam1 = false;
		gotoCam2 = true;
		camActivate = true;
    }
    
    public void antiCoast()
    {
    	
    }
    
//----------------------------------------------------------------------------------------------------------------------------------\\
    
    //Auto Modes
    
    public void Testing()
    {
    	
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
    	armsClose();
    	
    	setTurn(90, -1);
    	
    	drive(1400, 0.5);
    	
    	armsOpen();
    	
    	elevatorThreadAuto.cancel();
    	elevatorThread2Auto.cancel();
    	sensorThreadAuto.cancel();
    }
    
    public void oneBinAuto()
    {
    	armsClose();
    	
    	setTurn(90, 1);
    	
    	drive(1400, 0.5);
    	
    	armsOpen();
    	
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
    	encoderR.reset();
    	elevatorUp();
    	wait(1100);
    	setTurn(45, -0.65);
    	encoderR.reset();
    	drive(1000, 0.5);
    	drive(100, -0.5);
    	setTurn(40, 0.65);
    	//drive(1000, 0.5);
    }

    public void binJackerAuto()
    {
    	drive(598, -0.7);
		
		wait(900);
		
    	stingerOut();
    	
    	wait(1000);
    	
    	drive(800, 1);
    	
		wait(700);
		
    	stingerIn();
    	
    	elevatorThreadAuto.cancel();
    	elevatorThread2Auto.cancel();
    	sensorThreadAuto.cancel();
    }
    
}
