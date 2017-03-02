package de.docfaust.mp3synchronizer;

import de.docfaust.mp3synchronizer.client.control.MP3SynchronizerControl;

/**
 * Mainklasse, startet die gesamte Anwendung.
 * 
 * @author XHU1011
 *
 */
public final class MP3SynchronizerMain {
	/**
	 * Konstruktor versteckt.
	 */
	private MP3SynchronizerMain() {
	}

	/**
	 * Startet die Anwendung.
	 * 
	 * @param args
	 *            Keine Argumente, da GUI
	 */
	public static void main(final String[] args) {
		MP3SynchronizerControl control = new MP3SynchronizerControl();
		control.execute();
	}
}
