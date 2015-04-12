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
import edu.wpi.first.wpilibj.PIDController;

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
	
	//public static CameraServer camera;
	
	// Talon SRXs
	
	PIDController camPID;
	
	PIDController FLpid;
	PIDController FRpid;
	PIDController BLpid;
	PIDController BRpid;
	
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
    public static DoubleSolenoid gearShift;
    public static DoubleSolenoid leftArm;
    public static DoubleSolenoid rightArm;
    public static DoubleSolenoid flipper;
   
    public static DigitalInput limit1;	//Limit Switches
    public static DigitalInput limit2;
    
    public static String gearPos, gearPos2; // Strings for the smartdasboard gear positions
    
    
    public static double degrees, potDegrees;		// Variables where Potentiometer values are stored
    public static double turnRad, speedMultiplier; // Variables for turning radius and overall speed multiplier
    public static double leftRate, rightRate;
	
    public static boolean stillPressed;
    
    public static boolean elevatorMax;	//Booleans for elevator limit switches
    public static boolean elevatorMin;
    public static boolean elevatorManual;	//Boolean to decide whether manual elevator control is allowed
    public static boolean camSetPoint = false;
    public static boolean gotoCam = true;
    public static boolean camChange = false;
    public static boolean camActivate = false;
    public static boolean goOnce, teleOpOnce; // Variables to allow auto and certain teleop funtions to run only once
	
    public static int camMode;	// Decide whether cam should use setpoint or manual mode
    public static int leftR, rightR, elevatorR;	// Variables that store encoder values. "R" means rotations not "right".
	public static int autoMode;	// Variable that decides which auto to use
	
    public void robotInit()
    {
    	
    	prefs = Preferences.getInstance();
   
    	talKicker = new Talon(0);
    	talArmLeft = new Talon(1);
    	talArmRight = new Talon(2);
    	
    	CANTalon canFL = new CANTalon(1); 
    	CANTalon canBL = new CANTalon(2);
    	CANTalon canFR = new CANTalon(5);
        CANTalon canBR = new CANTalon(6);
    
    	camPID = new PIDController(-8, 0, -1, pot1, talKicker);
    	FLpid = new PIDController(0, 0, 0, encoderL, canFL);
    	FRpid = new PIDController(0, 0, 0, encoderL, canFR);
    	BLpid = new PIDController(0, 0, 0, encoderL, canBL);
    	BRpid = new PIDController(0, 0, 0, encoderL, canBR);
    	
    	
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
    	
    	//camSet1 = prefs.getDouble("Cam_Out", 2.91);
    	//camSet2 = prefs.getDouble("Cam_In", 2.5);
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
    			nothingAuto();
    		}
    		if(autoMode == 1)
    		{	
    			moveToZoneAuto();
    		}
    		
    		if(autoMode == 2)
    		{	
    			oneToteAuto();
    		}
    		
    		if(autoMode == 3)
    		{	
    			oneBinAuto();
    		}
    		
    		if(autoMode == 4)
    		{	
    			Testing();
    		}
    		
    		if(autoMode == 6)
    		{	
    			binJackerAuto();     
    		}
    		
    		if(autoMode == 7)
    		{	
    			threeToteAuto();
    		}
    		goOnce = false;
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
			
			camPID.enable();
			
			FLpid.enable();
			FRpid.enable();
			BLpid.enable();
			BRpid.enable();
		
			teleOpOnce = false; // Ending if statement so it only runs once
		}

		//camera = CameraServer.getInstance();
		//camera.setQuality(50);
		//camera.startAutomaticCapture("cam0");

    	arcadeDrive();
    	
    	armMotors();
    	
    	manual();
    	
    	buttonToggles();
    	
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
    	Toggles.GearPrint();
    	
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
    	Elevator.GotoSetpoint(1400);
    }
    
    public void elevatorALittleUp()
    {
    	Elevator.GotoSetpoint(1500);
    }
    
    public void elevatorHigh()
    {
    	
    }
    
    public void elevatorOneTote()
    {
    	if (miranda.getRawButton(4) == false) {stillPressed = false;}
    	
    	if (miranda.getRawButton(4) && (stillPressed == false))
    	{
    		gotoCam = true;
    		stillPressed = true;
    
    		leftArm.set(DoubleSolenoid.Value.kForward);
			rightArm.set(DoubleSolenoid.Value.kForward);
    		
			Elevator.GotoSetpoint(10768);
    	}
    }
    
    
    public void buttonToggles(){
    	if(cole.getRawButton(3) == true){
    		Toggles.SpeedToggle();
    	}
    	else{
    		Toggles.stillPressed3 = false;
    	}
    	
    	if(miranda.getRawButton(1) == true){
    		Toggles.CamSetToggle();
    	}
    	else{
    		Toggles.stillPressed1 = false;
    	}
    	
    	if(miranda.getRawButton(6) == true){
    		Toggles.CamModeToggle();
    	}
    	else{
    		Toggles.stillPressed6 = false;
    	}
    	
    	if(cole.getRawButton(2) == true){
    		Toggles.GearShift();
    	}
    	else{
    		Toggles.stillPressed2 = false;
    	}
    	
    	if(cole.getRawButton(5) == true || miranda.getRawButton(5)){
    		Toggles.ArmToggle();
    	}
    	else{
    		Toggles.stillPressed5 = false;
    	}
    	
    	if(cole.getRawButton(1) == true){
    		Toggles.StingerToggle();
    	}
    	else{
    		Toggles.stillPressed = false;
    	}
    }
       
    public void camSetpoint()
    {
    	//If cam is in setpoint mode, switch positions using the pot
    	
    	if ((camActivate) && (camMode == 1))
    	{
    		Cam.Auto(gotoCam);
    	}	
    }
    
    public void arcadeDrive()
    {
    	if(cole.getRawAxis(1)!= 0 && cole.getRawAxis(4) == 0){
    		Drive.drive();
    	}
    }
       
    public void manual(){
    	if(elevatorMax && elevatorMin){
    		Elevator.manual();
    	}
    	if(camMode == 2){
    		Cam.Manual();
    	}
    }
    
    public void armMotors()
    {
    	if(miranda.getRawAxis(1)!= 0 && miranda.getRawAxis(4) == 0){
    		Arms.armMoters();
    	}
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
			Drive.canFL.set(-power);
			Drive.canBL.set(-power);
			
			Drive.canBR.set(power);
			Drive.canFR.set(power);
		}
		//Stops
		
    	Drive.canFL.set(0);
		Drive.canBL.set(0);
		
		Drive.canBR.set(0);
		Drive.canFR.set(0);
		
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
    		Drive.canFL.set(power);
    		Drive.canBL.set(power);
    		
    		Drive.canBR.set(power);
    		Drive.canFR.set(power);
    	}
    	
    	Drive.canFL.set(0);
		Drive.canBL.set(0);
		
		Drive.canBR.set(0);
		Drive.canFR.set(0);
		
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
		gotoCam = true;
		camActivate = true;
	
    }
    
    public void elevatorDown()
    {
		gotoCam = false;
		camActivate = true;
    }
    
    public void setDrivePID(int target){
    	FLpid.setSetpoint(target);
    	FRpid.setSetpoint(target);
    	BLpid.setSetpoint(target);
    	BRpid.setSetpoint(target);
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
