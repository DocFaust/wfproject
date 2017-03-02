package de.mp3synchronizer.junit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.docfaust.mp3synchronizer.client.model.ConfigurationModel;

public class TestConfigurationModel {

    @Test
    public void testOk() {
	ConfigurationModel.getInstance().setLastSourceDir("C:\\test");
	assertEquals("C:\\test", ConfigurationModel.getInstance().getLastSourceDir());
	ConfigurationModel.getInstance().setLastTargetDir("C:\\target");
	assertEquals("C:\\target", ConfigurationModel.getInstance().getLastTargetDir());
    }

}
