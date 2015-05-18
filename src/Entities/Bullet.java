package Entities;

import Math.Vector3f;
import Player.Player;
import Player.Weapon;
import Util.Delta;
import Util.EnemyBullet;
import Util.OBJLoader;

public class Bullet extends Entity{
	
	Weapon weapon;
	private Vector3f velocity;
	private int timeCount;
	
	public Bullet(Weapon weapon){
		super(OBJLoader.loadObjModel("cube", weapon.getLoader(), new Vector3f(0, 0, 1)), new Vector3f(weapon.getPosition()).add(new Vector3f(0, 0, 0)), 
				weapon.getHolder().getRotation(),
				new Vector3f(0.15f, 0.15f, 0.15f));
		timeCount = 0;
		velocity = new Vector3f(0, 0, 0);
		this.weapon = weapon;
		genBullet();
	}
	
	public Bullet(Weapon weapon, Vector3f location){
		super(OBJLoader.loadObjModel("cube", weapon.getLoader(), new Vector3f(1, 0, 0)), new Vector3f(weapon.getPosition()).add(new Vector3f(0, 0, 0)), 
				weapon.getHolder().getRotation(),
				new Vector3f(0.3f, 0.3f, 0.3f));
		timeCount = 0;
		this.weapon = weapon;
		this.velocity = location.subtractNoSet(weapon.getPlayer().getPosition()).normalize().multiplyNoSet(0.5f);
		EnemyBullet.enemyBullets.add(this);
	}
	
	public void update(){
		getPosition().add(velocity.multiplyNoSet(Delta.delta).multiplyNoSet(0.05f));
		timeCount += Delta.delta;
    	if(timeCount > 2000){
    		weapon.bulletList.remove(this);
    	}
	}
	
	public void genBullet(){
		
		//Thank you for the help Devesh
		float theta = (float) Math.toRadians(((Player) weapon.getHolder()).getCamera().getYaw()%360);
		float phi = (float) Math.toRadians((((Player) weapon.getHolder()).getCamera().getPitch()+90)%360);
		
		Vector3f direction = new Vector3f((float) (Math.sin(theta) * Math.sin(phi)),
				(float) Math.cos(phi),
				(float) -(Math.cos(theta) * Math.sin(phi))).normalize();
		
		velocity.set(direction);
		weapon.bulletList.add(this);
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

	public int getTimeCount() {
		return timeCount;
	}

	public void setTimeCount(int timeCount) {
		this.timeCount = timeCount;
	}
}
