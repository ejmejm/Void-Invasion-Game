package TerrainGen;

import Entities.Entity;
import Entities.EntityController;
import Graphics.Loader;
import Graphics.RawModel;
import Math.Vector3f;
import Util.OBJLoader;

public class VoxelTerrain {

	Entity base;
	
	public VoxelTerrain(Vector3f position){
		float[] vertecies = {	
        		-0.5f,0.5f,-0.5f,	
				-0.5f,-0.5f,-0.5f,	
				0.5f,-0.5f,-0.5f,	
				0.5f,0.5f,-0.5f,		
				
				-0.5f,0.5f,0.5f,	
				-0.5f,-0.5f,0.5f,	
				0.5f,-0.5f,0.5f,	
				0.5f,0.5f,0.5f,
				
				0.5f,0.5f,-0.5f,	
				0.5f,-0.5f,-0.5f,	
				0.5f,-0.5f,0.5f,	
				0.5f,0.5f,0.5f,
				
				-0.5f,0.5f,-0.5f,	
				-0.5f,-0.5f,-0.5f,	
				-0.5f,-0.5f,0.5f,	
				-0.5f,0.5f,0.5f,
				
				-0.5f,0.5f,0.5f,
				-0.5f,0.5f,-0.5f,
				0.5f,0.5f,-0.5f,
				0.5f,0.5f,0.5f,
				
				-0.5f,-0.5f,0.5f,
				-0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,0.5f
        };
        
        int[] indices = {	
        		0,1,3,	
				3,1,2,	
				4,5,7,
				7,5,6,
				8,9,11,
				11,9,10,
				12,13,15,
				15,13,14,	
				16,17,19,
				19,17,18,
				20,21,23,
				23,21,22
        };
        
        RawModel baseModel = OBJLoader.loadObjModel("base", new Loader());
        base = new Entity(baseModel, position, new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
	}
	
	public void render(){
        EntityController.entityList.add(base);
	}

	public void remove(){
        EntityController.entityList.remove(base);
	}
}
