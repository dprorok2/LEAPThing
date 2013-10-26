package sigmusic.thing.theremin;

import java.awt.Color;

import sigmusic.color.RandomColorEaserLooper;
import sigmusic.thing.ProcessingThing;
import sigmusic.thing.interfaces.IDrawableThing;

public class ThereminThing implements IDrawableThing{
	private ProcessingThing pt;
	private RandomColorEaserLooper rcet = new RandomColorEaserLooper(300);
	public ThereminThing(ProcessingThing p) {
		pt = p;
	}
	
	public void draw() {
		drawGrid();
	}
	
	private void drawGrid() {
		Color c = rcet.getNextColor();
		int octaveSpacing = pt.displayHeight/ProcessingThing.OCTAVES;
		int noteSpacing = octaveSpacing/12;
		
		for(int i = 1; i < ProcessingThing.OCTAVES; i++) {
			pt.stroke(c.getRed(), c.getGreen(), c.getBlue(), 70);
			pt.line(0, octaveSpacing*i, pt.displayWidth, octaveSpacing*i);
		}
		
		for(int i = 1; i < ProcessingThing.OCTAVES*12; i++) {
			pt.stroke(c.getRed(), c.getGreen(), c.getBlue(), 30);
			pt.line(0, noteSpacing*i, pt.displayWidth, noteSpacing*i);
		}
	}
}
