package Util;

public class Delta {
	public static long lastFrame;
	
	public static long getTime() {
	    return System.nanoTime() / 1000000;
	}
	
	public static int getDelta() {
	    long time = getTime();
	    int delta = (int) (time - lastFrame);
	    lastFrame = time;
	         
	    return delta;
	}
}
