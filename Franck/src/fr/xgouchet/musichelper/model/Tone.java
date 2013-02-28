package fr.xgouchet.musichelper.model;

/**
 * This enumeration lists all available tone in classic notation, it does not
 * list all possible alteretad notes.
 * 
 * For instance, there's no DFlat (use {@link #CSharp} instead).
 */
public enum Tone {
	C, CSharp, D, EFlat, E, F, FSharp, G, GSharp, A, BFlat, B;

	/**
	 * TODO parse French names too (Sol, Fa, Mi, ...)
	 * 
	 * Try and parses a value
	 * 
	 * 
	 * @param value
	 *            any non null String value, like "A", "C#" or "Bb"
	 * @return the Tone value
	 */
	public static Tone parse(final String value) {
		Tone tone;
		try {
			tone = Tone.valueOf(value);
		} catch (IllegalArgumentException e) {
			tone = null;
		}

		if (tone == null) {
			String note = value.trim();
			tone = Tone.valueOf(note.substring(0, 1));
			for (int i = 1; i < note.length(); ++i) {
				char alter = note.charAt(i);
				switch (alter) {
				case '#':
					tone = tone.augmented();
					break;
				case 'b':
					tone = tone.diminished();
					break;
				default:
					throw new IllegalArgumentException(
							"Unrecognized alteration in note " + value);
				}
			}
		}

		return tone;
	}

	/**
	 * @return a user friendly string value for this tone
	 */
	public String toPrettyString() {
		switch (this) {
		case BFlat:
			return "Bb";
		case CSharp:
			return "C#";
		case EFlat:
			return "Eb";
		case FSharp:
			return "F#";
		case GSharp:
			return "G#";
		default:
			return toString();
		}
	}

	/**
	 * @return the augmented tone for this dominant
	 */
	public Tone augmented() {
		int tone = (ordinal() + 1) % values().length;

		return values()[tone];
	}

	/**
	 * @return the diminished tone for this dominant
	 */
	public Tone diminished() {
		int tone = ((ordinal() + values().length) - 1) % values().length;

		return values()[tone];
	}

	/**
	 * @return the second tone for this dominant
	 */
	public Tone second() {
		int tone = (ordinal() + 2) % values().length;

		return values()[tone];
	}

	/**
	 * @return the third tone for this dominant
	 */
	public Tone third() {
		int tone = (ordinal() + 4) % values().length;

		return values()[tone];
	}

	/**
	 * @return the fourth tone for this dominant
	 */
	public Tone fourth() {
		int tone = (ordinal() + 5) % values().length;

		return values()[tone];
	}

	/**
	 * @return the fifth tone for this dominant
	 */
	public Tone fifth() {
		int tone = (ordinal() + 7) % values().length;

		return values()[tone];
	}

	/**
	 * @return the sixth tone for this dominant
	 */
	public Tone sixth() {
		int tone = (ordinal() + 9) % values().length;

		return values()[tone];
	}

	/**
	 * @return the seventh tone for this dominant
	 */
	public Tone seventh() {
		int tone = (ordinal() + 11) % values().length;

		return values()[tone];
	}
}
