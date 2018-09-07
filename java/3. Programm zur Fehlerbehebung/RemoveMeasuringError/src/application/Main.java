package application;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {
	
	Stage stage;
	FileChooser fileChooser;
	File scanResult;
	TextField maxDepthField;
	
	Logic logic;
			
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
			
			stage.setTitle("Scanresult remove measuring errors");
			
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
				logic = new Logic(scanResult);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void save() throws Exception
	{
		System.out.println("Save");
		fileChooser.setTitle("Save edited File");
		fileChooser.setInitialDirectory(scanResult.getParentFile());
		File save = fileChooser.showSaveDialog(stage);
		if(save == null)return;
		
		int maxDis = 0;
		try
		{
			maxDis = Integer.parseInt(maxDepthField.getText());
		}catch(Exception e){maxDis = 10;}
		
		logic.removeMeasuringError(maxDis);
		
		FileWriter fw = new FileWriter(save.getAbsoluteFile());
		PrintWriter pw = new PrintWriter(fw);
		
		pw.println("width:" + logic.pointCloud.length);
		pw.println("height:" + logic.pointCloud[0].length);
		
		for(int y = 0; y < logic.pointCloud[0].length; y++)
		{
			for(int x = 0; x < logic.pointCloud.length; x++)pw.print(logic.pointCloud[x][y].x + "_" + logic.pointCloud[x][y].y + "_" + logic.pointCloud[x][y].z + "#");
			pw.println();
		}
		
		pw.close();
	}
}
