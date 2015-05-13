package Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Graphics.Loader;
import Graphics.RawModel;
import Math.Vector3f;

public class OBJLoader {

	public static RawModel loadObjModel(String fileName, Loader loader){
		
		FileReader fr = null; 
		try{
			fr = new FileReader(new File("res/" + fileName + ".obj"));			
		}catch(IOException e){
			System.err.println("Could not load OBJ file!");
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(fr);
		String line;
		List<Vector3f> vertices = new ArrayList<Vector3f>();
		List<Vector3f> normals = new ArrayList<Vector3f>();
		List<Integer> indices = new ArrayList<Integer>();
		float[] verticesArray = null;
		float[] normalsArray = null;
		int[] indicesArray = null;
		
		try{
			while(true){
				line = reader.readLine();
				String[] currentLine = line.split(" ");
				if(line.startsWith("v ")){
					vertices.add(new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3])));
				}
				else if(line.startsWith("vn ")){
					normals.add(new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3])));
				}
				else if(line.startsWith("f ")){
					normalsArray = new float[vertices.size() * 3];
					break;
				}
			}
			
			while(line != null){
				if(!line.startsWith("f ")){
					line = reader.readLine();
					continue;
				}
				String[] currentLine = line.split(" ");
				String[] vertex1 = currentLine[1].split("//");
				String[] vertex2 = currentLine[2].split("//");
				String[] vertex3 = currentLine[3].split("//");
				
				processVertex(vertex1, indices, normals, normalsArray);
				processVertex(vertex2, indices, normals, normalsArray);
				processVertex(vertex3, indices, normals, normalsArray);
				line = reader.readLine();
			}
			reader.close();
			
		}catch(IOException e){
			e.printStackTrace();
		}
		verticesArray = new float[vertices.size() * 3];
		indicesArray = new int[indices.size() * 3];
		
		int vertexPointer = 0;
		for(Vector3f vertex : vertices){
			verticesArray[vertexPointer++] = vertex.x;
			verticesArray[vertexPointer++] = vertex.y;
			verticesArray[vertexPointer++] = vertex.z;
		}
		
		for(int i = 0; i < indices.size(); i++){
			indicesArray[i] = indices.get(i);
		}
		
		return loader.loadToVAO(verticesArray, indicesArray, normalsArray);
	}
	
	private static void processVertex(String[] vertexData, List<Integer> indices, List<Vector3f> normals, float[] normalsArray){
		int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
		indices.add(currentVertexPointer);
		Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[1]) - 1);
		normalsArray[currentVertexPointer * 3] = currentNorm.x;
		normalsArray[currentVertexPointer * 3 + 1] = currentNorm.y;
		normalsArray[currentVertexPointer * 3 + 2] = currentNorm.z;
	}
	
}
