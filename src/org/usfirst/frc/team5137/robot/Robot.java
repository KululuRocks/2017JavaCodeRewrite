/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5137.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;

public class Robot extends TimedRobot {
	
    Joystick marinara;
    
    Victor leftDrive;
    Victor rightDrive;
    
    Spark slideDrive;
    Spark shooter;
    Spark climber;
    Spark feeder;
    Spark intake;
    
    int driveMode; // 0 is arcade, 1 is tank
    
    public void robotInit() {
    	marinara = new Joystick(0);
    	
    	//maybe leftDrive should be inverted instead?
    	leftDrive = new Victor(0);
    	rightDrive = new Victor(1);
    	rightDrive.setInverted(true);
    	
    	slideDrive = new Spark(2);
    	shooter = new Spark(3);
    	climber = new Spark(4);
    	feeder = new Spark(5);
    	intake = new Spark(6);
    	intake.setInverted(true);
    	
        driveMode = 0; //initiates to arcade drive mode
    }

	public void teleopPeriodic() {
	    
	    //double-check these
	    double leftStickZValue = marinara.getRawAxis(0);
		double leftStickYValue = marinara.getRawAxis(1);
		double rightStickZValue = marinara.getRawAxis(2);
		double rightStickYValue = marinara.getRawAxis(3);
		
		//double-check these too
		boolean upIsPressed = marinara.getRawButton(5);
		//boolean rightIsPressed = marinara.getRawButton(6);
		//boolean downIsPressed = marinara.getRawButton(7);
		//boolean leftIsPressed = marinara.getRawButton(8);
		
		boolean triangleIsPressed = marinara.getRawButton(13);
		boolean circleIsPressed = marinara.getRawButton(14);
		boolean crossIsPressed = marinara.getRawButton(15);
		boolean squareIsPressed = marinara.getRawButton(16);
   
		if (triangleIsPressed) driveMode = 0; // triangle button triggers arcade drive mode
		if (circleIsPressed) driveMode = 1; // circle button triggers tank drive mode}
		
		switch (driveMode) {
			case 0: // if arcade drive (gonna have to change sign values)
				leftDrive.set(0.5 * leftStickYValue + 0.5 * rightStickZValue);
				rightDrive.set(0.5 * leftStickYValue - 0.5 * rightStickZValue);
				slideDrive.set(leftStickZValue);
				break;
			case 1: // if tank drive
				leftDrive.set(leftStickYValue);
				rightDrive.set(rightStickYValue);
				slideDrive.set(0); // probably unnecessary, but just in case
				break;
			default: break;
		}
		
		// cross button activates intake when held
		if (crossIsPressed) {
			intake.set(0.5);
		} else {
			intake.set(0);
		}
		
		// square button activates climber when held
		if (squareIsPressed) {
			climber.set(1);
		} else {
			climber.set(0);
		}		
		
		// up button activates shooter when held
		if (upIsPressed) {
			shooter.set(1);
			Timer.delay(0.5);
			feeder.set(1);
		} else {
			shooter.set(0);
			feeder.set(0);
		}
		
	}
}

