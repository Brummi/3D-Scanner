package application;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

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
import javafx.scene.control.TextField;

public class Main extends Application {
	
	Stage stage;
	FileChooser fileChooser;
	File scanResult;
	TextField maxDepthField;
	Point3D[][] pointCloud;
			
	public void start(Stage primaryStage) {
		try {
			
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("layout.fxml"));
            
			Parent root = loader.load();
			
			Scene scene = new Scene(root);
			
			Button open = (Button) scene.lookup("#open");
			Button save = (Button) scene.lookup("#save");
			maxDepthField = (TextField) scene.lookup("#maxDepth");
			
			open.setOnAction(new EventHandler<ActionEvent>(){public void handle(ActionEvent event) {open();}});
			save.setOnAction(new EventHandler<ActionEvent>(){public void handle(ActionEvent event) {try {
				save();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}});
			
			stage = primaryStage;
			
			stage.setScene(scene);
			
			stage.setTitle("Scanresult to Depthimage Converter");
			
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
		fileChooser.setTitle("Save depthimage");
		fileChooser.setInitialDirectory(scanResult.getParentFile());
		File save = fileChooser.showSaveDialog(stage);
		if(save == null)return;
		
		float[][] depth = new float[pointCloud.length][pointCloud[0].length];
		float maxDepth = 0;
		int maxmaxDepth = 0;
		try
		{
			maxmaxDepth = Integer.parseInt(maxDepthField.getText());
		}catch(Exception e){maxmaxDepth = 1000;}
		
		
		for(int i = 0; i < pointCloud.length; i++)
		{
			for(int j = 0; j < pointCloud[0].length; j++)
			{
				depth[i][j] = (float) Math.sqrt(Math.pow(pointCloud[i][j].getX(), 2) + Math.pow(pointCloud[i][j].getY(), 2) + Math.pow(pointCloud[i][j].getZ(), 2));
				if(depth[i][j] > maxmaxDepth)depth[i][j] = maxmaxDepth;
				if(depth[i][j] > maxDepth)maxDepth = depth[i][j];
			}
		}
		
		BufferedImage depthImage = new BufferedImage(pointCloud.length, pointCloud[0].length, BufferedImage.TYPE_INT_RGB);
		
		Color color = Color.WHITE;
		float value = 0;
		
		for(int i = 0; i < pointCloud.length; i++)
		{
			for(int j = 0; j < pointCloud[0].length; j++)
			{
				value = (1 - depth[i][j] / maxDepth) * 255;
				color = new Color((int)value, (int)value, (int)value);
				depthImage.setRGB(i, j, color.getRGB());
				
			}
		}
		ImageIO.write(depthImage, "png", save);
	}
}
