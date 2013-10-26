package sigmusic.thing;

import java.awt.Dimension;
import java.awt.Toolkit;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import sigmusic.thing.debug.DebugOverlay;
import sigmusic.thing.theremin.ThereminThing;

import com.leapmotion.leap.Finger;
import com.onformative.leap.LeapMotionP5;

public class ProcessingThing extends PApplet {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = 3183795322040170994L;
	
	public static final int MIDI_HI = 96;
	public static final int MIDI_LO = 24;
	public static final int MIDI_RANGE = MIDI_HI - MIDI_LO;
	public static final int OCTAVES = ProcessingThing.MIDI_RANGE/12;
	private int screenWidth;
	private int screenHeight;
	private static int port;
	private static OSCServerThing server;
	private Finger currFinger;
	private boolean displayDebug = false;
	private boolean displayTheremin = true;
	private DebugOverlay debug;
	private ThereminThing theremin;
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
		debug = new DebugOverlay(this, leap);
		theremin = new ThereminThing(this);
		
		this.ellipseMode(PConstants.CENTER);
	}

	@Override
	public void draw() {
		background(0);
		fill(255);
		boolean fingerFound = false;
		
		if(displayTheremin) {
			theremin.draw();
		}
		
		if(displayDebug) {
			debug.draw();
		}
		
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
		if(key == 'd' || key == 'D') {
			displayDebug = !displayDebug;
		}
		else if(keyCode == PConstants.ESC) {
			this.exit();
		}
	}
	
	@Override
	public void stop() {
		leap.stop();
	}
	
	public float normalizePitch(float y) {
		y = screenHeight - y;
		y = ((((float)MIDI_RANGE)/screenHeight)*y) + MIDI_LO;
		return y;
	}
}
