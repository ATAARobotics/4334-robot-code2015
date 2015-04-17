package external;

import edu.wpi.first.wpilibj.Joystick;

/**
 * @author Jayden Chan
 * Date Created: April 18 2015
 * Last Updated: April 18 2015
 */
public class SuperController 
{
	private final Joystick joy;
	private boolean pre;
	
	public SuperController(Joystick joy)
	{ //Constructing SuperController object with chosen joystick.
		this.joy = joy;
	}
	
	public void doWhenPressed(int button, Runnable action)
	{ //Runs chosen object when chosen button is pressed. Does not repeat if held down.
		if((joy.getRawButton(button)) && (!pre))
		{
			pre = true;
			action.run();
		}
		else if(!joy.getRawButton(button))
		{
			pre = false;
		}
	}
	
	public double getAxisWithDeadzone(int axis, double deadzone, boolean inverted)
	{ //Returns a double value of the chosen axis. If the axis is within the chosen deadzone, method returns 0.
		double axisthing = 0;
		if((joy.getRawAxis(axis) <= deadzone) && (joy.getRawAxis(axis) >= -deadzone))
    	{
			axisthing = 0;
    	}
    	else
    	{
    		if(inverted)
    		{ //Gets inverted value of axis
    			axisthing = -joy.getRawAxis(axis);
    		}
    		else
    		{ //Gets raw value of axis
    			axisthing = joy.getRawAxis(axis);
    		}	
    	}
		return axisthing;
	}
}
