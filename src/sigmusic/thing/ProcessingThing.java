package sigmusic.thing;

import java.awt.Dimension;
import java.awt.Toolkit;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

import com.leapmotion.leap.Finger;
import com.onformative.leap.LeapMotionP5;

public class ProcessingThing extends PApplet {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = 3183795322040170994L;
	private static final int MIDI_RANGE = 127;
	private int screenWidth;
	private int screenHeight;
	private static int port;
	private static OSCServerThing server;
	private Finger currFinger;
	
	private static LeapMotionP5 leap;
	
	public static void main(String args[]) {
		PApplet.main(new String[] { "--present",
		"sigmusic.thing.ProcessingThing" });
	}

	@Override
	public void setup() {
		port = 9001;
		server = new OSCServerThing(port, "");
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

		screenWidth = d.width;
		screenHeight = d.height;

		this.size(screenWidth, screenHeight);

		leap = new LeapMotionP5(this);
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
			server.sendPitch(normalizePitch(tip.y));
		}
	}
	
	@Override
	public void keyPressed() {
		if(this.key == PConstants.ESC) {
			int i = 0;
			i++;
		}
	}
	
	@Override
	public void stop() {
		super.stop();
		leap.stop();
	}
	
	public float normalizePitch(float y) {
		y = screenHeight - y;
		y = (((float)MIDI_RANGE)/screenHeight)*y;
		return y;
	}
}
