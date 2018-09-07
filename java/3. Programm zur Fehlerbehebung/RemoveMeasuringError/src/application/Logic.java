package application;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PointLight;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class Logic 
{
	Measurment[][] pointCloud_raw;
	Measurment[][] pointCloud;
	
	public Logic(File file)
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(file));	
			int x = Integer.parseInt(br.readLine().substring(6));
			int y = Integer.parseInt(br.readLine().substring(7));
			
			pointCloud = new Measurment[x][y];
			
			for(int i = 0; i < y; i++)
			{
				String s = br.readLine();
				String[] points = s.split("#");
				
				for(int j = 0; j < x; j++)
				{
					String[] point = points[j].split("_");
					float posX = Float.parseFloat(point[0]);
					float posY = Float.parseFloat(point[1]);
					float posZ = Float.parseFloat(point[2]);
					
					pointCloud[j][i] = new Measurment(posX, posY, posZ);
				}
			}
			br.close();
		}
		catch(Exception e){e.printStackTrace();}

		pointCloud_raw = pointCloud;
	}
	
	public void removeMeasuringError(float maxDistance)
	{
		Measurment[] temp = new Measurment[9];
		Measurment[] points;
		
		int size;
		
		for(int x = 0; x < pointCloud_raw.length; x++)
		{
			for(int y = 0; y < pointCloud_raw[0].length; y++)
			{
				size = 1;
				
				temp[0] = pointCloud_raw[x][y];
				
				for(int i = 0; i < 3; i++)
				{
					for(int j = 0; j < 3; j++)
					{
						if(i != 1 && j != 1)
						{
							try
							{
								temp[size] = pointCloud_raw[x-1+i][y-1+j];
								size++;
							}
							catch(ArrayIndexOutOfBoundsException e){}
							
						}
					}
				}
				
				points = new Measurment[size];
				
				for(int i = 0; i < size; i++)
				{
					points[i] = temp[i];
				}
				
				pointCloud[x][y] = checkPoint(points, maxDistance);
			}
		}
	}
	
	public Measurment checkPoint(Measurment[] points, float maxDistance)
	{
		//points[0] ist der zu überprüfende Punkt. Die restlichen Punkte sind die umliegenden Punkte
		
		float a = 0;		//Durchschnittswert für x
		float b = 0;		//Durchschnittswert für y
		float c = 0;		//Durchschnittswert für z
		
		for(int i = 1; i < points.length; i++)
		{
			a += points[i].getX();
			b += points[i].getY();
			c += points[i].getZ();
		}
		
		a = a / (points.length - 1);
		b = b / (points.length - 1);
		c = c / (points.length - 1);
		
		//Überprüfung der Entfernung zum Durchschnittspunkt ( Math.abs(x) entspricht |x| )
		
		if(Math.abs(points[0].getX() - a) < maxDistance && Math.abs(points[0].getY() - b) < maxDistance && Math.abs(points[0].getZ() - c) < maxDistance)return points[0];
		
		//Überprüfung der Entfernungen zu den einzelnen umliegenden Punkten
		
		for(int i = 1; i < points.length; i++)
		{
			if(Math.abs(points[0].getX() - points[i].getX()) < maxDistance && Math.abs(points[0].getY() - points[i].getY()) < maxDistance && Math.abs(points[0].getZ() - points[i].getZ()) < maxDistance)return points[0];
		}
		
		return new Measurment(a, b, c);
	}
}
