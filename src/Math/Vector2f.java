package Math;

public class Vector2f {
	
	public float x, y;
	
	public Vector2f(float x, float y){
		this.x = x;
		this.y = y;
	}
	public Vector2f(Vector2f other){
		this.x = other.x;
		this.y = other.y;
	}
	
	public Vector2f add(Vector2f other){
		this.x += other.x;
		this.y += other.y;
		return this;
	}
	
	public Vector2f addNoSet(Vector2f other){
		return new Vector2f(this.x + other.x, this.y + other.y);
	}
	
	public Vector2f multiplyNoSet(Vector2f other){
		return new Vector2f(this.x * other.x, this.y * other.y);
	}
	
	public Vector2f multiplyNoSet(float factor){
		return new Vector2f(this.x * factor, this.y * factor);
	}

	public Vector2f subtractNoSet(Vector2f other){
		return new Vector2f(this.x - other.x, this.y - other.y);
	}
	
	public Vector2f subtract(Vector2f other){
		this.x -= other.x;
		this.y -= other.y;
		return this;
	}
	
	public Vector2f multiply(Vector2f other){
		this.x *= other.x;
		this.y *= other.y;
		return this;
	}
	
	public Vector2f divide(Vector2f other){
		this.x /= other.x;
		this.y /= other.y;
		return this;
	}
	
	public Vector2f add(float scale){
		this.x += scale;
		this.y += scale;
		return this;
	}
	
	public Vector2f subtract(float scale){
		this.x -= scale;
		this.y -= scale;
		return this;
	}
	
	public Vector2f multiply(float scale){
		this.x *= scale;
		this.y *= scale;
		return this;
	}
	
	public Vector2f divide(float scale){
		this.x /= scale;
		this.y /= scale;
		return this;
	}
	
	public Vector2f normalize(){
		float length = (float) Math.abs(Math.sqrt(x*x + y*y));
		x /= length;
		y /= length;
		return this;
	}
	
	public void set(float x, float y, float z){
		this.x = x;
		this.y = y;
	}
	
	public void set(Vector2f other){
		this.x = other.x;
		this.y = other.y;
	}
	
	public boolean equals(Vector2f other){
		return (x == other.x && y == other.y);
	}
	
	public String toString(){
		return ("(" + x + ", " + y + ")");
	}
	
}
