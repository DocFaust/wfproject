package de.mp3synchronizer.junit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.docfaust.mp3synchronizer.client.model.ExtensionsModel;
import de.docfaust.mp3synchronizer.client.util.MusicFileFilter;

public class TestMusicFileFilter {
	File dir = new File("test");
	File a = new File(dir, "a.mp3");
	File b = new File(dir, "b.wma");
	File c = new File(dir, "c.txt");
	File d = new File(dir, "d");

	@Before
	public void before() throws IOException {
		List<String> extensions = new ArrayList<String>() {
			{
				add("MP3");
				add("WMA");
			}
		};
		ExtensionsModel.getInstance().setExtensions(extensions);
		dir.mkdirs();
		a.createNewFile();
		b.createNewFile();
		c.createNewFile();
		d.mkdirs();
	}

	@After
	public void after() {
		a.delete();
		b.delete();
		c.delete();
		d.delete();
		dir.delete();
	}

	@Test
	public void test() {
		File dir = new File("test");
		File[] files = dir.listFiles(new MusicFileFilter());
		Assert.assertEquals(3, files.length);
	}

}
