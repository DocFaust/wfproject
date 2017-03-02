package de.mp3synchronizer.junit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import de.docfaust.mp3synchronizer.client.model.ExtensionsModel;
import de.mp3synchronizer.util.TestDataHelper;

public class TestExtensionsModel {
    private static final String PROPERTIES_FILE = System.getProperty("user.home") + "/.mp3sync/conf/extensions.properties";
    private static TestDataHelper helper = new TestDataHelper();

    @BeforeClass
    public static void init() {
	new File(PROPERTIES_FILE).delete();
    }

    @Test
    public void testSave() {

	helper.initExtensionModel("WMA", "MP3");
	ExtensionsModel.getInstance().saveExtensions();

	try {
	    FileInputStream stream = new FileInputStream(PROPERTIES_FILE);
	    Properties props = new Properties();
	    props.load(stream);

	    Assert.assertEquals("WMA", props.getProperty("extension.0"));
	    Assert.assertEquals("MP3", props.getProperty("extension.1"));
	    stream.close();

	} catch (IOException e) {
	    e.printStackTrace();
	}

	List<String> ext = ExtensionsModel.getInstance().getExtensions();
	Assert.assertEquals("WMA", ext.get(0));
	Assert.assertEquals("MP3", ext.get(1));
    }

    @Test
    public void testLoad() throws FileNotFoundException, IOException {
	Properties props = new Properties();
	props.setProperty("extension.0", "mp3");
	props.setProperty("extension.1", "wma");
	props.store(new FileOutputStream(PROPERTIES_FILE), "");

	ExtensionsModel.getInstance().loadExtensions();

	List<String> ext = ExtensionsModel.getInstance().getExtensions();
	Assert.assertEquals("wma", ext.get(0));
	Assert.assertEquals("mp3", ext.get(1));
    }

    @Test
    public void testCannotSafe() throws FileNotFoundException, IOException {
	File p = new File(PROPERTIES_FILE);
	p.delete();
	p.mkdirs();
	ExtensionsModel.getInstance().getExtensions().add("ogg");
	ExtensionsModel.getInstance().saveExtensions();
	Assert.assertNotNull(ExtensionsModel.getInstance().getExtensions());
	ExtensionsModel.getInstance().getExtensions().clear();
	p.delete();
    }
}
