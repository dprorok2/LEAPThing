package com.flewp.leaptest;

import java.io.IOException;

import com.leapmotion.leap.Controller;


public class LeapTest {

	public static void main(String[] args) {
		 // Create a sample listener and assign it to a controller to receive events
	    LeapListener listener = new LeapListener();
	    
	    @SuppressWarnings("unused")
		Controller controller = new Controller(listener);
	    
	    // Keep this process running until Enter is pressed
	    System.out.println("Press Enter to quit...");
	    
	    try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}

	    //The controller must be disposed of before the listener
	    controller = null;
	}
}
