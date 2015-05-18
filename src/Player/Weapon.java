package Player;

import java.util.ArrayList;

import Entities.Bullet;
import Entities.Entity;
import Graphics.Loader;
import Graphics.RawModel;
import Math.Vector3f;

public class Weapon extends Entity{
	
	private Entity player;
	
	public ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
	
	private Loader loader = new Loader();
	
	public Weapon(RawModel model, Vector3f position, Vector3f rotation, Vector3f scale, Entity player) {
		super(model, position, rotation, scale);
		this.player = player;
	}
	
	public Bullet genBullet(){
		return new Bullet(this);
	}
	
	public Bullet genBullet(Vector3f location){
		return new Bullet(this, location);
	}

	public Entity getHolder() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Loader getLoader() {
		return loader;
	}

	public void setLoader(Loader loader) {
		this.loader = loader;
	}

	public Entity getPlayer() {
		return player;
	}

	public void setPlayer(Entity player) {
		this.player = player;
	}
}
