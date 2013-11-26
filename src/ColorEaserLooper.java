


import java.awt.Color;

public class ColorEaserLooper {
	private ColorEaser easer;
	private Color[] colors;
	private int[] intervals;
	private int currColorIndex;
	private int currIntervalIndex;

	public ColorEaserLooper(Color[] colors, int[] intervals) {
		// There needs to be an exception thrown here because there will be an OutOfBoundsException
		// but throwing a RuntimeException might be a little harsh, consider throwing a custom exception,
		// this is a RuntimeException cause I don't want to surround every constructure with try/catch
		if(colors.length == 0 || intervals.length == 0) {
			throw new RuntimeException("Don't pass in empty arrays to a ColorEaserLooper!");
		}
		this.colors = colors;
		this.intervals = intervals;
		this.currColorIndex = 0;
		this.currIntervalIndex = 0;
		this.easer = new ColorEaser(colors[0]);
	}

	private void refreshEaser() {
		easer.startColorEase(colors[currColorIndex],
				colors[(currColorIndex + 1) % colors.length],
				intervals[currIntervalIndex]);
		
		currColorIndex = (currColorIndex + 1) % colors.length;
		currIntervalIndex = (currIntervalIndex + 1) % intervals.length;
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
