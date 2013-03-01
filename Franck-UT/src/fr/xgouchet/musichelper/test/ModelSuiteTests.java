package fr.xgouchet.musichelper.test;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.xgouchet.musichelper.test.model.ChordTest;
import fr.xgouchet.musichelper.test.model.NoteTest;

public class ModelSuiteTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(ModelSuiteTests.class.getName());

		// $JUnit-BEGIN$
		suite.addTestSuite(ChordTest.class);
		suite.addTestSuite(NoteTest.class);
		// $JUnit-END$

		return suite;
	}

}
