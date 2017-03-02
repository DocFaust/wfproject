package de.docfaust.mp3synchronizer.client.control;

/**
 * Interface f�r alle Listener, die auf Statistikdaten h�ren wollen.
 * 
 * @author XHU1011
 *
 */
public interface StatisticListener {
	/**
	 * Callbackmethode f�r das Empfangen eines Statistikevents.
	 */
	void statisticUpdated();
}
