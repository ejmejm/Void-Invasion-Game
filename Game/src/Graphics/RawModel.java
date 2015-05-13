package Graphics;

import Materials.Metal;
import Materials.ModelMaterial;
import Math.Vector3f;

public class RawModel {

	private int vaoID;
	private int vertexCount;
	private ModelMaterial material;
	private Vector3f color;
	
	public RawModel(int vaoID, int vertexCount){
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
		this.material = new Metal();
		this.color = new Vector3f(1.0f, 1.0f, 1.0f);
	}

	public int getVaoID() {
		return vaoID;
	}

	public void setVaoID(int vaoID) {
		this.vaoID = vaoID;
	}

	public int getVertexCount() {
		return vertexCount;
	}

	public void setVertexCount(int vertexCount) {
		this.vertexCount = vertexCount;
	}

	public ModelMaterial getMaterial() {
		return material;
	}

	public void setMaterial(ModelMaterial material) {
		this.material = material;
	}

	public Vector3f getColor() {
		return color;
	}
	
	public void setColor(Vector3f color) {
		this.color = color;
	}
}
