package Graphics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Entities.Camera;
import Entities.Entity;
import Entities.Light;
import Math.Vector3f;
import Shaders.StaticShader;

public class MasterRenderer {

	private StaticShader shader = new StaticShader();
	private Renderer renderer = new Renderer(shader);
	
	private Map<RawModel, List<Entity>> entities = new HashMap<RawModel, List<Entity>>();
	public void render(Light lightSource, Camera camera){
		shader.start();
		shader.loadLight(lightSource);
		shader.loadViewMatrix(camera);
		renderer.render(entities);
		shader.stop();
		entities.clear();
	}
	
	public void processEntity(Entity entity){
		RawModel entityModel = entity.getModel();
		List<Entity> batch = entities.get(entityModel);
		if(batch != null){
			batch.add(entity);
		}else{
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);
		}
	}
	
	public void cleanUp(){
		shader.cleanUp();
	}
	
}
