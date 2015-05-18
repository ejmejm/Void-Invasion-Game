package Player;

import Entities.Camera;
import Entities.Entity;
import Entities.EntityController;
import Graphics.RawModel;
import Input.KeyInput;
import Math.Vector3f;
import Util.Delta;
import Util.DeltaMouse;

public class Player extends Entity{
	
	private float health, damage, speed;
	private Vector3f cameraOffset, weaponOffset;
	private Camera camera;
	private Weapon weapon;
	private float gravity;
	private Vector3f velocity;

	public Player(RawModel model, Vector3f position, Vector3f rotation,
			Vector3f scale, float health, float damage, float speed, Camera camera, RawModel weaponModel) {
		super(model, position, rotation, scale);
		this.health = health;
		this.damage = damage;
		this.speed = speed;
		this.camera = camera;
		this.gravity = 0.0003f;
		this.velocity = new Vector3f(0, 0, 0);
		Vector3f weaponPos = new Vector3f(position.x, position.y, position.z);
		Vector3f weaponRot = new Vector3f(rotation.x, rotation.y, rotation.z);
		Vector3f weaponScale = new Vector3f(scale.x, scale.y, scale.z);
		weapon = new Weapon(weaponModel, weaponPos, weaponRot.multiply(new Vector3f(-90, 0, 0)), weaponScale.multiply(2), this);
	}
	
	public Player(RawModel model, Vector3f position, Vector3f rotation,
			Vector3f scale, Camera camera, RawModel weaponModel) {
		super(model, position, rotation, scale);
		this.health = 100;
		this.damage = 10;
		this.speed = 0.005f;
		this.camera = camera;
		this.getCamera().setPitch(0);
		this.gravity = 0.0003f;
		this.velocity = new Vector3f(0, 0, 0);
		Vector3f weaponPos = new Vector3f(position.x, position.y, position.z);
		Vector3f weaponRot = new Vector3f(rotation.x, rotation.y, rotation.z);
		Vector3f weaponScale = new Vector3f(scale.x, scale.y, scale.z);
		weapon = new Weapon(weaponModel, weaponPos, weaponRot.add(new Vector3f(90, 0, 45)), weaponScale.multiply(1.5f), this);
	}
	
	public void spawn(){
		EntityController.entityList.add(this);
		EntityController.entityList.add(weapon);
	}
	
	public void spawnPlayer(){
		EntityController.entityList.add(this);
	}
	
	public void spawnWeapon(){
		EntityController.entityList.add(weapon);
	}
	
	public void registerInput(){
        if(DeltaMouse.getDX() != 0 || DeltaMouse.getDY() != 0){
        	if(camera.getPitch() <= 90 && camera.getPitch() >= -90){
	        	camera.setPitch(camera.getPitch() + (float) DeltaMouse.getDY());
        	}
        	else if(camera.getPitch() <= 90){
        		camera.setPitch(-90);
        	}else{
        		camera.setPitch(90);
        	}
        	camera.setYaw(camera.getYaw() + (float) DeltaMouse.getDX());
        	rotate(new Vector3f((float) -DeltaMouse.getDX(), 0, 0));
        	weapon.setRotation(new Vector3f(-camera.getYaw() + 90, -camera.getRoll(), -camera.getPitch()));
        }
        if(getPosition().y == 0.5){
        	if(KeyInput.wasd[4] == 1){
        		velocity.y = 0.2f;
        	}
        }
        else if(getPosition().y <= 0.5){
        	setPosition(new Vector3f(getPosition().x, 0.5f, getPosition().z));
        	weapon.getPosition().y = getPosition().y + weaponOffset.y;
        	camera.getPosition().y = getPosition().y + cameraOffset.y;
        	//camera.setPosition(new Vector3f(getPosition().x + cameraOffset.x, getPosition().y + cameraOffset.y, getPosition().z + cameraOffset.z));
        	velocity.y = 0;
        }else{
        	velocity.y -= gravity * Delta.delta;
        }
        Vector3f direction = new Vector3f((float) Math.cos(Math.toRadians((camera.getYaw() + 90)%360)), (float) Math.sin(Math.toRadians((camera.getYaw() + 90)%360)), 0).normalize();
        Vector3f directionSide = new Vector3f((float) Math.cos(Math.toRadians(camera.getYaw()%360)), (float) Math.sin(Math.toRadians(camera.getYaw()%360)), 0).normalize();
		Vector3f translation = new Vector3f( (direction.x * (KeyInput.wasd[0] + KeyInput.wasd[2]) * speed * Delta.delta) + (directionSide.x * (KeyInput.wasd[1] + KeyInput.wasd[3]) * speed * Delta.delta),
				 0,
				 direction.y * (KeyInput.wasd[0] + KeyInput.wasd[2]) * speed * Delta.delta + (directionSide.y * (KeyInput.wasd[1] + KeyInput.wasd[3]) * speed * Delta.delta)).add(velocity);
		translate(translation);
		weapon.translate(translation);
        camera.translate(translation);
	}
	
	
	
	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public float getGravity() {
		return gravity;
	}

	public void setGravity(float gravity) {
		this.gravity = gravity;
	}

	public Vector3f getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector3f velocity) {
		this.velocity = velocity;
	}

	public void setFirstPerson(){
		setCameraOffset(new Vector3f(0, 1, -1));
	}

	public void setThirdPerson(){
		setCameraOffset(new Vector3f(0, 2, 2));
	}
	
	public Vector3f getCameraOffset() {
		return cameraOffset;
	}

	public void setCameraOffset(Vector3f cameraOffset) {
		this.cameraOffset = cameraOffset;
		camera.translate(cameraOffset);
	}
	
	public Vector3f getWeaponOffset() {
		return weaponOffset;
	}

	public void setWeaponOffset(Vector3f weaponOffset) {
		this.weaponOffset = weaponOffset;
		weapon.translate(weaponOffset);
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
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
}
