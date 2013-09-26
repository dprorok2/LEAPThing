package sigmusic.thing;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PVector;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.onformative.leap.LeapMotionP5;

public class ProcessingThing extends PApplet {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = 3183795322040170994L;
	
	private int screenWidth;
	private int screenHeight;
	private static int port;
	private static OSCServerThing server;
	private Finger currFinger;
	private PFont font;
	
	private static LeapMotionP5 leap;
	
	public static void main(String args[]) {
		
		PApplet.main(new String[] { "--present",
		"sigmusic.thing.ProcessingThing" });
		port = 9001;
		server = new OSCServerThing(port, "");
		
		BufferedReader in = new BufferedReader( new InputStreamReader(System.in));
		try {
			in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int n = 0; n < 100000; n++) {
			for(int i = 30; i < 100; i++) {
				server.sendPitch(i);
			}
		}
		
		in = new BufferedReader( new InputStreamReader(System.in));
		try {
			in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void setup() {
		port = 31337;
		server = new OSCServerThing(port, "");
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

		screenWidth = d.width/2;
		screenHeight = d.height/2;

		this.size(screenWidth, screenHeight);

		leap = new LeapMotionP5(this);
		font = this.createDefaultFont(10);
		this.ellipseMode(PConstants.CENTER);
	}

	@Override
	public void draw() {
		background(0);
		fill(255);
		boolean fingerFound = false;
		
		if(!leap.getFingerList().isEmpty()) {
			fingerFound = true;
			currFinger = leap.getFingerList().get(0);
		}
		
		if(fingerFound) {
			PVector tip = leap.getTip(currFinger);
			this.ellipse(tip.x, tip.y, 10, 10);
			server.sendPitch(tip.y);
		}
		
	}
}
