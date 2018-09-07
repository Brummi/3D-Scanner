package src;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Scanner_0_1 
{
	SerialConnection scon;
	Scanner console;
	static boolean dataIn;
	static Scanner input;
	static OutputStream output;
	final int ticksPerRotation = 4350;
	final int maxRange = 4000;
	static boolean inScan;
	static ScanProcedure scanProcedure;
	
	public Scanner_0_1()
	{
		scon = new SerialConnection("COM5", 9600);
		console = new Scanner(System.in);
		input = scon.getScanner();
		output = scon.getOutputStream();
		
		inScan = false;
		
		System.out.println("Wait for scanner to be ready");
		
		while(true)
		{
			if(inScan)
			{
				if(!scanProcedure.scan)
				{
					saveScan(scanProcedure.result);
					inScan = false;
					scon.closeSerialPort();
					System.exit(0);
				}
			}
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
//		while(true)loop();
	}
	
	public static void main(String[] args)
	{
		Scanner_0_1 s = new Scanner_0_1();
	}
	
	public void loop()
	{
		if(console.hasNextLine())
		{
			String s = console.nextLine();
			try 
			{
				output.write(s.getBytes());
			} 
			catch (IOException e) {e.printStackTrace();}
			
		}
	}
	
	public static void dataIn()
	{
//		dataIn = true;
//		System.out.println(input.nextLine());
		String inputString = input.nextLine();
		if(inputString != "")
		{
			if(!inScan)
			{
				scanProcedure = new ScanProcedure(output, 600, 600, 5.0f);
				inScan = true;
				System.out.println("Start scanprocedure");
				
				
			}
			else scanProcedure.continueScan(inputString);
		}	
	}
	
	public void saveScan(Point3[][] result)
	{
		try
		{
			File file = new File("/users/felix/desktop/scanResult.txt");
	
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
	
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			PrintWriter pw = new PrintWriter(fw);
			
			pw.println("width:" + result[0].length);
			pw.println("height:" + result.length);
			
			for(Point3[] points: result)
			{
				for(Point3 point: points)
				{
					pw.print(point.x + "_" + point.y + "_" + point.z + "#");
				}
				pw.println();
			}
			
			pw.close();
	
	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

