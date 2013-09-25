package sigmusic.leap;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.Gesture.Type;
import com.leapmotion.leap.HandList;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.Pointable;
import com.leapmotion.leap.Vector;

public class LeapListener extends Listener {

	public HandList lastHands;
	public Vector tapPosition;
	
	public void onInit(Controller controller) {
		controller.enableGesture(Type.TYPE_KEY_TAP);
		System.out.println("tap gesture enabled");
	}

	public void onConnect(Controller controller) {
		System.out.println("Connected");
	}

	public void onDisconnect(Controller controller) {
		System.out.println("Disconnected");
	}

	public void onFrame(Controller controller) {
		for (Gesture g : controller.frame().gestures()) {
			if (g.type() == Type.TYPE_KEY_TAP && g.isValid()) {
				for (Pointable p : g.pointables()) {
					Vector v = p.tipPosition();
					System.out.println("Tapped at (" + v.getX() + ", "
							+ v.getY() + ", " + v.getZ() + ")");
					
					tapPosition = v;
				}
			} else {
				System.out.println("Interesting...");
			}
		}
		lastHands = controller.frame().hands();
	}
}
