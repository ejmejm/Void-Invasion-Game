package GUI;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import Graphics.Loader;
import Graphics.RawModel;
import Math.Matrix4f;
import Math.Vector3f;

public class GuiRenderer {

	private final RawModel quad;
	private GuiShader shader;
	
	public GuiRenderer(Loader loader){
		float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
		quad = loader.loadToVAO(positions);
		shader = new GuiShader();
	}
	
	public void render(List<GuiTexture> guis){
		shader.start();
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		for(GuiTexture gui : guis){
			Matrix4f matrix = Matrix4f.createTransformationMatrix(gui.getPosition(), gui.getScale());
			shader.loadTransformation(matrix);
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
		}
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}
	
	public void cleanUp(){
		shader.cleanUp();
	}
}