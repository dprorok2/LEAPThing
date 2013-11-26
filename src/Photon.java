


public class Photon {
	private int x;
	private int y;
	private int size;
	private int speed;
	public boolean invisible = false; // true when blocked by hand
	
	public Photon(int x, int y, int size){
	this.x=x;
	this.y=y;
	this.size=size;
	speed = (int)(Math.random()*20+30);
}

	public void move(){
		y-=speed;
	}
	
	public int size(){return size;}
	public int getX(){return x;}
	public int getY(){return y;}
	public void setY(int y){this.y=y;}
}
