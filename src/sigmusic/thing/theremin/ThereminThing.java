package sigmusic.thing.theremin;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;

import javax.imageio.ImageIO;

import sigmusic.color.RandomColorEaserLooper;
import sigmusic.thing.ProcessingThing;
import sigmusic.thing.interfaces.IDrawableThing;

public class ThereminThing implements IDrawableThing{
	private ProcessingThing pt;
	private Graphics g;
	private RandomColorEaserLooper rcet = new RandomColorEaserLooper(300);
	private int height;
	private int lengthLim;
	private Laser[] lasers;
	private final int numLasers=8;
	private boolean onLaser = false;
	private Font noteFont = new Font("Arial", Font.BOLD, 60);
	

	public ThereminThing(ProcessingThing p) {
		pt = p;
		pt.setFont(noteFont);
		g = p.getGraphics();
		height = p.displayHeight;
		lengthLim=height/4;
		lasers = new Laser[numLasers];
		for(int i=0; i<numLasers;i++)
		{
			lasers[i]= new Laser((int)((i+.5)*pt.displayWidth/numLasers), lengthLim, 
			(int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
			int note = (int)((2*(double)lasers[i].location()*numLasers/pt.displayWidth)+60);
			char c='A';
			if (note==60||note==74)c='C'; 
			else if(note==62)c='D';
			else if(note==64)c='E';
			else if(note==66)c='F';
			else if(note==68)c='G';
			else if(note==70)c='A';
			else if(note==72)c='B';
			lasers[i].setNote(c);
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
			if(pt.mousePressed&&(p.getY()-pt.mouseY<15&&p.getY()-pt.mouseY>-50&&(Math.abs(p.getX()-pt.mouseX)<28)))
			{
				p.invisible=true;
				int pitch = (int)(2*(double)l.location()*numLasers/pt.displayWidth)+60;
				if(pitch>64)pitch--;
				if (pitch>72)pitch--;
				ProcessingThing.server.sendPitch(pitch);
				onLaser=true;
			}
			
			if(!p.invisible)
			{
			pt.rect(p.getX(), p.getY(),p.size(),p.size()*60);
			}
		}
	}
	
	public void draw() {
		//drawGrid();
		onLaser=false;
		for (int i=0; i<numLasers;i++)
		{
			drawLaser(lasers[i]);
		}
		for (int i=0; i<numLasers;i++)
		{
			if(i%2==0)
			{
				pt.stroke(218,165,32);
				pt.fill(218,165,32);
			}
			else
			{
				pt.stroke(227, 228, 229);
				pt.fill(227, 228, 229);
			}
			pt.rect(lasers[i].location()-lasers[i].width()/2-10, height-100, lasers[i].width()+20, 100);
			//g.drawString(lasers[i].getNote(),lasers[i].location()-lasers[i].width()/2+5, height-30);
		}
		if(!onLaser)
			ProcessingThing.server.sendPitch(0);
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
