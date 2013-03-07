package fr.xgouchet.musichelper.test.model;

import fr.xgouchet.musichelper.model.Key;
import junit.framework.TestCase;

public class KeyTest extends TestCase {

	/**
	 * Simply the offset on a staff from the C4 to the lowest line
	 */
	public void testKeyOffset() {

		assertEquals(Key.treble.toString(), -2, Key.treble.c4Offset());
		assertEquals(Key.bass.toString(), 10, Key.bass.c4Offset());
		assertEquals(Key.alto.toString(), 4, Key.alto.c4Offset());

	}
}
