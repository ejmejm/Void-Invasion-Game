package Graphics;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import Entities.Entity;
import Math.Matrix4f;
import Math.Vector3f;
import Shaders.StaticShader;

public class Renderer {

	public static float FOV = 70;
	public static float NEAR_PLANE = 0.1f;
	public static float FAR_PLANE = 1000;
	
	private StaticShader shader;
	
	public Renderer(StaticShader shader){
		this.shader = shader;
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		Matrix4f projectionMatrix = Matrix4f.projection(FOV, (float) Window.WIDTH / (float) Window.WIDTH , NEAR_PLANE, FAR_PLANE);
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	public void render(Map<RawModel, List<Entity>> entities){
		for(RawModel model : entities.keySet()){
			prepareModel(model);
			List<Entity> batch = entities.get(model);
			for(Entity entity : batch){
				prepareInstance(entity);
				shader.loadObjColor(entity.getModel().getColor());
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			unbindModel();
		}
	}
	
	private void prepareModel(RawModel model){
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		shader.loadShineVariables(model.getMaterial().getShineDamper(), model.getMaterial().getReflectivity());
	}
	
	private void unbindModel(){
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}
	
	private void prepareInstance(Entity entity){
		Matrix4f transformationMatrix = Matrix4f.genMatrix(entity.getPosition(), entity.getRotation(), entity.getScale());
		shader.loadTransformationMatrix(transformationMatrix);
	}
	
}