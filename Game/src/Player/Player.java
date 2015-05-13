package Player;

import Entities.Camera;
import Entities.Entity;
import Entities.EntityController;
import Graphics.RawModel;
import Input.KeyInput;
import Math.Vector3f;

public class Player extends Entity{
	
	private float health, damage, speed;
	private Vector3f cameraOffset, weaponOffset;
	private Camera camera;
	private Weapon weapon;

	public Player(RawModel model, Vector3f position, Vector3f rotation,
			Vector3f scale, float health, float damage, float speed, Camera camera, RawModel weaponModel) {
		super(model, position, rotation, scale);
		this.health = health;
		this.damage = damage;
		this.speed = speed;
		this.camera = camera;
		Vector3f weaponPos = new Vector3f(position.x, position.y, position.z);
		Vector3f weaponRot = new Vector3f(rotation.x, rotation.y, rotation.z);
		Vector3f weaponScale = new Vector3f(scale.x, scale.y, scale.z);
		weapon = new Weapon(weaponModel, weaponPos, weaponRot.multiply(new Vector3f(-90, 0, 0)), weaponScale.multiply(2));
	}
	
	public Player(RawModel model, Vector3f position, Vector3f rotation,
			Vector3f scale, Camera camera, RawModel weaponModel) {
		super(model, position, rotation, scale);
		this.health = 100;
		this.damage = 10;
		this.speed = 0.05f;
		this.camera = camera;
		Vector3f weaponPos = new Vector3f(position.x, position.y, position.z);
		Vector3f weaponRot = new Vector3f(rotation.x, rotation.y, rotation.z);
		Vector3f weaponScale = new Vector3f(scale.x, scale.y, scale.z);
		weapon = new Weapon(weaponModel, weaponPos, weaponRot.add(new Vector3f(90, 0, 0)), weaponScale.multiply(1.5f));
	}
	
	public void spawn(){
		EntityController.entityList.add(this);
		EntityController.entityList.add(weapon);
	}
	
	public void registerInput(){
		Vector3f translation = new Vector3f((KeyInput.wasd[1] + KeyInput.wasd[3]) * speed, (KeyInput.wasd[4] + KeyInput.wasd[5]) * speed, (KeyInput.wasd[0] + KeyInput.wasd[2]) * speed);
		translate(translation);
		weapon.translate(translation);
        camera.translate(translation);
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
