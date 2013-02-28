package fr.xgouchet.musichelper.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class UISuiteTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(UISuiteTests.class.getName());

		// $JUnit-BEGIN$
		// suite.addTestSuite(UITest.class);
		// $JUnit-END$
		return suite;

	}

}
