package GUI;

import Math.Vector2f;
import Math.Vector3f;

public class GuiTexture {
	private Vector3f color;
	private Vector2f position;
	private Vector2f scale;
	
	public GuiTexture(Vector3f color, Vector2f position, Vector2f scale) {
		super();
		this.color = color;
		this.position = position;
		this.scale = scale;
	}
	
	public Vector3f getColor() {
		return color;
	}
	public void setColor(Vector3f color) {
		this.color = color;
	}
	public Vector2f getPosition() {
		return position;
	}
	public void setPosition(Vector2f position) {
		this.position = position;
	}
	public Vector2f getScale() {
		return scale;
	}
	public void setScale(Vector2f scale) {
		this.scale = scale;
	}
}
