package gr.jmanji.tylr.util;

public abstract class Util {
	public static float clamp (float currentValue, float minValue, float maxValue) {
		return Math.min(maxValue, Math.max(currentValue, minValue));
	}
}
