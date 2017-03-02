package de.docfaust.mp3synchronizer.client.control;

/**
 * Interface für alle Listener, die auf Statistikdaten hören wollen.
 * 
 * @author XHU1011
 *
 */
public interface StatisticListener {
	/**
	 * Callbackmethode für das Empfangen eines Statistikevents.
	 */
	void statisticUpdated();
}
