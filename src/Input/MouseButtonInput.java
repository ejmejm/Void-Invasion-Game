package Input;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import static org.lwjgl.glfw.GLFW.*;

public class MouseButtonInput extends GLFWMouseButtonCallback{

	public static boolean[] mouseButtons = new boolean[30];
	
	@Override
	public void invoke(long window, int button, int action, int mods) {
		if(action == GLFW_PRESS){
			mouseButtons[button] = true;		
		}
		else{
			mouseButtons[button] = false;
		}
	}
}

