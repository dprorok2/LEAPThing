package sigmusic.thing.theremin;

public class Laser {
	public Photon[] laser;
	public final int photonSize = 2;
	private int length, location;
	private final int width = 50;
	private int r,g,b; //color components
	private String note;
	
	public Laser(int location, int lengthLim, int red, int green, int blue){
		laser = new Photon[lengthLim];
		this.location = location;
		r=red;
		g=green;
		b = blue;
	}

	public void setLaser(Photon[] laser) {
		this.laser = laser;
	}
	public void setNote(char c)
	{
		note=""+c;
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

	public int length(){return length;}
	public void length(int i){length+=i;}
}
