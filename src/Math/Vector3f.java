package Math;

public class Vector3f {
	
	public float x, y, z;
	
	public Vector3f(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public Vector3f(Vector3f other){
		this.x = other.x;
		this.y = other.y;
		this.z = other.z;
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
	
	public Vector3f multiplyNoSet(Vector3f other){
		return new Vector3f(this.x * other.x, this.y * other.y, this.z * other.z);
	}
	
	public Vector3f multiplyNoSet(float factor){
		return new Vector3f(this.x * factor, this.y * factor, this.z * factor);
	}

	public Vector3f subtractNoSet(Vector3f other){
		return new Vector3f(this.x - other.x, this.y - other.y, this.z - other.z);
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
	/*
	public Vector3f normalize(){
		Vector3f negativeMult = new Vector3f(x/Math.abs(x), y/Math.abs(y), 1);
		if(!(negativeMult.x  <= 1 && negativeMult.x >= -1)){
			negativeMult.x = 1;
		}
		if(!(negativeMult.y  <= 1 && negativeMult.y >= -1)){
			negativeMult.y = 1;
		}
		x = Math.abs(x);
		y = Math.abs(y);
		z = Math.abs(z);
		float scale = 1/(this.x + this.y + this.z);
		this.x = scale * this.x * negativeMult.x;
		this.y = scale * this.y * negativeMult.y;
		this.z = scale * this.z * negativeMult.z;
		return this;
	}
	*/
	
	public Vector3f normalize(){
		float length = (float) Math.abs(Math.sqrt(x*x + y*y + z*z));
		x /= length;
		y /= length;
		z /= length;
		return this;
	}
	
	public void set(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void set(Vector3f other){
		this.x = other.x;
		this.y = other.y;
		this.z = other.z;
	}
	
	public String toString(){
		return ("(" + x + ", " + y + ", " + z + ")");
	}
	
}
