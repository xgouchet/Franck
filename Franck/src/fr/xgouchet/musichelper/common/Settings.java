package fr.xgouchet.musichelper.common;

import fr.xgouchet.musicgeneration.model.Notation;

/**
 * @author Xavier Gouchet
 */
public class Settings {

	private static Notation sNotation = Notation.english;
	private static boolean sShouldSimplify = true;

	/**
	 * @return if notes (double flats, E#, etc... ) should be simplified
	 */
	public static boolean shouldSimplify() {
		return sShouldSimplify;
	}

	public static Notation getNotation() {
		return sNotation;
	}
}
