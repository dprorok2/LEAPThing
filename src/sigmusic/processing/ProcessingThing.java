package sigmusic.processing;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import processing.core.PApplet;
import processing.core.PConstants;
import sigmusic.leap.LeapListener;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.HandList;

public class ProcessingThing extends PApplet {

	/**
	 * Generated
	 */
	private static final long serialVersionUID = 3183795322040170994L;

	public int screenWidth;
	public int screenHeight;

	static LeapListener listener;
	static Controller controller;

	public static void main(String args[]) {
		listener = new LeapListener();
		controller = new Controller(listener);
		
		BufferedReader i = new BufferedReader( new InputStreamReader(System.in));
		try {
			i.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//PApplet.main(new String[] { "--present",
		//		"sigmusic.processing.ProcessingThing" });
	}

	@Override
	public void setup() {
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

		screenWidth = d.width;
		screenHeight = d.height;

		size(screenWidth, screenHeight);
		background(0);

		

		this.ellipseMode(PConstants.CENTER);
	}

	@Override
	public void draw() {
		HandList hands = listener.lastHands;

		if (hands != null) {
			for (int i = 0; i < hands.count(); i++) {
				Hand h = hands.get(i);
				fill(255);
				ellipse(translateX(h.sphereCenter().getX()), h.sphereCenter().getY(), 10,
						10);
			}
		}
	}

	public float translateX(float x) {
		if(x < -300) {
			return -300;
		}
		
		if(x > 300) {
			return 300;
		}
		
		return x + (float)(displayWidth)/600 + 300f;
	}
	
	public float translateY(float y) {
		return 0f;
	}
	
	
}
