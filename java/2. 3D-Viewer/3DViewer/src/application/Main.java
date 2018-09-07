package application;
	
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Point3D;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class Main extends Application {
	
	Scene scene;
	Point3D[][] pointCloud;
	
	CameraController camController;
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			scene = new Scene(root, 1600, 800);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			Xform group = new Xform();
			
			PhongMaterial material1 = new PhongMaterial();
			material1.setDiffuseColor(Color.BLUE);
			material1.setSpecularColor(Color.BLUE);
			material1.setSpecularPower(10.0);
			
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Scan File (.txt)");
			
			File file = fileChooser.showOpenDialog(primaryStage);
			
			if(file == null)System.exit(0);
			
			BufferedReader br = new BufferedReader(new FileReader(file));	
			int x = Integer.parseInt(br.readLine().substring(6));
			int y = Integer.parseInt(br.readLine().substring(7));
			
			pointCloud = new Point3D[y][x];
			
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
					
					pointCloud[i][j] = new Point3D(posX, posY, posZ);
					
					Box box  = new Box(1, 1, 1);
					
					double f = (float) Math.sqrt(posX*posY*posZ);
					System.out.println(f);
					
					Color c = new Color(0, 1, 0, 1);
					
					
					material1.setDiffuseColor(c);
					material1.setSpecularColor(c);
					box.setMaterial(material1);
					
					box.setTranslateX(posX);
					box.setTranslateY(posY);
					box.setTranslateZ(posZ);
					
					group.getChildren().add(box);
				}
				
				group.setRotateX(-90);
				
				
			}
			
			br.close();
			
			AmbientLight light=new AmbientLight(new Color(1, 1, 1, 1));
			light.setTranslateX(-1.80);
			light.setTranslateY(-0.90);
			light.setTranslateZ(-1.20);

			PointLight light2=new PointLight(new Color(1, 1, 1, 1));
			light2.setTranslateX(1.80);
			light2.setTranslateY(1.90);
			light2.setTranslateZ(1.80);
	
			group.getChildren().addAll(light,light2);
		
			scene.setFill(Color.BLACK);
			
			camController = new CameraController();
			camController.setSPEED(5f);
			
			root.getChildren().add(camController.getXform());
			
			scene.addEventHandler(MouseEvent.ANY, camController.getMouseHandler());
			scene.addEventHandler(KeyEvent.ANY, camController.getKeyHandler());
			scene.setCamera(camController.getCamera());
			
			root.getChildren().addAll(group);
			

			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public TriangleMesh getMeshFromPointCloud()
	{
		TriangleMesh mesh = new TriangleMesh();
		for(int i = 0; i < pointCloud.length; i++)
		{
			for(int j = 0; j < pointCloud[0].length; j++)
			{
				float[] f = {
						(float) pointCloud[i][j].getX(),
						(float) pointCloud[i][j].getY(),
						(float) pointCloud[i][j].getZ()
				};
				mesh.getPoints().addAll(f);
			}
		}
		return mesh;
	}
	
	public static void main(String[] args) {
		launch(args);		
	}
}
