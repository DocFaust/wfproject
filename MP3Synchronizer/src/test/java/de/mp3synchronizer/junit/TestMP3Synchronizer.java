package de.mp3synchronizer.junit;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import de.docfaust.mp3synchronizer.client.control.StatisticListener;
import de.docfaust.mp3synchronizer.client.model.StatisticModel;
import de.docfaust.mp3synchronizer.client.util.CopyHelper;
import de.docfaust.mp3synchronizer.client.util.MP3Synchronizer;
import de.mp3synchronizer.util.TestDataHelper;

public class TestMP3Synchronizer {
    private static TestDataHelper helper = new TestDataHelper();

    @BeforeClass
    public static void init() {
	helper.initExtensionModel("WMA", "MP3");
    }

    @Before
    public void setUp() throws Exception {
	helper.createFiles();
    }

    @After
    public void tearDown() throws Exception {
	helper.removeFiles();
    }

    @Test
    public void testCollect() {
	LoggerFactory.getLogger(getClass()).info("testCollect");
	MP3Synchronizer synchronizer = new MP3Synchronizer();

	StatisticListener s = new StatisticListener() {

	    @Override
	    public void statisticUpdated() {
		StatisticModel model = StatisticModel.getInstance();
		model.getAverageSpeed();
		model.getAverageTime();
		model.getCurrentFiles();
		model.getCurrentLength();
		model.getStatus();
		model.getTotalFiles();
		model.getTotalTime();
		LoggerFactory.getLogger(getClass()).info(ToStringBuilder.reflectionToString(model));
	    }
	};

	synchronizer.addStatisticListener(s);
	synchronizer.removeStatisticListener(s);
	synchronizer.addStatisticListener(s);
	synchronizer.collectFiles();
	synchronizer.setSourceDirectory(helper.dir.getAbsolutePath());
	synchronizer.collectFiles();
	synchronizer.setTargetDirectory(helper.t.getAbsolutePath());
	synchronizer.collectFiles();
	Assert.assertFalse(synchronizer.isEmpty());
	List<CopyHelper> filesToSynchronize = synchronizer.getFilesToSynchronize();
	Assert.assertEquals(4, filesToSynchronize.size());
    }

    @Test
    public void testSynch() throws IOException {
	LoggerFactory.getLogger(getClass()).info("testSynch");
	MP3Synchronizer synchronizer = new MP3Synchronizer();
	synchronizer.addStatisticListener(new StatisticListener() {

	    @Override
	    public void statisticUpdated() {
		StatisticModel model = StatisticModel.getInstance();
		model.getAverageSpeed();
		model.getAverageTime();
		model.getCurrentFiles();
		model.getCurrentLength();
		model.getStatus();
		model.getTotalFiles();
		model.getTotalTime();
		LoggerFactory.getLogger(getClass()).info(ToStringBuilder.reflectionToString(model));
	    }
	});
	synchronizer.setSourceDirectory(helper.dir.getAbsolutePath());
	synchronizer.setTargetDirectory("testtarget");
	synchronizer.collectFiles();
	synchronizer.synchronize();
	synchronizer.interrupt();
	synchronizer.setInterrupted(false);
	synchronizer.synchronize();
	synchronizer.synchronize();
	helper.orphan.createNewFile();
	System.out.println(helper.orphan.getAbsolutePath());
	Assert.assertTrue(helper.orphan.exists());
	synchronizer.cleanUp();
	Assert.assertTrue(helper.t.exists());
	Assert.assertTrue(helper.ta.exists());
	Assert.assertTrue(helper.tb.exists());
	Assert.assertFalse(helper.tc.exists());
	Assert.assertTrue(helper.td.exists());
	Assert.assertTrue(helper.tda.exists());
	Assert.assertTrue(helper.tdb.exists());
	Assert.assertFalse(helper.tdc.exists());
	Assert.assertFalse(helper.orphan.exists());
	synchronizer.deleteTarget();
	Assert.assertFalse(helper.t.exists());
	Assert.assertFalse(helper.ta.exists());
	Assert.assertFalse(helper.tb.exists());
	Assert.assertFalse(helper.tc.exists());
	Assert.assertFalse(helper.td.exists());
	Assert.assertFalse(helper.tda.exists());
	Assert.assertFalse(helper.tdb.exists());
	Assert.assertFalse(helper.tdc.exists());
	Assert.assertFalse(helper.orphan.exists());
    }
}
