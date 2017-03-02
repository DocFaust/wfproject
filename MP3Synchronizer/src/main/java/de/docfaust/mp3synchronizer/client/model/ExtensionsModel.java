package de.docfaust.mp3synchronizer.client.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Model der Dateierweiterungen, nach denen gefiltert wird.
 * 
 * @author XHU1011
 * 
 */
public final class ExtensionsModel {
    /**
     * Propertiesdatei, in der die Erweiterungen gespeichert werden.
     */
    private static final String PROPERTIES_FILE = System.getProperty("user.home") + "/.mp3sync/conf/extensions.properties";

    /**
     * Vector der Erweiterungen.
     */
    private List<String> extensions = null;

    /**
     * Konstante für die Property-Keys.
     */
    private static final String EXTENSION_PREFIX = "extension.";

    /**
     * Eigene Instanz.
     */
    private static ExtensionsModel instance = null;

    /**
     * Logger.
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Konstruktor. Ruft das Laden auf und initialisiert den Vector.
     * 
     */
    private ExtensionsModel() {
	extensions = new Vector<String>();
	loadExtensions();
    }

    /**
     * Gibt die eigene Instanz zurück.
     * 
     * @return Eigene Instanz
     */
    public static synchronized ExtensionsModel getInstance() {
	if (instance == null) {
	    instance = new ExtensionsModel();
	}
	return instance;
    }

    /**
     * Gibt den Vector der Erweiterungen zurück.
     * 
     * @return Vector, der die Erweiterungen enthält
     */
    public List<String> getExtensions() {
	return extensions;
    }

    /**
     * Setzt den Vector der Erweiterungen.
     * 
     * @param extensions
     *            Vector der die Erweiterungen enthält.
     */
    public void setExtensions(final List<String> extensions) {
	this.extensions = extensions;
    }

    /**
     * Lädt die Erweiterungen aus der Propertiesdatei.
     * 
     */
    public void loadExtensions() {
	Properties props = new Properties();
	try {
	    FileInputStream stream = new FileInputStream(PROPERTIES_FILE);
	    props.load(stream);
	    stream.close();
	    Enumeration<Object> e = props.elements();
	    while (e.hasMoreElements()) {
		String extension = (String) e.nextElement();
		extensions.add(extension);
	    }
	} catch (IOException e) {
	    logger.error("Extensions konnten nicht geladen werden: " + e.getMessage());
	}
    }

    /**
     * Speichert die Konfigurationsdaten in der Propertiesdatei.
     * 
     */
    public void saveExtensions() {
	Properties props = new Properties();
	for (int i = 0; i < extensions.size(); i++) {
	    props.setProperty(EXTENSION_PREFIX + i, extensions.get(i));
	}
	try {
	    new File(PROPERTIES_FILE).getParentFile().mkdirs();
	    FileOutputStream stream = new FileOutputStream(PROPERTIES_FILE);
	    logger.debug("Speichere " + props + " nach " + PROPERTIES_FILE);
	    props.store(stream, "Do not change!");

	    stream.close();
	} catch (IOException e) {
	    logger.error("Extensions konnten nicht gespeichert werden", e);
	}
    }
}
