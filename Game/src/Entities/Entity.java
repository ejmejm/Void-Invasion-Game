package Entities;

import Graphics.RawModel;
import Math.Vector3f;

public class Entity {

	private RawModel model;
	private Vector3f position, rotation, scale;
	
	public Entity(RawModel model, Vector3f position, Vector3f rotation,
			Vector3f scale) {
		super();
		this.model = model;
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}

	public void translate(Vector3f translation){
		this.position.x += translation.x;
		this.position.y += translation.y;
		this.position.z += translation.z;
	}

	public void rotate(Vector3f rotation){
		this.rotation.x += rotation.x;
		this.rotation.y += rotation.y;
		this.rotation.z += rotation.z;
	}

	public RawModel getModel() {
		return model;
	}

	public void setModel(RawModel model) {
		this.model = model;
	}
	public Vector3f getPosition() {
		return position;
	}
	public Vector3f getRotation(){
		return rotation;
	}
	public Vector3f getScale() {
		return scale;
	}
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}
	public void setScale(Vector3f scale) {
		this.scale = scale;
	}
}
