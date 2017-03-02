package de.docfaust.mp3synchronizer.client.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Generierte Klasse zum Holen der Nachrichten in JOptionPanes.
 * 
 * @author XHU1011
 * 
 */
public final class Messages {
    /**
     * Name des Ressourcebundles aus dem die Nachrichten geholt werden.
     */
    private static final String BUNDLE_NAME = "messages";

    /**
     * Gibt das ResourceBundle zurück.
     * 
     * @return Instanz des RessourceBundles.
     */
    private static ResourceBundle getBundle() {
	ResourceBundle bundle = null;
	try {
	    bundle = ResourceBundle.getBundle(BUNDLE_NAME);
	} catch (MissingResourceException e) {
	    System.err.println("Sprache wurde nicht gefunden: " + Locale.getDefault().toString());
	    bundle = ResourceBundle.getBundle(BUNDLE_NAME, Locale.US);
	}
	return bundle;
    }

    /**
     * Konstruktor verstecken.
     */
    private Messages() {
    }

    /**
     * Gibt die Nachricht mit dem gewünschten Schlüssel zurück.
     * 
     * @param key
     *            Schlüssel der Nachricht
     * @return Nachricht
     */
    public static String getString(final String key) {
	try {
	    return getBundle().getString(key);
	} catch (MissingResourceException e) {
	    return '!' + key + '!';
	}
    }

    /**
     * Gibt die Nachricht mit dem gewünschten Schlüssel zurück.
     * 
     * @param key
     *            Schlüssel der Nachricht
     * @param arguments
     *            Einzufügende Argumente
     * @return Nachricht
     */
    public static String getString(final String key, final Object... arguments) {
	try {
	    return MessageFormat.format(getBundle().getString(key), arguments);
	} catch (MissingResourceException e) {
	    return '!' + key + '!';
	}
    }
}
