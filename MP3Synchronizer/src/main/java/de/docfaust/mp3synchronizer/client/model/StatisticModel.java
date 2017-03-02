package de.docfaust.mp3synchronizer.client.model;

/**
 * Model, das die Statistikdaten hält. Wird den StatisticListenern bei
 * Änderungen übergeben.
 * 
 * @author XHU1011
 * 
 */
public final class StatisticModel {
	/**
	 * My Instace.
	 */
	private static StatisticModel instance = new StatisticModel();

	/**
	 * Anfangsdurchschnittszeit um DIV/0 zu vermeiden.
	 */
	private static final double INITIAL_AVG_TIME = 0.000133514404296875;

	/**
	 * Durchschnittszeit in ms/Byte.
	 */
	private double averageTime = 0;

	/**
	 * Durchschnittsgeschwindigkeit in Byte/ms.
	 */
	private double averageSpeed = 0;

	/**
	 * Gesamte Anzahl der Dateien, die zu synchronisieren sind.
	 */
	private long totalFiles = 0;

	/**
	 * Gesamte Größe der Dateien, die zu synchronisieren sind.
	 */
	private long totalLength = 0;

	/**
	 * Geschätzte Gesamtzeit, die das Synchronisieren dauern wird. Errechnist
	 * sich aus totalLength/averageTime.
	 */
	private long totalTime = 0;

	/**
	 * Aktuell verbrauchte Zeit.
	 */
	private long currentTime = 0;

	/**
	 * Aktuelle kumulierte Länge der synchronisierten Dateien.
	 */
	private long currentLength = 0;

	/**
	 * Aktuelle kumulierte Anzahl der synchronisierten Dateien.
	 */
	private long currentFiles = 0;

	/**
	 * Timervariable.
	 */
	private long timer = 0;

	/**
	 * Indiziert ob der Timer läuft, oder nicht.
	 */
	private boolean timerRunning = false;

	/**
	 * Aktueller Status. Name der Datei, die gerade kopiert wurde.
	 */
	private String status = null;

	/**
	 * Privater Konstruktor.
	 */
	private StatisticModel() {

	}

	/**
	 * Instanzgetter.
	 * 
	 * @return Instanz
	 */
	public static StatisticModel getInstance() {
		return instance;
	}

	/**
	 * Erhöht die Gesamtanzahl der Dateien um Eins.
	 * 
	 */
	public void incrementTotalFiles() {
		totalFiles++;
	}

	/**
	 * Kumuliert die Gesamtdateilänge.
	 * 
	 * @param length
	 *            Länge einer Datei, die zu synchronisieren ist.
	 */
	public void addTotalLength(final long length) {
		totalLength += length;
	}

	/**
	 * Erhöht die aktuelle anzahl der Dateien um Eins.
	 * 
	 */
	public void incrementCurrentFiles() {
		currentFiles++;
	}

	/**
	 * Kumuliert die aktuelle Dateilänge.
	 * 
	 * @param length
	 *            Länge einer Datei, die synchronisiert worden ist.
	 */
	public void addCurrentLength(final long length) {
		currentLength += length;
	}

	/**
	 * Kumuliert die aktuell verbrauchte Zeit.
	 * 
	 * @param time
	 *            Fügt Zeit in ms hinzu.
	 */
	public void addCurrentTime(final long time) {
		currentTime += time;
	}

	/**
	 * Startet den Timer für die Zeitmessung.
	 */
	public void startTimer() {
		timer = System.currentTimeMillis();
		timerRunning = true;
	}

	/**
	 * Stoppt den Timer für die Zeitmessung.
	 */
	public void stopTimer() {
		if (timerRunning) {
			addCurrentTime(System.currentTimeMillis() - timer);
			timerRunning = false;
		}
	}

	/**
	 * Berechnet die Durchschnittszeit und gibt sie zurück.
	 * 
	 * @return Durchschnittszeit
	 */
	public double getAverageTime() {
		if (currentTime == 0) {
			averageTime = INITIAL_AVG_TIME;
		} else {
			averageTime = (double) currentTime / (double) currentLength;
		}
		return averageTime;
	}

	/**
	 * Berechnet die Durchschnittsgeschwindigkeit und gibt sie zurück.
	 * 
	 * @return Durchschnittsgeschwindigkeit
	 */
	public double getAverageSpeed() {
		averageSpeed = (double) currentLength / (double) currentTime;
		return averageSpeed;
	}

	/**
	 * Gibt die kumulierte Anzahl der synchronisierten Dateien zurück.
	 * 
	 * @return Anzahl der synchronisierten Dateien
	 */
	public long getCurrentFiles() {
		return currentFiles;
	}

	/**
	 * Gibt die kumulierte Länge der synchronisierten Dateien zurück.
	 * 
	 * @return Länge der synchronisierten Dateien
	 */
	public long getCurrentLength() {
		return currentLength;
	}

	/**
	 * Gibt die kumulierte Zeit der synchronisierten Dateien zurück.
	 * 
	 * @return Zeit der synchronisierten Dateien
	 */
	public long getCurrentTime() {
		return currentTime;
	}

	/**
	 * Gibt die Gesamtanzahl der zu synchronisierenden Dateien zurück.
	 * 
	 * @return Anzahl der zu synchronisierenden Dateien
	 */
	public long getTotalFiles() {
		return totalFiles;
	}

	/**
	 * Gibt die GesamtLänge der zu synchronisierenden Dateien zurück.
	 * 
	 * @return GesamtLänge der zu synchronisierenden Dateien
	 */
	public long getTotalLength() {
		return totalLength;
	}

	/**
	 * Gibt die geschätzte Gesamtzeit für das Synchronisieren der Dateien
	 * zurück.
	 * 
	 * @return geschätzte Gesamtzeit für das Synchronisieren
	 */
	public long getTotalTime() {
		totalTime = Math.round(totalLength * averageTime);
		return totalTime;
	}

	/**
	 * Gibt den Namen der aktuell synchronisierten Datei zurück.
	 * 
	 * @return Name der aktuell synchronisierten Datei.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Setzt den Namen der aktuell synchronisierten Datei.
	 * 
	 * @param actualFile
	 *            Name der aktuell synchronisierten Datei.
	 */
	public void setStatus(final String actualFile) {
		this.status = actualFile;
	}

}
