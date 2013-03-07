package fr.xgouchet.musichelper.common;

/**
 * @author Xavier Gouchet
 */
public class Settings {

	private static boolean sUseFrenchNotation = false;
	private static boolean sShouldSimplify = true;

	/**
	 * @return if notes (double flats, E#, etc... ) should be simplified
	 */
	public static boolean shouldSimplify() {
		return sShouldSimplify;
	}

	/**
	 * @return if notes should be displayed using the French notation (Do, Re,
	 *         Mi instead of C, D, E)
	 */
	public static boolean useFrenchNotation() {
		return sUseFrenchNotation;
	}
}
