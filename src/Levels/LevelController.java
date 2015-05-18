package Levels;

import java.util.Random;
import Entities.EnemyFlyer;
import Graphics.Loader;
import Graphics.RawModel;
import Materials.Metal;
import Math.Vector3f;
import Player.Player;
import Util.Delta;
import Util.OBJLoader;

public class LevelController {

	private int level;
	private float difficulty;
	private int totalLevelEnemies;
	private int defeatedLevelEnemies;
	private int spawnedEnemies;
	private float enemySpawnInterval;
	private float currentInterval;
	private Random rand;
	Player player;
    Loader loader;
    RawModel flyerModel;
    
	public LevelController(float difficulty, Player player){
		this.difficulty = difficulty;
		rand = new Random();
		this.player = player;
		loader = new Loader();
		flyerModel = OBJLoader.loadObjModel("enemy", loader, new Vector3f(1.0f, 0.6f, 0.0f));
		flyerModel.setMaterial(new Metal());
	}
	
	public void begin(){
		level = 1;
		calculateAmountEnemies();
		calculateEnemySpawnInterval();
	}
	
	private int calculateAmountEnemies(){
		totalLevelEnemies = (int) (level * difficulty * 5 * (rand.nextDouble() + 1));
		return totalLevelEnemies;
	}
	
	public void update(){
		if(spawnedEnemies == 0){
			for(int i = 0; i < totalLevelEnemies/4; i++){
				spawnEnemy();
			}
		}else if(defeatedLevelEnemies == totalLevelEnemies){
			advanceLevel();
		}else{
			if(spawnedEnemies < totalLevelEnemies && currentInterval >= enemySpawnInterval){
				int numEnemies = calculateEnemySimSpawn();
				for(int i = 0; i < numEnemies; i++){
					spawnEnemy();
				}
				currentInterval = 0;
			}
			currentInterval += 1 * Delta.delta;
		}
	}
	
	private void spawnEnemy(){
		EnemyFlyer flyer = new EnemyFlyer(flyerModel, calculateEnemyPosition(), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1), this, player);
		flyer.setHealth(calculateEnemyHealth());
		flyer.setDamage(calculateEnemyDamage());
		flyer.setSpeed(calculateEnemySpeed());
		calculateEnemySpawnInterval();
		flyer.spawnEnemy();
		spawnedEnemies++;
	}
	
	private void advanceLevel(){
		level++;
		defeatedLevelEnemies = 0;
		spawnedEnemies = 0;
		currentInterval = 0;
		calculateEnemySpawnInterval();
		calculateAmountEnemies();
	}

	public void defeatEnemy(){
		defeatedLevelEnemies++;
	}
	
	private Vector3f calculateEnemyPosition(){
		return new Vector3f((float) (player.getWeapon().getPosition().x +  ((rand.nextDouble() * 100) - 50)),
				(float) (30 + ((rand.nextDouble() * 10) - 5)), 
				(float) (player.getWeapon().getPosition().z + ((rand.nextDouble() * 100) - 50)));
	}
	
	private float calculateEnemyHealth(){
		return (30 * (((float) level) / 2) * difficulty * (float) (rand.nextDouble() + 1));
	}
	
	private float calculateEnemyDamage(){
		return (5 * (((float) level) / 2) * difficulty * (float) (rand.nextDouble() + 1));
	}
	
	private float calculateEnemySpeed(){
		return (0.025f * (((float) level) / 2) * difficulty * (float) (rand.nextDouble() + 1));
	}
	
	private float calculateEnemySpawnInterval(){
		enemySpawnInterval = (2000 * (1/(((float) level) / 2)) * (1/difficulty) * (float) (rand.nextDouble() + 1));
		return enemySpawnInterval;
	}
	
	private int calculateEnemySimSpawn(){
		return (int) ((float) level * difficulty * (float) (rand.nextDouble() + 1.2));
	}
}