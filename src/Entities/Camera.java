package Entities;

import Math.Vector3f;

public class Camera {

	private Vector3f position = new Vector3f(0, 0, 0);
	private float pitch;
	private float yaw;
	private float roll;
	
	public void translate(Vector3f translation){
		position.add(translation);
	}
	
	public void rotate(Vector3f rotation){
		pitch += rotation.y;
		yaw += rotation.x;
		roll += rotation.z;
	}
	
	public void rotate2D(float rotX, float rotY){
		pitch += rotY;
		yaw += rotX;
	}
	
	public Vector3f getPosition() {
		return position;
	}
	public float getPitch() {
		return pitch;
	}
	public float getYaw() {
		return yaw;
	}
	public float getRoll() {
		return roll;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public void setRoll(float roll) {
		this.roll = roll;
	}
	
	
}
