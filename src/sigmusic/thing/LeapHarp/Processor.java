package sigmusic.thing.LeapHarp;


import java.awt.Dimension;
import java.awt.Toolkit;

import processing.core.PApplet;
import processing.core.PConstants;

import com.leapmotion.leap.Gesture.Type;
import com.onformative.leap.LeapMotionP5;

public class Processor extends PApplet {

	private static final long serialVersionUID = 3183795322040170994L;//Generated
	public final int MAX_HANDS = 5;
	private int screenWidth;
	private int screenHeight;
	private static int[] port;
	public static OSCServer[] server;
	private boolean displayLaserHarp = true;
	private LaserHarp laserHarp;
	public LeapMotionP5 leap;
	
	public static void main(String args[]) {
		System.loadLibrary("LeapJava");
		PApplet.main(new String[] { "--present",
		"sigmusic.thing.LeapHarp.Processor" });
	}

	@Override
	public void setup() {
		
		//Each possible hand has it's own server, connected to it's own port. Makes chords possible.
		port = new int[MAX_HANDS];
		server = new OSCServer[MAX_HANDS];
		for (int i = 0; i<MAX_HANDS;i++)
		{
			port[i]=9001+i;
			server[i]= new OSCServer(port[i], "");
		}
		
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

		screenWidth = d.width;
		screenHeight = d.height;

		this.size(screenWidth, screenHeight);

		leap = new LeapMotionP5(this);
		laserHarp = new LaserHarp(this);
		this.ellipseMode(PConstants.CENTER);
		//disabling unused gestures makes Leap more efficient
		leap.disableGesture(Type.TYPE_CIRCLE);
		leap.disableGesture(Type.TYPE_INVALID);
		leap.disableGesture(Type.TYPE_KEY_TAP);
		leap.disableGesture(Type.TYPE_SCREEN_TAP);
		leap.disableGesture(Type.TYPE_SWIPE);
	}

	@Override
	public void draw() {
		background(0);
		fill(255);
		
		if(displayLaserHarp) {
			laserHarp.draw();
		}
		
	}
	
	@Override
	public void keyPressed() {
		if(keyCode == PConstants.ESC) {
			for (OSCServer s : server)
			s.sendPitch(0,0);
			this.exit();
		}
	}
	
	@Override
	public void stop() {
		leap.stop();
	}
}
