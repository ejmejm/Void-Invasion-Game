package Graphics;
import static org.lwjgl.glfw.Callbacks.errorCallbackPrint;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.ByteBuffer;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import Entities.Camera;
import Entities.Entity;
import Entities.EntityController;
import Entities.Light;
import Input.CursorPos;
import Input.KeyInput;
import Input.MouseButtonInput;
import Materials.Metal;
import Math.Vector3f;
import Player.Player;
import TerrainGen.VoxelTerrain;
import Util.Delta;
import Util.OBJLoader;

public class Window {
	
	public static int WIDTH, HEIGHT;
	
	// We need to strongly reference callback instances.
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback keyCallback;
    private GLFWMouseButtonCallback mouseButtonCallback;
    private GLFWCursorPosCallback cursorPosCallback;
 
    // The window handle
    private long window;

	public Window(int width, int height){
		init(width, height);
	}
	
	private void init(int WIDTH, int HEIGHT){
		
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));
 
        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( glfwInit() != GL11.GL_TRUE )
            throw new IllegalStateException("Unable to initialize GLFW");
 
        // Configure our window
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable
 
        // Create the window
        window = glfwCreateWindow(WIDTH, HEIGHT, "Final Project", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");
 
        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, keyCallback = new KeyInput());
        glfwSetMouseButtonCallback(window, mouseButtonCallback = new MouseButtonInput());
        glfwSetCursorPosCallback(window, cursorPosCallback = new CursorPos());
        //glfwSetMouseButtonCallback(window, mouseButtonCallback = input);
 
        // Get the resolution of the primary monitor
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window
        glfwSetWindowPos(
            window,
            (GLFWvidmode.width(vidmode) - WIDTH) / 2,
            (GLFWvidmode.height(vidmode) - HEIGHT) / 2
        );
 
        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);
 
        // Make the window visible
        glfwShowWindow(window);
    }
	
	public void render() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the ContextCapabilities instance and makes the OpenGL
        // bindings available for use.
        GLContext.createFromCurrent();
 
        // Set the clear color
        glClearColor(0.5f, 0.6f, 1.0f, 0.0f);
        
        Loader loader = new Loader();
        
        MasterRenderer renderer = new MasterRenderer();
        
        Camera camera = new Camera();

        RawModel weaponModel = OBJLoader.loadObjModel("playerGun", loader);
        RawModel model = OBJLoader.loadObjModel("player", loader);

        Metal metal = new Metal();
        model.setMaterial(metal);
        model.setColor(new Vector3f(0.9f, 0.9f, 0.0f));
        weaponModel.setMaterial(metal);
        weaponModel.setColor(new Vector3f(1.0f, 0.8f, 0.6f));
        
        Player player = new Player(model, new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1), camera, weaponModel);
        Light light = new Light(new Vector3f(0, 10000, 0), new Vector3f(1, 1, 1));
        
        player.getModel().setMaterial(metal);
        
        player.setThirdPerson();
        player.setWeaponOffset(new Vector3f(0.5f, 0, -1));
        
        player.spawn();
        
        VoxelTerrain terrain = new VoxelTerrain(new Vector3f(0, -1, 0));
        terrain.render();
        
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        Delta.lastFrame = Delta.getTime();
        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while ( glfwWindowShouldClose(window) == GL_FALSE ) {
        	
        	int delta = Delta.getDelta();
        	
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            
            
            /////////////////////////Render stuff here///////////////////////////////////////////
            
            
            
			player.registerInput();
            
            for(Entity e : EntityController.entityList){
            	renderer.processEntity(e);
            }
            
            renderer.render(light, camera);
			
			
			
			////////////////////////////////////////////////////////////////////////////////////
			
            glfwSwapBuffers(window); // swap the color buffers
			
            glfwPollEvents();
            
        }
        renderer.cleanUp();
        loader.cleanUp();
        glfwDestroyWindow(window);
        glfwTerminate();
    }
}