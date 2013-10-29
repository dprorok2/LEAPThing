package sigmusic.thing.theremin;

import java.awt.Color;

import sigmusic.color.RandomColorEaserLooper;
import sigmusic.thing.ProcessingThing;
import sigmusic.thing.interfaces.IDrawableThing;

public class ThereminThing implements IDrawableThing{
	private ProcessingThing pt;
	private RandomColorEaserLooper rcet = new RandomColorEaserLooper(300);
	private int height;
	private int lengthLim;
	private Laser[] lasers;
	private final int numLasers=5;
	
	public ThereminThing(ProcessingThing p) {
		pt = p;
		height = p.displayHeight;
		lengthLim=height/2;
		lasers = new Laser[numLasers];
		for(int i=0; i<numLasers;i++)
		{
			lasers[i]= new Laser((int)((i+.5)*pt.displayWidth/numLasers), lengthLim, (int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
		}

	}
	
	public void drawLaser(Laser l){
		pt.stroke(l.getR(),l.getG(),l.getB(), 175);
		pt.fill(l.getR(),l.getG(),l.getB(), 175);
		if(l.length()<lengthLim)
		{
		int dx=(int)(Math.random()*l.width()-l.width()/2);
		l.laser[l.length()]= new Photon(l.location()+dx, height, l.photonSize);
		l.length(1);//adds 1 to length
		}
		for (int i =0; i<l.length(); i++)
		{
			Photon p = l.laser[i];
			p.move();
			if (p.getY()<-200)
			{
				p.setY(height);
				p.invisible=false;
			}
			//(Math.abs(p.getY()-pt.mouseY)<30)
			if(p.getY()-pt.mouseY<15&&p.getY()-pt.mouseY>-50&&(Math.abs(p.getX()-pt.mouseX)<50))
				p.invisible=true;
			
			if(!p.invisible)
			{
			pt.rect(p.getX(), p.getY(),p.size(),p.size()*60);
			}
		}
	}
	
	public void draw() {
		drawGrid();
		for (int i=0; i<numLasers;i++)
		{
			drawLaser(lasers[i]);
		}
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
