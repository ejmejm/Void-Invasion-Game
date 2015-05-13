package Math;

public class Vector3f {
	
	public float x, y, z;
	
	public Vector3f(){
		x = 0;
		y = 0;
		z = 0;
	}
	
	public Vector3f(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3f add(Vector3f other){
		this.x += other.x;
		this.y += other.y;
		this.z += other.z;
		return this;
	}
	
	public Vector3f addNoSet(Vector3f other){
		return new Vector3f(this.x + other.x, this.y + other.y, this.z + other.z);
	}
	
	public Vector3f subtract(Vector3f other){
		this.x -= other.x;
		this.y -= other.y;
		this.z -= other.z;
		return this;
	}
	
	public Vector3f multiply(Vector3f other){
		this.x *= other.x;
		this.y *= other.y;
		this.z *= other.z;
		return this;
	}
	
	public Vector3f divide(Vector3f other){
		this.x /= other.x;
		this.y /= other.y;
		this.z /= other.z;
		return this;
	}
	
	public Vector3f add(float scale){
		this.x += scale;
		this.y += scale;
		this.z += scale;
		return this;
	}
	
	public Vector3f subtract(float scale){
		this.x -= scale;
		this.y -= scale;
		this.z -= scale;
		return this;
	}
	
	public Vector3f multiply(float scale){
		this.x *= scale;
		this.y *= scale;
		this.z *= scale;
		return this;
	}
	
	public Vector3f divide(float scale){
		this.x /= scale;
		this.y /= scale;
		this.z /= scale;
		return this;
	}
	
}
