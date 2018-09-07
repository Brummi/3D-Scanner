package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point3D;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class Main extends Application {
	
	Stage stage;
	FileChooser fileChooser;
	File scanResult;
	Point3D[][] pointCloud;
			
	public void start(Stage primaryStage) {
		try {
			
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("layout.fxml"));
            
			Parent root = loader.load();
			
			Scene scene = new Scene(root);
			
			Button open = (Button) scene.lookup("#open");
			Button save = (Button) scene.lookup("#save");
			
			open.setOnAction(new EventHandler<ActionEvent>(){public void handle(ActionEvent event) {open();}});
			save.setOnAction(new EventHandler<ActionEvent>(){public void handle(ActionEvent event) {try {
				save();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}});
			
			stage = primaryStage;
			
			stage.setScene(scene);
			
			stage.setTitle("Scanresult to Wavefront .obj Converter");
			
			fileChooser = new FileChooser();
			 
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private void open()
	{
		System.out.println("Open");
		fileChooser.setTitle("Open scanresult");
		try{
			fileChooser.setInitialDirectory(scanResult.getParentFile());
		}catch(Exception e){}
		scanResult = fileChooser.showOpenDialog(stage);
		if(scanResult != null)
		{
			try {
				calculate();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void calculate() throws Exception
	{
		BufferedReader br = new BufferedReader(new FileReader(scanResult));	
		int x = Integer.parseInt(br.readLine().substring(6));
		int y = Integer.parseInt(br.readLine().substring(7));
		
		pointCloud = new Point3D[x][y];
		
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
				
				pointCloud[j][i] = new Point3D(posX, posY, posZ);
			}
		}
	}
	
	private void save() throws Exception
	{
		System.out.println("Save");
		fileChooser.setTitle("Save obj file");
		fileChooser.setInitialDirectory(scanResult.getParentFile());
		File save = fileChooser.showSaveDialog(stage);
		if(save == null)return;
		BufferedWriter bw = new BufferedWriter(new FileWriter(save)); 
		
		for(int x = 0; x < pointCloud.length; x++)
		{
			for(int y = 0; y < pointCloud[0].length; y++)
			{
				bw.write("v " + (float)pointCloud[x][y].getX() + " " + (float)pointCloud[x][y].getY() + " " + (float)pointCloud[x][y].getZ());
				bw.newLine();
			}
		}
		
		bw.close();
	}
}
