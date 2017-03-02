package de.mp3synchronizer.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.docfaust.mp3synchronizer.client.model.ExtensionsModel;

public class TestDataHelper {
    public File t = new File("testtarget");
    public File dir = new File("test");
    public File a = new File(dir, "a.mp3");
    public File b = new File(dir, "b.wma");
    public File c = new File(dir, "c.txt");
    public File d = new File(dir, "d");
    public File da = new File(d, "da.mp3");
    public File db = new File(d, "db.wma");
    public File dc = new File(d, "dc.txt");
    public File ta = new File(t, "a.mp3");
    public File tb = new File(t, "b.wma");
    public File tc = new File(t, "c.txt");
    public File td = new File(t, "d");
    public File tda = new File(td, "da.mp3");
    public File tdb = new File(td, "db.wma");
    public File tdc = new File(td, "dc.wma");
    public File orphan = new File(t, "orphan.mp3");

    public void createFiles() throws IOException {
	dir.mkdirs();
	d.mkdirs();
	a.createNewFile();
	b.createNewFile();
	c.createNewFile();
	da.createNewFile();
	db.createNewFile();
	dc.createNewFile();
	t.mkdirs();
    }

    public void removeFiles() throws IOException {
	a.delete();
	b.delete();
	c.delete();
	da.delete();
	db.delete();
	dc.delete();
	dir.delete();
	d.delete();
	ta.delete();
	tb.delete();
	tda.delete();
	tdb.delete();
	orphan.delete();
	dir.delete();
	t.delete();
    }

    public void initExtensionModel(final String... extensions) {
	List<String> extensionList = new ArrayList<String>() {
	    private static final long serialVersionUID = 1L;

	    {
		for (String ext : extensions) {
		    add(ext);
		}
	    }
	};
	ExtensionsModel.getInstance().setExtensions(extensionList);
    }
}
