package Util;

public class DeltaMouse {

	private static double mousePrevX = 0;
	private static double mousePrevY = 0;
	private static double mouseDX, mouseDY;
	
	public static void updateDeltaMouse(double mouseX, double mouseY){
		mouseDX = mouseX - mousePrevX;
		mouseDY = mouseY - mousePrevY;
		mousePrevX = mouseX;
		mousePrevY = mouseY;
	}

	public static double getPrevX() {
		return mousePrevX;
	}

	public static double getPrevY() {
		return mousePrevY;
	}

	public static double getDX() {
		return mouseDX;
	}

	public static double getDY() {
		return mouseDY;
	}
}