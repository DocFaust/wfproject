package de.mp3synchronizer.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestConfigurationModel.class, TestCopyHelper.class, TestExtensionsModel.class, TestMessages.class, TestMP3Synchronizer.class,
	TestMusicFileFilter.class })
public class AllTests {

}
