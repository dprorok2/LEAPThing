package sigmusic.color;

import java.awt.Color;
import java.util.Random;

public class RandomColorEaserLooper {
	private final Random generator = new Random(System.currentTimeMillis());
	private ColorEaser easer;
	private Color currColor;
	private Color nextColor;
	private int interval;

	public RandomColorEaserLooper(int interval) {
		this.interval = interval;
		nextColor = generateColor();
		easer = new ColorEaser(nextColor);
		refreshEaser();
	}

	private Color generateColor() {
		return new Color(generator.nextInt(256), generator.nextInt(256),
				generator.nextInt(256));
	}
	
	private void refreshEaser() {
		currColor = nextColor;
		nextColor = generateColor();
		easer.startColorEase(currColor, nextColor, interval);
	}

	public void update() {
		if(easer.getSteps() <= 0) {
			refreshEaser();
		}
		easer.update();
	}
	
	public Color getNextColor() {
		update();
		return easer.getCurrColor();
	}
}
