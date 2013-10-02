package sigmusic.thing.debug;

import processing.core.PFont;
import sigmusic.thing.ProcessingThing;

public class DebugOverlay {
	private ProcessingThing pt;
	
	public DebugOverlay(ProcessingThing p) {
		pt = p;
	}
	
	public void draw() {
		pt.fill(100, 120, 70);
		pt.text("FPS: " + pt.frameRate, 15, 15);
	}
}
