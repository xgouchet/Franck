package fr.xgouchet.musichelper.test;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.xgouchet.musichelper.test.model.ChordTest;
import fr.xgouchet.musichelper.test.model.KeyTest;
import fr.xgouchet.musichelper.test.model.NoteTest;
import fr.xgouchet.musichelper.test.model.TuningTest;

public class ModelSuiteTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(ModelSuiteTests.class.getName());

		// $JUnit-BEGIN$
		suite.addTestSuite(ChordTest.class);
		suite.addTestSuite(NoteTest.class);
		suite.addTestSuite(TuningTest.class);
		suite.addTestSuite(KeyTest.class);
		// $JUnit-END$

		return suite;
	}

}
