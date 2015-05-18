package Entities;

import java.util.Random;

import Graphics.RawModel;
import Levels.LevelController;
import Math.Vector3f;
import Player.Weapon;
import Util.EnemyList;

public abstract class Enemy extends Entity{

	private float health, damage, speed;
	private Vector3f weaponOffset;
	private Weapon weapon;
	protected Vector3f velocity;
	
	public Enemy(RawModel model, Vector3f position, Vector3f rotation,
			Vector3f scale) {
		super(model, position, rotation, scale);
		this.health = 30;
		this.damage = 10;
		this.speed = 0.05f;
		this.weaponOffset = new Vector3f(0, 0, 0);
		this.weapon = new Weapon(model, position, scale, scale, this);
		this.velocity = new Vector3f(0, 0, 0);
	}

	public Enemy(RawModel model, Vector3f position, Vector3f rotation,
			Vector3f scale, float health, float damage, float speed,
			Vector3f weaponOffset, Weapon weapon,
			Vector3f velocity) {
		super(model, position, rotation, scale);
		this.health = health;
		this.damage = damage;
		this.speed = speed;
		this.weaponOffset = weaponOffset;
		this.weapon = weapon;
		this.velocity = velocity;
	}

	public void shoot(Vector3f location){
		weapon.genBullet(location);
	}

	public void shoot(Vector3f location, float errorBound){
		Random rand = new Random();
		weapon.genBullet(new Vector3f (location.x + (float) ((rand.nextDouble() * 2 * errorBound) - errorBound), location.y + (float) ((rand.nextDouble() * 2 * errorBound) - errorBound), location.z + (float) ((rand.nextDouble() * 2 * errorBound) - errorBound)));
	}
	
	public void despawn(){
		EntityController.entityList.remove(this);
		EntityController.entityList.remove(weapon);
		EnemyList.enemies.remove(this);
	}
	
	public void despawnEnemy(LevelController levelController){
		EntityController.entityList.remove(this);
		EnemyList.enemies.remove(this);
		levelController.defeatEnemy();
	}
	
	public void despawnWeapon(){
		EntityController.entityList.remove(weapon);
	}
	
	public void spawn(){
		EntityController.entityList.add(this);
		EntityController.entityList.add(weapon);
		EnemyList.enemies.add(this);
	}
	
	public void spawnEnemy(){
		EntityController.entityList.add(this);
		EnemyList.enemies.add(this);
	}
	
	public void spawnWeapon(){
		EntityController.entityList.add(weapon);
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public float getDamage() {
		return damage;
	}

	public void setDamage(float damage) {
		this.damage = damage;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public Vector3f getWeaponOffset() {
		return weaponOffset;
	}

	public void setWeaponOffset(Vector3f weaponOffset) {
		this.weaponOffset = weaponOffset;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public Vector3f getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector3f velocity) {
		this.velocity = velocity;
	}
	
	
}
