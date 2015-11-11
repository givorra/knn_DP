package AA;

import java.lang.Math;

public class Coordenada 
{
	private int x, y;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public float distanciaEuclidea(Coordenada coord)
	{
		return (float)Math.sqrt(Math.pow((coord.getX()-x), 2) + Math.pow((coord.y - y), 2));
	}
	
	public String toString()
	{
		return x + "," + y;
	}

}
