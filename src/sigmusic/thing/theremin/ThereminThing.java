package sigmusic.thing.theremin;

import sigmusic.thing.ProcessingThing;

public class ThereminThing {
	private ProcessingThing pt;
	
	
	public ThereminThing(ProcessingThing p) {
		pt = p;
	}
	
	public void draw() {
		drawGrid();
	}
	
	private void drawGrid() {
		
		int octaveSpacing = pt.displayHeight/ProcessingThing.OCTAVES;
		int noteSpacing = octaveSpacing/12;
		int currOctave = ProcessingThing.OCTAVES;
		
		for(int i = 1; i < ProcessingThing.OCTAVES; i++) {
			pt.stroke(10, 255, 10, 70);
			pt.line(0, octaveSpacing*i, pt.displayWidth, octaveSpacing*i);
		}
		
		for(int i = 1; i < ProcessingThing.OCTAVES*12; i++) {
			pt.stroke(10, 255, 90, 30);
			pt.line(0, noteSpacing*i, pt.displayWidth, noteSpacing*i);
		}
	}
}
