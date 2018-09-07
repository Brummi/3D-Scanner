package application;

public class Measurment 
{
	float x, y, z;
	float distance;
	boolean corrupt;
	
	public Measurment(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		
		distance = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
		distance = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
		distance = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
		distance = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public boolean isCorrupt() {
		return corrupt;
	}

	public void setCorrupt(boolean corrupt) {
		this.corrupt = corrupt;
	}
}
