package de.mp3synchronizer.junit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.docfaust.mp3synchronizer.client.util.CopyHelper;

public class TestCopyHelper {
	File testFile = new File("test/source.txt");

	@Before
	public void setUp() {
		testFile.getParentFile().mkdirs();
		try {
			FileOutputStream out = new FileOutputStream(testFile);
			out.write("test".getBytes());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@After
	public void tearDown() {
		testFile.delete();
		testFile.getParentFile().delete();
	}

	@Test
	public void testCopyWithExistingDir() {
		File target = new File(testFile.getParent(), "tartget/target.txt");
		target.getParentFile().mkdirs();
		CopyHelper ch = new CopyHelper(testFile, target);
		assertTrue("Copy nicht erfolgreich", ch.copy());
		assertTrue("Target nicht vorhanden", target.exists());
		try {
			FileInputStream in = new FileInputStream(ch.getTargetFile());
			byte[] b = new byte[4];
			in.read(b);
			in.close();

			assertTrue(Arrays.equals(b, "test".getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		target.delete();
		target.getParentFile().delete();
	}

	@Test
	public void testCopyWithNewDir() {
		File target = new File(testFile.getParent(), "tartget/target.txt");
		CopyHelper ch = new CopyHelper(testFile, target);
		assertTrue("Copy nicht erfolgreich", ch.copy());
		assertTrue("Target nicht vorhanden", target.exists());
		try {
			FileInputStream in = new FileInputStream(ch.getTargetFile());
			byte[] b = new byte[4];
			in.read(b);
			in.close();

			assertTrue(Arrays.equals(b, "test".getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		target.delete();
		target.getParentFile().delete();
	}

	@Test
	public void testGetSet() {
		File target = new File(testFile.getParent(), "tartget/target.txt");
		CopyHelper ch = new CopyHelper(testFile, target);
		assertTrue(testFile == ch.getSourceFile());
		assertTrue(target == ch.getTargetFile());
	}

	@Test
	public void testNoFiles() {
		CopyHelper ch = new CopyHelper(null, null);
		assertFalse(ch.copy());
		ch = new CopyHelper(testFile, null);
		assertFalse(ch.copy());
		ch = new CopyHelper(null, testFile);
		assertFalse(ch.copy());
	}

	@Test
	public void testNoSource() {
		File target = new File(testFile.getParent(), "tartget/target.txt");
		CopyHelper ch = new CopyHelper(new File("binnichtda"), target);
		assertFalse(ch.copy());
	}
}
