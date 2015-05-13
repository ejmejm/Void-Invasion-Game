package Shaders;

import Entities.Camera;
import Entities.Light;
import Math.Matrix4f;
import Math.Vector3f;

public class StaticShader extends ShaderProgram{

	private static final String VERTEX_FILE = "src/Shaders/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src/Shaders/fragmentShader.txt";
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition;
	private int location_lightColor;
	private int location_shineDamper;
	private int location_reflectivity;
	private int location_objColor;
	
	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	@Override
	protected void bindAttributes(){
		bindAttribute(0, "position");
		bindAttribute(1, "normal");
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = getUniformLocation("transformationMatrix");
		location_projectionMatrix = getUniformLocation("projectionMatrix");
		location_viewMatrix = getUniformLocation("viewMatrix");
		location_lightPosition = getUniformLocation("lightPositon");
		location_lightColor = getUniformLocation("lightColor");
		location_shineDamper = getUniformLocation("shineDamper");
		location_reflectivity = getUniformLocation("reflectivity");
		location_objColor = getUniformLocation("objColor");
	}
	
	public void loadShineVariables(float damper, float reflectivity){
		loadFloat(location_reflectivity, reflectivity);
		loadFloat(location_shineDamper, damper);
	}
	
	public void loadLight(Light light){
		loadVector(location_lightPosition, light.getPosition());
		loadVector(location_lightColor, light.getColor());
	}
	
	public void loadObjColor(Vector3f color){
		loadVector(location_objColor, color);
	}
	
	public void loadTransformationMatrix(Matrix4f matrix){
		loadMatrix(location_transformationMatrix, matrix);
	}
	
	public void loadViewMatrix(Camera camera){
		Matrix4f viewMatrix = Matrix4f.view(camera);
		loadMatrix(location_viewMatrix, viewMatrix);
	}

	public void loadProjectionMatrix(Matrix4f matrix){
		loadMatrix(location_projectionMatrix, matrix);
	}
}
