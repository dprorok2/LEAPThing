package sigmusic.thing.debug;

import java.util.ArrayList;

import sigmusic.thing.ProcessingThing;

import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Hand;
import com.onformative.leap.LeapMotionP5;

public class DebugOverlay {
	private ProcessingThing pt;
	private LeapMotionP5 leap;
	public DebugOverlay(ProcessingThing pt, LeapMotionP5 leap) {
		this.pt = pt;
		this.leap = leap;
	}

	public void draw() {
		pt.fill(100, 120, 70);
		pt.text("FPS: " + pt.frameRate, 15, 15);
		pt.text("Frames: " + pt.frameCount, 15, 30);
		pt.text("Hand Information: \n" + getHandInfo(), 15, 45);
	}
	
	public String getHandInfo() {
		StringBuilder sb = new StringBuilder();
		ArrayList<Hand> hands = leap.getHandList();
		for(Hand h : hands) {
			sb.append("Hand " + h.id() + ":\n");
			for(Finger f : h.fingers()) {
				sb.append("\tFinger " + f.id() + "@ (" + f.tipPosition().getX() + "," + f.tipPosition().getY() + ")\n");
			}
		}
		return sb.toString();
	}
}
