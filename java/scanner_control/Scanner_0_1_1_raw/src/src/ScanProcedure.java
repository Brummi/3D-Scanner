package src;

import java.io.IOException;
import java.io.OutputStream;

public class ScanProcedure 
{
	static OutputStream output;
	final int ticksPerRotation = 4350;
	final int maxRange = 4000;
	Point3[][] result;
	float mpt;
	int state = 0;
	String motor1Command, motor2Command;
	boolean finished;
	int x, y;
	int width, height;
	boolean scan;
	
	@SuppressWarnings("static-access")
	public ScanProcedure(OutputStream output, int width, int height, float MeasurmentsPerTick)
	{
		this.output = output;
		
		mpt = MeasurmentsPerTick;
		System.out.println(mpt);
		result = new Point3[(int)(height/mpt)+1][(int)(width/mpt)+1]; 
		
		this.width = width;
		this.height = height;
		
		x = (int) (-width/(2*mpt));
		y = (int) (-height/(2*mpt));
		
		if(x < 0)motor1Command = "m1" + "-" + (int)Math.abs(x * mpt) + ";";
		else motor1Command = "m1" + "+" + (int)(x * mpt) + ";";
		
		if(y < 0)motor2Command = "m2" + "-" + (int)Math.abs(y * mpt) + ";";
		else motor2Command = "m2" + "+" + (int)(y * mpt) + ";";
		
		try {
			System.out.println(motor1Command);
			output.write(motor1Command.getBytes());
		} catch (IOException e) {}
		
		nextState();
		
		scan = true;
	}
	
	public void continueScan(String input)
	{
		System.out.println("Input: " + input);
		if(scan)
		{
			if(state == 0)
			{
				String distanceString = input;
				
				int distance = Integer.parseInt(distanceString);
						
				System.out.println(distance);
				Point3 scanResult = new Point3();
				scanResult.calculatePoint(distance, (float)y*mpt/ticksPerRotation*360, (float)x*mpt/ticksPerRotation*360);
				System.out.println(scanResult);
				
				try{result[y + (int) (height/(2*mpt))][x + (int) (width/(2*mpt))] = scanResult;}
				catch(Exception e){e.printStackTrace();}
				
				if(y < (int)height/(2*mpt))y++;
				else
				{
					y = (int) (-height/(2*mpt));
					
					if(x < (int)width/(2*mpt))x++;
					else
					{
						scan = false;
						System.out.println("Scan completed");
						
						try {
							output.write("m1+0;".getBytes());
							output.write("m2+0;".getBytes());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return;
					}
				}
				
				if(x < 0)motor1Command = "m1" + "-" + (int)Math.abs(x * mpt) + ";";
				else motor1Command = "m1" + "+" + (int)(x * mpt) + ";";
				
				if(y < 0)motor2Command = "m2" + "-" + (int)Math.abs(y * mpt) + ";";
				else motor2Command = "m2" + "+" + (int)(y * mpt) + ";";
				
				try {
					System.out.println(motor1Command);
					output.write(motor1Command.getBytes());
				} catch (IOException e) {}
			}
			else if(state == 1)
			{
				try {
					System.out.println(motor2Command);
					output.write(motor2Command.getBytes());
				} catch (IOException e) {}
			}
			else if(state == 2)
			{
				try {
					output.write("d;".getBytes());
				} catch (IOException e) {}
			}
			
			nextState();
		}
	}
	
	private void nextState()
	{
		if(state < 2)state++;
		else state = 0;
	}
	
/*	public Point3[][] scan(int width, int height, float MeasurmentsPerTick)
	{
		float mpt = MeasurmentsPerTick;
		String motor1Command, motor2Command;
		Point3[][] result = new Point3[(int)(width/mpt)][(int)(height/mpt)]; 
		
		for(int x = (int) (-width/(2*mpt)); x < width/(2*mpt); x++)
		{
			for(int y = (int) (-height/(2*mpt)); y < height/(2*mpt); y++)
			{
				if(x < 0)motor1Command = "m1" + "-" + (int)Math.abs(x * mpt) + ";";
				else motor1Command = "m1" + "+" + (int)(x * mpt) + ";";
				
				if(y < 0)motor2Command = "m2" + "-" + (int)Math.abs(y * mpt) + ";";
				else motor2Command = "m2" + "+" + (int)(y * mpt) + ";";
				
				try {
					System.out.println(motor1Command);
					output.write(motor1Command.getBytes());
				} catch (IOException e) {}
				while(!dataIn){}
				dataIn = false;
				
				try {
					System.out.println(motor2Command);
					output.write(motor2Command.getBytes());
				} catch (IOException e) {}
				while(!dataIn){}
				dataIn = false;
				
				try {
					output.write("d;".getBytes());
				} catch (IOException e) {}
				while(!dataIn){}
				dataIn = false;
				
				String distanceString = input.nextLine();
				
				int distance = Integer.parseInt(distanceString);
						
				Point3 scanResult = new Point3();
				scanResult.calculatePoint(distance, y*mpt/ticksPerRotation, x*mpt/ticksPerRotation);
				System.out.println(scanResult);
				
				result[y + (int) (-height/(2*mpt))][x + (int) (-width/(2*mpt))] = scanResult;
			}
		}
		
		return result;
	} */
}
