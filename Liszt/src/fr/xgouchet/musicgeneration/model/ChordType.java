package fr.xgouchet.musicgeneration.model;

import android.text.TextUtils;

/**
*
*/
public enum ChordType {

	// triad chords
	major, minor, augmented, diminished,

	// common 7th chords
	dominant7, major7, minor7, augmented7, diminished7,

	// advanced 7th chords
	halfDiminishedSeventh, minorMajorSeventh, augmentedMajorSeventh;

	public String asSuffix() {
		switch (this) {
		case major:
			return "";
		case minor:
			return "m";
		case augmented:
			return "aug";
		case diminished:
			return "dim";
		case dominant7:
			return "7";
		case major7:
			return "M7";
		case minor7:
			return "m7";
		case diminished7:
			return "dim7";
		case augmented7:
			return "aug7";
		default:
			return toString();
		}
	}

	/**
	 * Converts a chord string representation into the corresponding enum value.
	 * Below are listed the possible values for each chord
	 * 
	 * <ul>
	 * <li>Basic chords :
	 * <dl>
	 * <dt>Major</dt>
	 * <dd>C, CM, Cma, Cmaj</dd>
	 * <dt>Minor</dt>
	 * <dd>Cm, Cmi, Cmin</dd>
	 * <dt>Augmented</dt>
	 * <dd>C+, Caug</dd>
	 * <dt>Diminished</dt>
	 * <dd>C°, Cdim</dd>
	 * </dl>
	 * </li>
	 * 
	 * <li>7th chords :
	 * <dl>
	 * <dt>Diminished 7th</dt>
	 * <dd>C°7, Cdim7</dd>
	 * <dt>Half Diminished 7th</dt>
	 * <dd>Cm7b5</dd>
	 * <dt>Minor 7th</dt>
	 * <dd>Cm7, Cmin7</dd>
	 * <dt>Minor Major 7th</dt>
	 * <dd>Cm(M7), Cm maj7</dd>
	 * <dt>Dominant 7th</dt>
	 * <dd>C7</dd>
	 * <dt>Major 7th</dt>
	 * <dd>CM7, Cmaj7</dd>
	 * <dt>Augmented 7th</dt>
	 * <dd>C+7, Caug7, C7+5</dd>
	 * <dt>Augmented Major 7th</dt>
	 * <dd>C+(M7), CM7+5</dd>
	 * </dl>
	 * </li>
	 * </ul>
	 * 
	 * @param value
	 * @return
	 */
	public static ChordType fromString(final String value) {
		if (TextUtils.isEmpty(value)) {
			return major;
		} else if ("M".equals(value)) {
			return major;
		} else if ("ma".equalsIgnoreCase(value)) {
			return major;
		} else if ("maj".equalsIgnoreCase(value)) {
			return major;
		} else if ("m".equals(value)) {
			return minor;
		} else if ("mi".equals(value)) {
			return minor;
		} else if ("min".equals(value)) {
			return minor;
		} else if ("aug".equals(value)) {
			return augmented;
		} else if ("+".equals(value)) {
			return augmented;
		} else if ("dim".equals(value)) {
			return diminished;
		} else if ("°".equals(value)) {
			return diminished;
		} else if ("7".equals(value)) {
			return dominant7;
		} else if ("M7".equals(value)) {
			return major7;
		} else if ("maj7".equalsIgnoreCase(value)) {
			return major7;
		} else if ("m7".equals(value)) {
			return minor7;
		} else if ("min7".equals(value)) {
			return minor7;
		} else if ("aug7".equals(value)) {
			return augmented7;
		} else if ("+7".equals(value)) {
			return augmented7;
		} else if ("dim7".equals(value)) {
			return diminished7;
		} else if ("°7".equals(value)) {
			return diminished7;
		}

		throw new IllegalArgumentException("Unknown chord type " + value);
	}
}
