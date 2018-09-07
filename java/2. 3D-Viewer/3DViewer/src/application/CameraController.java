package application;

import javafx.event.EventHandler;
import javafx.scene.PerspectiveCamera;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class CameraController 
{
	public float SPEED, SENSITIVITY;
	public boolean invertY;
	public String FWD, BWD, LEFT, RIGHT, UP, DOWN;
	
	private PerspectiveCamera camera;
	private Xform xform;
	private float cameraX, cameraY, cameraZ;
	private float cameraRX, cameraRY, cameraRZ;
	private MouseHandler mouseHandler;
	private KeyHandler keyHandler;
	
	public CameraController()
	{
		camera = new PerspectiveCamera(true);
		
		camera.setNearClip(0.1);
		camera.setFarClip(1000.0);
		
		xform = new Xform();
		
		xform.getChildren().add(camera);
		
		cameraX = 0;
		cameraY = 0;
		cameraZ = 0;
		
		cameraRX = 0;
		cameraRY = 0;
		cameraRZ = 0;
		
		FWD = "W";
		BWD = "S";
		LEFT = "A";
		RIGHT = "D";
		UP = "E";
		DOWN = "Q";
		
		invertY = false;
		
		SPEED = 0.4f;
		SENSITIVITY = 0.2f;
		
		mouseHandler = new MouseHandler();
		keyHandler = new KeyHandler();
	}
	
	public void update()
	{
		xform.setTranslate(cameraX, cameraY, cameraZ);
		xform.setRotate(cameraRX, cameraRY, cameraRZ);
	}
	
	public void moveFWD()
	{
		cameraX += (float) Math.cos(Math.toRadians(cameraRX))*Math.cos(Math.toRadians(cameraRY-90))*SPEED;
		cameraY += (float) Math.cos(Math.toRadians(cameraRX+90))*SPEED;
		cameraZ += (float) Math.cos(Math.toRadians(cameraRX))*Math.cos(Math.toRadians(cameraRY))*SPEED;
		update();
	}
	
	public void moveBWD()
	{	
		cameraX -= (float) Math.cos(Math.toRadians(cameraRX))*Math.cos(Math.toRadians(cameraRY-90))*SPEED;
		cameraY -= (float) Math.cos(Math.toRadians(cameraRX+90))*SPEED;
		cameraZ -= (float) Math.cos(Math.toRadians(cameraRX))*Math.cos(Math.toRadians(cameraRY))*SPEED;
		update();
	}
	
	public void moveLEFT()
	{
		cameraX += (float) Math.cos(Math.toRadians(cameraRX))*Math.cos(Math.toRadians(cameraRY-90-90))*SPEED;
		cameraZ += (float) Math.cos(Math.toRadians(cameraRX))*Math.cos(Math.toRadians(cameraRY-90))*SPEED;
		update();
	}
	
	public void moveRIGHT()
	{
		cameraX += (float) Math.cos(Math.toRadians(cameraRX))*Math.cos(Math.toRadians(cameraRY-90+90))*SPEED;
		cameraZ += (float) Math.cos(Math.toRadians(cameraRX))*Math.cos(Math.toRadians(cameraRY+90))*SPEED;
		update();
	}
	
	public void moveUP()
	{
		cameraY += SPEED;
		update();
	}
	
	public void moveDOWN()
	{
		cameraY -= SPEED;
		update();
	}
	
	private class MouseHandler implements EventHandler<MouseEvent>
	{
		boolean clicked = false;
		double mouseX, mouseY;
		
		public void handle(MouseEvent event) 
		{
			if(event.isPrimaryButtonDown())
			{
				if(!clicked)clicked = true;
				else
				{
					if(!invertY)cameraRX += (event.getSceneY() - mouseY) * SENSITIVITY;
					else cameraRX -= (event.getSceneY() - mouseY) * SENSITIVITY;
					cameraRY -= (event.getSceneX() - mouseX) * SENSITIVITY;
					 
					
					System.out.println("CAMERA ANGLE:");
					System.out.println("X: " + cameraRX + "°");
					System.out.println("Y: " + cameraRY + "°");
					System.out.println("Z: " + cameraRZ + "°");
					System.out.println("---------------");
				}
				
				mouseX = event.getSceneX();
				mouseY = event.getSceneY();
				
				update();
			}
			else
			{
				clicked = false;
			}
		}
		
	}
	
	private class KeyHandler implements EventHandler<KeyEvent>
	{
		public void handle(KeyEvent event)
		{
			if(event.getCode().equals(KeyCode.getKeyCode(FWD)))moveFWD();
			if(event.getCode().equals(KeyCode.getKeyCode(BWD)))moveBWD();
			if(event.getCode().equals(KeyCode.getKeyCode(LEFT)))moveLEFT();
			if(event.getCode().equals(KeyCode.getKeyCode(RIGHT)))moveRIGHT();
			if(event.getCode().equals(KeyCode.getKeyCode(UP)))moveUP();
			if(event.getCode().equals(KeyCode.getKeyCode(DOWN)))moveDOWN();
		}
	}

	public float getSPEED() {
		return SPEED;
	}

	public void setSPEED(float sPEED) {
		SPEED = sPEED;
	}

	public float getSENSITIVITY() {
		return SENSITIVITY;
	}

	public void setSENSITIVITY(float sENSITIVITY) {
		SENSITIVITY = sENSITIVITY;
	}

	public boolean isInvertY() {
		return invertY;
	}

	public void setInvertY(boolean invertY) {
		this.invertY = invertY;
	}

	public String getFWD() {
		return FWD;
	}

	public void setFWD(String fWD) {
		FWD = fWD;
	}

	public String getBWD() {
		return BWD;
	}

	public void setBWD(String bWD) {
		BWD = bWD;
	}

	public String getLEFT() {
		return LEFT;
	}

	public void setLEFT(String lEFT) {
		LEFT = lEFT;
	}

	public String getRIGHT() {
		return RIGHT;
	}

	public void setRIGHT(String rIGHT) {
		RIGHT = rIGHT;
	}

	public String getUP() {
		return UP;
	}

	public void setUP(String uP) {
		UP = uP;
	}

	public String getDOWN() {
		return DOWN;
	}

	public void setDOWN(String dOWN) {
		DOWN = dOWN;
	}

	public PerspectiveCamera getCamera() {
		return camera;
	}

	public void setCamera(PerspectiveCamera camera) {
		this.camera = camera;
	}

	public Xform getXform() {
		return xform;
	}

	public void setXform(Xform xform) {
		this.xform = xform;
	}

	public float getCameraX() {
		return cameraX;
	}

	public void setCameraX(float cameraX) {
		this.cameraX = cameraX;
		update();
	}

	public float getCameraY() {
		return cameraY;
	}

	public void setCameraY(float cameraY) {
		this.cameraY = cameraY;
		update();
	}

	public float getCameraZ() {
		return cameraZ;
	}

	public void setCameraZ(float cameraZ) {
		this.cameraZ = cameraZ;
		update();
	}

	public float getCameraRX() {
		return cameraRX;
	}

	public void setCameraRX(float cameraRX) {
		this.cameraRX = cameraRX;
		update();
	}

	public float getCameraRY() {
		return cameraRY;
	}

	public void setCameraRY(float cameraRY) {
		this.cameraRY = cameraRY;
		update();
	}

	public float getCameraRZ() {
		return cameraRZ;
	}

	public void setCameraRZ(float cameraRZ) {
		this.cameraRZ = cameraRZ;
		update();
	}

	public MouseHandler getMouseHandler() {
		return mouseHandler;
	}

	public void setMouseHandler(MouseHandler mouseHandler) {
		this.mouseHandler = mouseHandler;
	}

	public KeyHandler getKeyHandler() {
		return keyHandler;
	}

	public void setKeyHandler(KeyHandler keyHandler) {
		this.keyHandler = keyHandler;
	}
}
