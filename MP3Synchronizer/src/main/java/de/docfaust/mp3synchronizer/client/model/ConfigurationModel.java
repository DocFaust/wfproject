package de.docfaust.mp3synchronizer.client.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Singleton, der die Konfigurationsdaten aus einer Propertiesdatei liest, hält
 * und Änderungen wieder speichert.
 * 
 * @author XHU1011
 * 
 */
public final class ConfigurationModel {
    /** Key für lasttargetDir. */
    private static final String KEY_LAST_TARGET_DIR = "last.target.dir";

    /** Key für lastSourceDir. */
    private static final String KEY_LAST_SOURCE_DIR = "last.source.dir";

    /**
     * Dateiname der Propertiesdatei.
     */
    private static final String PROPERTIES_FILE = System.getProperty("user.home") + "/.mp3sync/conf/MP3Synchronizer.properties";

    /**
     * Quellverzeichnis, das beim letzten Mal ausgewählt war.
     */
    private String lastSourceDir = null;

    /**
     * Zielverzeichnis, das beim letzten Mal ausgewählt war.
     */
    private String lastTargetDir = null;

    /**
     * Eigene Instanz.
     */
    private static ConfigurationModel instance = null;

    /**
     * Logger.
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Konstruktor. Ruft das Laden der Konfiguration auf.
     * 
     */
    private ConfigurationModel() {
	loadConfiguration();
    }

    /**
     * Gibt die einzige Instanz zurück.
     * 
     * @return Eigene Instanz
     */
    public static synchronized ConfigurationModel getInstance() {
	if (instance == null) {
	    instance = new ConfigurationModel();
	}
	return instance;
    }

    /**
     * Gibt das Quellverzeichnis zurück.
     * 
     * @return Quellverzeichnis, das beim letzten Mal ausgewählt war
     */
    public String getLastSourceDir() {
	return lastSourceDir;
    }

    /**
     * Setzt das Quellverzeichnis und ruft das Speichern auf.
     * 
     * @param lastSourceDir
     *            Quellverzeichnis, das beim letzten Mal ausgewählt war
     */
    public void setLastSourceDir(final String lastSourceDir) {
	this.lastSourceDir = lastSourceDir;
	saveConfiguration();
    }

    /**
     * Gibt das Zielverzeichnis zurück.
     * 
     * @return Zielverzeichnis, das beim letzten Mal ausgewählt war
     */
    public String getLastTargetDir() {
	return lastTargetDir;
    }

    /**
     * Setzt das Zielverzeichnis und ruft das Speichern auf.
     * 
     * @param lastTargetDir
     *            Zielverzeichnis, das beim letzten Mal ausgewählt war
     */
    public void setLastTargetDir(final String lastTargetDir) {
	this.lastTargetDir = lastTargetDir;
	saveConfiguration();
    }

    /**
     * Lädt die Konfigurationsdaten aus der Propertiesdatei.
     * 
     */
    private void loadConfiguration() {
	Properties props = new Properties();
	try {
	    FileInputStream stream = new FileInputStream(PROPERTIES_FILE);
	    props.load(stream);
	    stream.close();
	    lastSourceDir = props.getProperty(KEY_LAST_SOURCE_DIR, "");
	    lastTargetDir = props.getProperty(KEY_LAST_TARGET_DIR, "");
	} catch (IOException e) {
	    logger.warn("Konfiguration konnte nicht geladen werden!", e);
	    lastSourceDir = "";
	    lastTargetDir = "";
	}
    }

    /**
     * Speichert die Konfigurationsdaten in der Propertiesdatei.
     * 
     */
    private void saveConfiguration() {
	new File(PROPERTIES_FILE).getParentFile().mkdirs();
	Properties props = new Properties();
	try {
	    props.setProperty(KEY_LAST_SOURCE_DIR, lastSourceDir);
	    props.setProperty(KEY_LAST_TARGET_DIR, lastTargetDir);
	    logger.debug("Speichere " + props + " nach " + PROPERTIES_FILE);
	    FileOutputStream stream = new FileOutputStream(PROPERTIES_FILE);

	    props.store(stream, "Do not change!");
	    stream.close();
	} catch (IOException e) {
	    logger.error("Konfiguration konnte nicht gespeichert werden!", e);
	}
    }
}
