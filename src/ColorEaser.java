


import java.awt.Color;

public class ColorEaser {
	private Color startColor;
	private Color currColor;
	private Color finalColor;
	private int totalSteps;
	private int currStep;
	
	public ColorEaser() {
		startColor = Color.black;
		currColor = Color.black;
		finalColor = Color.black;
		totalSteps = currStep = 0;
	}
	
	public ColorEaser(Color color) {
		startColor = color;
		currColor = color;
		finalColor = color;
		totalSteps = currStep = 0;
	}
	
	public void startColorEase(Color startColor, Color finalColor, int steps) {
		this.startColor = startColor;
		this.finalColor = finalColor;
		this.totalSteps = this.currStep = steps;
	}
	
	public void startColorEase(Color finalColor, int steps) {
		this.finalColor = finalColor;
		this.totalSteps = this.currStep = steps;
	}
	
	public void update() {
		--currStep;
		if(currStep <= 0) {
			currColor = finalColor;
		}
		else {
			int currRed = (int)(((finalColor.getRed() - startColor.getRed())/(float)totalSteps)*(totalSteps - currStep));
			int currBlue = (int)(((finalColor.getBlue() - startColor.getBlue())/(float)totalSteps)*(totalSteps - currStep));
			int currGreen = (int)(((finalColor.getGreen() - startColor.getGreen())/(float)totalSteps)*(totalSteps - currStep));
			currColor = new Color(startColor.getRed() + currRed, startColor.getGreen() + currGreen, startColor.getBlue() + currBlue);
		}
	}
	
	public Color getNextColor() {
		update();
		return currColor;
	}
	
	public Color getCurrColor() {
		return currColor;
	}
	
	public int getSteps() {
		return currStep;
	}
}
