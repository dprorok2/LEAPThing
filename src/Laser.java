



public class Laser {
	public Photon[] laser;
	Processor pt;
	public final int photonSize = 2;
	public int length;
	private int location;
	private final int width = 50;
	private int r,g,b; //color components
	private String note;
	public int pitch;
	public boolean blocked =false;
	
	public Laser(int location, int lengthLim, int red, int green, int blue, Processor pt, int numLasers){
		laser = new Photon[lengthLim];
		this.pt=pt;
		this.location = location;
		r=red;
		g=green;
		b = blue;
		pitch = (int)(2*(double)location*numLasers/pt.displayWidth)+60;
		if(pitch>64)pitch--;
		if (pitch>72)pitch--;

		if (pitch%12==0)note="C"; 
		else if(pitch%12==2)note="D";
		else if(pitch%12==4)note="E";
		else if(pitch%12==5)note="F";
		else if(pitch%12==7)note="G";
		else if(pitch%12==9)note="A";
		else if(pitch%12==11)note="B";
		
	}

	public void setLaser(Photon[] laser) {
		this.laser = laser;
	}
	public String getNote()
	{
		return note;
	}
	public int location() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getG() {
		return g;
	}


	public int getB() {
		return b;
	}


	public int size() {
		return photonSize;
	}

	public int width() {
		return width;
	}
	public void addPhoton(int i)
	{
		while(i>0)
		{
		int dx=(int)(Math.random()*width-width/2-photonSize/2);
		laser[length]= new Photon(location()+dx, pt.height, photonSize);
		length++;
		i--;
		}
	}
	public void stepPhotons()
	{
		for (int i =0; i<length; i++)
		{
			Photon p = laser[i];
			p.move();
			if (p.getY()<-200)
			{
				p.setY(pt.height);
				p.invisible=false;
			}
			if(!p.invisible)
			{
			pt.rect(p.getX(), p.getY(),p.size(),p.size()*60);
			}
		}
	}
}
