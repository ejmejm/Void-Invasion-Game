package Input;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import Util.DeltaMouse;
import static org.lwjgl.glfw.GLFW.*;

public class CursorPos extends GLFWCursorPosCallback{

	public static double x, y;

	@Override
	public void invoke(long window, double xpos, double ypos) {
		x = xpos;
		y = ypos;
	}

}

