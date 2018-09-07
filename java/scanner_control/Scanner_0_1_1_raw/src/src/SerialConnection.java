package src;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Enumeration;
import java.util.Scanner;

import gnu.io.*;

public class SerialConnection 
{
	boolean serialPortOpen;
	int baudrate;
	int dataBits;
	int stopBits;
	int parity;
	String portName;
	CommPortIdentifier serialPortId;
	Enumeration enumComm;
	SerialPort serialPort;
	OutputStream outputStream;
	InputStream inputStream;
	
	OutputStreamWriter writer;
	Scanner scanner;
	
	public SerialConnection(String comPort, int baudrate)
	{
		serialPortOpen = false;
		this.baudrate = baudrate;
		dataBits = SerialPort.DATABITS_8;
		stopBits = SerialPort.STOPBITS_1;
		parity = SerialPort.PARITY_NONE;
		
		portName = comPort;
		
		try 
		{
			openSerialPort();
		} 
		catch (Exception e) {e.printStackTrace();}
		
		writer = new OutputStreamWriter(outputStream);
		scanner = new Scanner(inputStream);
		
	}
	
	public void getData()
	{
//		String data = scanner.nextLine();
		Scanner_0_1.dataIn();
	}
	
	public OutputStream getOutputStream()
	{
		return outputStream;
	}
	
	public Scanner getScanner()
	{
		return scanner;
	}
	
	public void openSerialPort() throws Exception
	{
		if (serialPortOpen != false) {
			System.out.println("Serialport already open");
			return;
		}
		System.out.println("Open Serialport");
		enumComm = CommPortIdentifier.getPortIdentifiers();
		while(enumComm.hasMoreElements()) {
			serialPortId = (CommPortIdentifier) enumComm.nextElement();
			if (portName.contentEquals(serialPortId.getName())) {
				break;
			}
			else 
			{
				System.out.println("Serialport could not be found: " + portName);
				return;
			}
		}
		serialPort = (SerialPort) serialPortId.open(this.getClass().getName(), 1000);
		outputStream = serialPort.getOutputStream();
		inputStream = serialPort.getInputStream();
		serialPort.addEventListener(new serialPortEventListener());
		serialPort.notifyOnDataAvailable(true);
		serialPort.setSerialPortParams(baudrate, dataBits, stopBits, parity);
		serialPortOpen = true;
	}
	
	void closeSerialPort()
	{
		if ( serialPortOpen == true) {
			System.out.println("Close Serialport");
			serialPort.close();
			serialPortOpen = false;
		} else {
			System.out.println("Serialport already closed");
		}
	}
	
	class serialPortEventListener implements SerialPortEventListener {
		public void serialEvent(SerialPortEvent event) {
			switch (event.getEventType()) {
			case SerialPortEvent.DATA_AVAILABLE:
				System.out.print("");
				getData();
				break;
			case SerialPortEvent.BI:
			case SerialPortEvent.CD:
			case SerialPortEvent.CTS:
			case SerialPortEvent.DSR:
			case SerialPortEvent.FE:
			case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
			case SerialPortEvent.PE:
			case SerialPortEvent.RI:
			default:
			}
		}
	}
}
