package Util;

import Math.Vector3f;

public class EntityUtil {

	public static float distance(Vector3f pos, Vector3f posOther){
		return (float) Math.sqrt(Math.pow(pos.x - posOther.x, 2) + Math.pow(pos.y - posOther.y, 2) + Math.pow(pos.z - posOther.z, 2));
	}
}
 