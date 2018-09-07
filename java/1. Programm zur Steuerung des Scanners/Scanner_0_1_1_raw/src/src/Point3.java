package src;

public class Point3 
{
	public float x, y, z;
	
	public Point3(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Point3()
	{
		x = 0;
		y = 0;
		z = 0;
	}
	
	public void calculatePoint(float dis, float angleY, float angleZ)
	{
		x = (float) (dis * Math.cos(Math.toRadians(angleY)) * Math.cos(Math.toRadians(angleZ)));
		y = (float) (dis * Math.cos(Math.toRadians(angleY)) * Math.sin(Math.toRadians(angleZ)));
		z = (float) (dis * Math.sin(Math.toRadians(angleY)));
	}
	
	public String toString()
	{
		return "x: " + x + " y: " + y + " z: " + z;
	}

}
