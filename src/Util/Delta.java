package Util;

public class Delta {
	public static long lastFrame;
	public static int delta;
	
	public static long getTime() {
	    return System.nanoTime() / 1000000;
	}
	
	public static int calcDelta() {
	    long time = getTime();
	    int delt = (int) (time - lastFrame);
	    lastFrame = time;
	    delta = delt;
	    
	    return delt;
	}
}
