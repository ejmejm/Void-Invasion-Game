package Entities;

import java.util.Random;

import Graphics.RawModel;
import Levels.LevelController;
import Math.Vector2f;
import Math.Vector3f;
import Player.Player;
import Player.Weapon;
import Util.Delta;

public class EnemyFlyer extends Enemy{

	private float flightHeight;
	private float moveErrorBound;
	private Vector2f destination;
	private LevelController levelController;
	private float currentInterval;
	private float shootInterval;
	private Player target;
	
	public EnemyFlyer(RawModel model, Vector3f position, Vector3f rotation,
			Vector3f scale, LevelController levelController, Player target) {
		super(model, position, rotation, scale);
		this.target = target;
		this.levelController = levelController;
		shootInterval = 1000;
		moveErrorBound = 25;
		destination = new Vector2f(getPosition().x, getPosition().z);
		flightHeight = 30;
		position.y += flightHeight;
	}
	
	public EnemyFlyer(RawModel model, Vector3f position, Vector3f rotation,
			Vector3f scale, float health, float damage, float speed,
			Vector3f weaponOffset, Weapon weapon, Vector3f velocity, LevelController levelController, Player target) {
		super(model, position, rotation, scale, health, damage, speed, weaponOffset,
				weapon, velocity);
		this.target = target;
		this.levelController = levelController;
		shootInterval = 500;
		moveErrorBound = 25;
		destination = new Vector2f(getPosition().x, getPosition().z);
		flightHeight = 30;
		position.y += flightHeight;
	}

	public void moveCalc(Vector3f location){
		velocity = new Vector3f(location.x - getPosition().x, 0, location.z - getPosition().z);
	}

	public void moveCalc(Vector3f location, float errorBound){
		Random rand = new Random();
		destination.x = (float) ((rand.nextDouble() * 2 * errorBound) - errorBound) + location.x;
		destination.y = (float) ((rand.nextDouble() * 2 * errorBound) - errorBound) + location.z;
		velocity = new Vector3f(destination.x - getPosition().x, 0, destination.y - getPosition().z).normalize();
	}
	
	public void update(Vector3f location){
		if(Math.abs(getPosition().x - destination.x) <= 0.5 && Math.abs(getPosition().z - destination.y) <= 0.5){
			moveCalc(location, moveErrorBound);
		}
		if(getPosition().x >= 200 || getPosition().z >= 200 || getPosition().x <= -200 || getPosition().z <= -200){
			this.despawnEnemy(levelController);
		}
		
		if(currentInterval >= shootInterval){
			shoot(target.getPosition(), 5);
			currentInterval = 0;
		}
		
		getPosition().add(velocity.multiplyNoSet(getSpeed()).multiplyNoSet(Delta.delta).multiplyNoSet(0.05f));
		
		currentInterval += Delta.delta;
	}
	
	public float getFlightHeight() {
		return flightHeight;
	}

	public void setFlightHeight(float flightHeight) {
		this.flightHeight = flightHeight;
	}

	public float getMoveErrorBound() {
		return moveErrorBound;
	}

	public void setMoveErrorBound(float moveErrorBound) {
		this.moveErrorBound = moveErrorBound;
	}

	public Vector2f getDestination() {
		return destination;
	}

	public void setDestination(Vector2f destination) {
		this.destination = destination;
	}

	public LevelController getLevelController() {
		return levelController;
	}

	public void setLevelController(LevelController levelController) {
		this.levelController = levelController;
	}
}
