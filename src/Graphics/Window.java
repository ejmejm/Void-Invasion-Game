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
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;
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
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import Entities.Bullet;
import Entities.Camera;
import Entities.Enemy;
import Entities.EnemyFlyer;
import Entities.Entity;
import Entities.EntityController;
import Entities.Light;
import GUI.GuiRenderer;
import GUI.GuiTexture;
import Input.CursorPos;
import Input.KeyInput;
import Input.MouseButtonInput;
import Levels.LevelController;
import Materials.Metal;
import Math.Vector2f;
import Math.Vector3f;
import Player.Player;
import TerrainGen.VoxelTerrain;
import Util.Delta;
import Util.DeltaMouse;
import Util.EnemyBullet;
import Util.EnemyList;
import Util.EntityUtil;
import Util.OBJLoader;

public class Window {
	
	public static int WIDTH, HEIGHT;
    public static boolean winOn = true;
	
	// We need to strongly reference callback instances.
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback keyCallback;
    private GLFWMouseButtonCallback mouseButtonCallback;
    private GLFWCursorPosCallback cursorPosCallback;
 
    // The window handle
    public long window;

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
 
        glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
        
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
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        
        Loader loader = new Loader();
        
        MasterRenderer renderer = new MasterRenderer();
        
        Camera camera = new Camera();

        RawModel weaponModel = OBJLoader.loadObjModel("playerGun", loader, new Vector3f(1.0f, 0.8f, 0.6f));
        RawModel model = OBJLoader.loadObjModel("player", loader, new Vector3f(0.9f, 0.9f, 0.0f));
        //RawModel flyerModel = OBJLoader.loadObjModel("enemy", loader, new Vector3f(1.0f, 0.6f, 0.0f));

        Metal metal = new Metal();
        model.setMaterial(metal);
        weaponModel.setMaterial(metal);
        
        //EnemyFlyer flyer = new EnemyFlyer(flyerModel, new Vector3f(0 , 5, -5), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
        
        Player player = new Player(model, new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1), camera, weaponModel);
        Light light = new Light(new Vector3f(0, 10000, 0), new Vector3f(1, 1, 1));
        
        player.getModel().setMaterial(metal);
        
        player.setFirstPerson();
        player.setWeaponOffset(new Vector3f(0.5f, 0, -1));
        
        player.spawnWeapon();
        //flyer.spawnEnemy();
        
        VoxelTerrain terrain = new VoxelTerrain(new Vector3f(0, -2.4f, 0));
        terrain.render();
        
        LevelController levelController = new LevelController(1, player);
        levelController.begin();
        
        List<GuiTexture> guis = new ArrayList<GuiTexture>();
        GuiTexture gui = new GuiTexture(new Vector3f (1.0f, 0.1f, 0.1f), new Vector2f(-0.65f, 0.9f), new Vector2f(0.3f, 0.05f));
        GuiTexture guiCover = new GuiTexture(new Vector3f (0.0f, 0.0f, 0.0f), new Vector2f(0f, 0f), new Vector2f(1f, 1f));
        GuiTexture crossAim = new GuiTexture(new Vector3f (0.0f, 0.0f, 0.0f), new Vector2f(0.0f, 0.0f), new Vector2f(0.01f, 0.01f));
        guis.add(gui);
        guis.add(crossAim);
        
        GuiRenderer guiRenderer = new GuiRenderer(loader);
        
        boolean cover = false;
        
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        Delta.lastFrame = Delta.getTime();
        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (glfwWindowShouldClose(window) == GL_FALSE && winOn) {
        	
        	Delta.calcDelta();
        	
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            
            
            /////////////////////////Render stuff here///////////////////////////////////////////
            

            DeltaMouse.updateDeltaMouse(CursorPos.x, CursorPos.y);
            
			player.registerInput();
			
			if(MouseButtonInput.mouseButtons[0]){
				player.getWeapon().genBullet();
				MouseButtonInput.mouseButtons[0] = false;
			}
			
            for(Entity e : EntityController.entityList){
            	renderer.processEntity(e);
            }
            
            for(Enemy e : EnemyList.enemies){
    			((EnemyFlyer) e).update(player.getWeapon().getPosition());
    		}
            
            ArrayList<Bullet> tempBulletList = new ArrayList(player.getWeapon().bulletList);
            ArrayList<Enemy> tempEnemyList = new ArrayList(EnemyList.enemies);
            
            for(Bullet b : tempBulletList){
            	if(b.getTimeCount() > 2500){
            		player.getWeapon().bulletList.remove(b);
            	}else{
            		boolean exists = true;
	        		for(Enemy e : tempEnemyList){
	        			if(EntityUtil.distance(b.getPosition(), e.getPosition()) <= 3){
	        				player.getWeapon().bulletList.remove(b);
	        				e.setHealth(e.getHealth() - ((Player) b.getWeapon().getHolder()).getDamage());
	        				if(e.getHealth() <= 0){
	        					e.despawnEnemy(levelController);
	        				}
	        				exists = false;
	        			}
	        		}
	        		if(exists){
	        			b.update();
	        			renderer.processEntity(b);	        			
	        		}
            	}
            }

            ArrayList<Bullet> tempEnemyBulletList = new ArrayList(EnemyBullet.enemyBullets);
            tempBulletList = new ArrayList(player.getWeapon().bulletList);
            
            for(Bullet b : tempEnemyBulletList){
            	if(b.getTimeCount() > 4000){
            		EnemyBullet.enemyBullets.remove(b);
            	}else{
            		boolean exists = true;
            		if(EntityUtil.distance(b.getPosition(), player.getWeapon().getPosition()) <= 1.9){
            			player.setHealth(player.getHealth() - ((Enemy) b.getWeapon().getHolder()).getDamage());
                		EnemyBullet.enemyBullets.remove(b);
                		exists = false;
            		}else{
            			for(Bullet pb : tempBulletList){
            				if(EntityUtil.distance(b.getPosition(), pb.getPosition()) <= 0.8){
                        		EnemyBullet.enemyBullets.remove(b);
                        		player.getWeapon().bulletList.remove(pb);
            					exists = false;
            				}
            			}
            		}
            		if(exists){
            			b.update();
            			renderer.processEntity(b);            			
            		}
            		
            	}
            }
            
            if(!(player.getHealth() <= 0)){
            	gui.setScale(new Vector2f(0.003f * player.getHealth(), gui.getScale().y));
            }else if(cover == false){
            	gui.setScale(new Vector2f(0, 0));
            	guis.add(guiCover);
            	cover = true;
            }
            
            levelController.update();
            
            renderer.render(light, camera);
            
			guiRenderer.render(guis);
            
			////////////////////////////////////////////////////////////////////////////////////
			
            glfwSwapBuffers(window); // swap the color buffers
            
            glfwPollEvents();
            
        }
        
        guiRenderer.cleanUp();
        renderer.cleanUp();
        loader.cleanUp();
        glfwDestroyWindow(window);
        glfwTerminate();
    }
}