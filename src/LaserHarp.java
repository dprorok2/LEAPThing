



import java.awt.Color;
import java.awt.Graphics;

import processing.core.PVector;

import com.leapmotion.leap.Vector;


public class LaserHarp {
	private Processor pt;
	private Graphics g;
	private RandomColorEaserLooper rcel = new RandomColorEaserLooper(300);
	private int height;
	private int lengthLim;
	private Laser[] lasers;
	private final int numLasers=8;
	private final int MAX_HANDS;
	//private Font noteFont = new Font("Arial", Font.BOLD, 60);
	

	public LaserHarp(Processor p) {
		pt = p;
		MAX_HANDS = pt.MAX_HANDS;
		//pt.setFont(noteFont);
		g = p.getGraphics();
		height = p.displayHeight;
		lengthLim=height/3;
		lasers = new Laser[numLasers];
		for(int i=0; i<numLasers;i++)
			lasers[i]= new Laser((int)((i+.5)*pt.displayWidth/numLasers), lengthLim, 
			(int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255), pt, numLasers);

		
	}
	
	public void drawLasers(){
		//draws photons
		for(Laser l: lasers)
		{
		pt.stroke(l.getR(),l.getG(),l.getB(), 175);
		pt.fill(l.getR(),l.getG(),l.getB(), 175);

		//spawns new photons each loop until limit is reached
		if(l.length<=lengthLim-3)
		{
			l.addPhoton(3);
		}
		
		//moves and draws photons
		l.stepPhotons();	
		}
		//draws bases of lasers
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
			//pt.getGraphics().drawString(lasers[i].getNote(),lasers[i].location()-lasers[i].width()/2+5, height-30);
		}
	}
	
	//oval shows hand depth.  note will play when oval becomes filled.
	public void drawHand(PVector hand)
	{
		g.setColor(new Color (255,255,255));
		if(hand.z<100)
			g.fillOval((int)hand.x-10,(int)hand.y, (int)(100*(400-hand.z)/400), (int)(30*(400-hand.z)/400));
		else 
			g.drawOval((int)hand.x-10,(int)hand.y, (int)(100*(400-hand.z)/400), (int)(30*(400-hand.z)/400));
	}
	
	public void findHands()
	{
		//resets to determine if note should still play
		for (Laser l: lasers)l.blocked=false;
		for (OSCServer s: Processor.server) s.pitch = 0;
		
		for (int f = 0; f<pt.leap.getHandList().size()&&f<MAX_HANDS;f++)
		{
			Vector hand0 = null; PVector hand = null;
			
			try{	//in case hand has been removed	
			hand0 = pt.leap.getHand(f).palmPosition();
			hand = pt.leap.convertLeapToScreenDimension(hand0.getX(), hand0.getY(), hand0.getZ());
			}
			catch (IndexOutOfBoundsException e){
				continue;//try next hand
			}
			
			drawHand(hand);
			if(hand!=null&& hand.z<100)
			{
				for (Laser l: lasers)
				{
					//if hand is above laser, see which photons hit it
					if (Math.abs(l.location()-(hand.x+50))<75)
					{
						for(Photon p: l.laser)
						{
							if (p!=null && p.getY()-hand.y<50&&p.getY()-hand.y>-50)
							{
								p.invisible=true;	
								l.blocked = true;
								//this hand's server should play this laser's pitch
								Processor.server[f].pitch=l.pitch;
							} 
						}
						break;//hand can only be above one laser
					}
				}
			}
		}
		
		//pitch will be 0 if that hand is not blocking a laser, else it plays that laser
		for (OSCServer s: Processor.server)
			if (s.pitch ==0)s.sendPitch(s.pitch, 0);
			else s.sendPitch(s.pitch, .05f);
	}
	
	public void draw() {
		drawBackground();
		findHands();//determines which photons are blocked and which notes should play, also draws hands
		drawLasers();
	}
	
	private void drawBackground() {
		Color c = rcel.getNextColor();
		int octaveSpacing = pt.displayHeight/6;
		int noteSpacing = octaveSpacing/12;
		
		for(int i = 1; i < 6; i++) {
			pt.stroke(c.getRed(), c.getGreen(), c.getBlue(), 70);
			pt.line(0, octaveSpacing*i, pt.displayWidth, octaveSpacing*i);
		}
		
		for(int i = 1; i < 6*12; i++) {
			pt.stroke(c.getRed(), c.getGreen(), c.getBlue(), 30);
			pt.line(0, noteSpacing*i, pt.displayWidth, noteSpacing*i);
		}
	}
	
}
