package fr.xgouchet.musichelper.common;

public class Settings {

	private static boolean sShouldSimplify = true;

	/**
	 * @return if notes (double flats, E#, etc... ) should be simplified
	 */
	public static boolean shouldSimplify() {
		return sShouldSimplify;
	}

}
