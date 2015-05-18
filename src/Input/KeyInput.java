package Input;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

import Graphics.Window;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class KeyInput extends GLFWKeyCallback{

	public static boolean[] keys = new boolean[65535];
	public static int[] wasd = {0, 0, 0, 0, 0, 0};
	
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		keys[key] = action != GLFW_RELEASE;
		if(key == GLFW_KEY_ESCAPE){
			Window.winOn = false;
		}
		
		if(action == GLFW_PRESS){
			if(key == GLFW_KEY_W){
				wasd[0] = -1;
			}
			
			if(key == GLFW_KEY_A){
				wasd[1] = -1;
			}
			
			if(key == GLFW_KEY_S){
				wasd[2] = 1;
			}
			
			if(key == GLFW_KEY_D){
				wasd[3] = 1;
			}
			
			if(key == GLFW_KEY_SPACE){
				wasd[4] = 1;
			}
			
			if(key == GLFW_KEY_LEFT_SHIFT){
				wasd[5] = -1;
			}
		}
		else if(action == GLFW_RELEASE){
			if(key == GLFW_KEY_W){
				wasd[0] = 0;
			}
			
			if(key == GLFW_KEY_A){
				wasd[1] = 0;
			}
			
			if(key == GLFW_KEY_S){
				wasd[2] = 0;
			}
			
			if(key == GLFW_KEY_D){
				wasd[3] = 0;
			}
			
			if(key == GLFW_KEY_SPACE){
				wasd[4] = 0;
			}
			
			if(key == GLFW_KEY_LEFT_SHIFT){
				wasd[5] = 0;
			}
		}
	}

}