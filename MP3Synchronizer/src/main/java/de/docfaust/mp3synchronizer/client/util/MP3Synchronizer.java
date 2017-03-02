package de.docfaust.mp3synchronizer.client.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.mp3synchronizer.client.control.StatisticListener;
import de.docfaust.mp3synchronizer.client.model.StatisticModel;

/**
 * MP3Synchronizer.
 * 
 * @author XHU1011
 * 
 */
public class MP3Synchronizer {
    /**
     * Quellverzeichnis.
     */
    private String sourceDirectory = null;

    /**
     * Zielverzeichnis.
     */
    private String targetDirectory = null;

    /**
     * Instanz des StatisticModels.
     */
    private StatisticModel statisticModel = null;

    /**
     * Vector der zusynchronisierenden Dateien.
     */
    private List<CopyHelper> filesToSynchronize = new ArrayList<>();

    /**
     * Vector der StatisticListener.
     */
    private List<StatisticListener> statisticListenerVector = null;

    /**
     * Zeigt an, on die Ausführung der Threads unterbrochen werden soll.
     */
    private boolean interrupted = false;

    /**
     * Logger.
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Gibt das Quellverzeichnis zurück.
     * 
     * @return Quellverzeichnis
     */
    public final String getSourceDirectory() {
	return sourceDirectory;
    }

    /**
     * Setzt das Quellverzeichnis .
     * 
     * @param sourceDirectory
     *            Quellverzeichnis
     */
    public final void setSourceDirectory(final String sourceDirectory) {
	this.sourceDirectory = sourceDirectory;
    }

    /**
     * Gibt das Zielverzeichnis zurück.
     * 
     * @return Zielverzeichnis
     */
    public final String getTargetDirectory() {
	return targetDirectory;
    }

    /**
     * Setzt das Zielverzeichnis.
     * 
     * @param targetDirectory
     *            Zielverzeichnis
     */
    public final void setTargetDirectory(final String targetDirectory) {
	this.targetDirectory = targetDirectory;
    }

    /**
     * Löscht das Zielverzeichnis rekursiv.
     * 
     * @param target
     *            Zielverzeichnis
     */
    private void delete(final File target) {
	logger.info("Lösche Pfad: " + target.getAbsolutePath());
	if (!target.exists()) {
	    return;
	}
	File[] list = target.listFiles();
	if (list != null) {
	    for (int i = 0; i < list.length; i++) {
		File f = list[i];
		if (f.isDirectory()) {
		    delete(f);
		}
		logger.debug("Lösche Datei: " + f.getAbsolutePath());

		f.delete();
	    }
	}
	target.delete();

    }

    /**
     * Löscht das Zielverzeichnis.
     */
    public final void deleteTarget() {
	logger.info("Lösche das Zielverzeichnis");
	File targetFile = new File(targetDirectory);
	delete(targetFile);
    }

    /**
     * Sammelt die Informationen über die Dateien, die synchronisiert werden
     * müssen.
     * 
     */
    public final void collectFiles() {
	logger.info("Sammle Dateiinfos");
	statisticModel = StatisticModel.getInstance();
	if (sourceDirectory == null) {
	    logger.warn("Keine Quelle angegeben");
	    statisticModel.setStatus("Fehler keine Quelle");
	    fireStatisticUpdated();
	    return;
	}
	if (targetDirectory == null) {
	    logger.warn("Kein Ziel angegeben");
	    statisticModel.setStatus("Fehler kein Ziel");
	    fireStatisticUpdated();
	    return;
	}
	statisticModel.setStatus("Sammle Dateien ...");
	fireStatisticUpdated();
	setFilesToSynchronize(new Vector<CopyHelper>());
	File sourceRootFile = new File(sourceDirectory);
	logger.debug("Durchsuche: " + sourceRootFile.getAbsolutePath());
	Stack<File> s = new Stack<File>();
	s.push(sourceRootFile);

	while (!s.isEmpty() && !interrupted) {
	    File actDir = (File) s.pop();
	    File[] list = actDir.listFiles(new MusicFileFilter());
	    for (int i = 0; list != null && i < list.length; i++) {
		File f = list[i];
		if (f.isDirectory()) {
		    s.push(f);
		} else {
		    String absoluteTargetPath = targetDirectory + f.getAbsolutePath().substring(sourceDirectory.length());
		    File targetFile = new File(absoluteTargetPath);
		    if (isSynchronizeNeccessary(f, targetFile)) {
			logger.debug("Datei gefunden " + f.getAbsolutePath());

			CopyHelper c = new CopyHelper(f, targetFile);
			getFilesToSynchronize().add(c);
			statisticModel.addTotalLength(f.length());
			statisticModel.incrementTotalFiles();
			fireStatisticUpdated();
		    }
		}
	    }
	}
	logger.info("Sammeln fertig");

    }

    /**
     * Synchronisiert alle Dateien, die im Vector filesToSynchronize gesammelt
     * wurden.
     * 
     * @see CopyHelper
     */
    public final void synchronize() {
	logger.info("Synchronisiere");
	if (getFilesToSynchronize() == null || getFilesToSynchronize().isEmpty()) {
	    statisticModel.setStatus("Fehler keine Files zum Synchronisieren!");
	    fireStatisticUpdated();
	    return;
	}
	Iterator<CopyHelper> iterator = getFilesToSynchronize().iterator();
	while (iterator.hasNext() && !interrupted) {
	    CopyHelper helper = (CopyHelper) iterator.next();

	    statisticModel.addCurrentLength(helper.getSourceFile().length());
	    statisticModel.incrementCurrentFiles();
	    statisticModel.startTimer();
	    helper.copy();
	    statisticModel.stopTimer();
	    statisticModel.setStatus("Synchronisiere: " + helper.getSourceFile().getName());
	    fireStatisticUpdated();
	}
	statisticModel.setStatus("Fertig ...");
	fireStatisticUpdated();
	logger.info("Synchronisieren fertig");

    }

    /**
     * Bereinigt das Ziel von Dateien, die im Quellverzeichnis nicht vorhanden
     * sind.
     */
    public final void cleanUp() {
	File targetRootFile = new File(targetDirectory);
	if (!targetRootFile.exists()) {
	    return;
	}
	Stack<File> s = new Stack<File>();
	s.push(targetRootFile);

	while (!s.isEmpty() && !interrupted) {
	    File actDir = (File) s.pop();
	    File[] list = actDir.listFiles(new MusicFileFilter());
	    if (list == null) {
		return;
	    }
	    for (int i = 0; i < list.length; i++) {
		File f = list[i];
		if (f.isDirectory()) {
		    s.push(f);
		} else {
		    String absoluteSourcePath = sourceDirectory + f.getAbsolutePath().substring(targetRootFile.getAbsolutePath().length());
		    logger.info("Prüfe: " + absoluteSourcePath);
		    File sourceFile = new File(absoluteSourcePath);
		    if (!sourceFile.exists()) {
			logger.info("Lösche: " + f.getAbsolutePath() + " weil " + sourceFile.getAbsolutePath() + " nicht da");
			f.delete();
		    }
		}
	    }
	}
    }

    /**
     * Gibt zurück, ob zwei Dateien synchronisiert werden müssen.
     * 
     * @param sourceFile
     *            Quelldatei
     * @param targetFile
     *            Zieldatei
     * @return true wenn die Dateien nicht gleich sind.
     */
    public final boolean isSynchronizeNeccessary(final File sourceFile, final File targetFile) {
	// Synchronisieren ist notwendig wenn ...
	// ... das File gar nicht existiert
	// oder
	// ... das Source-File neuer ist als das Target-File
	return !targetFile.exists() || (sourceFile.lastModified() > targetFile.lastModified());
    }

    /**
     * Fügt einen Listener für die Statistikdaten hinzu.
     * 
     * @param l
     *            Listener
     */
    public final void addStatisticListener(final StatisticListener l) {
	if (statisticListenerVector == null) {
	    statisticListenerVector = new Vector<StatisticListener>();
	}
	statisticListenerVector.add(l);
    }

    /**
     * Löscht einen StatisticListener aus der Liste.
     * 
     * @param l
     *            Listener, der gelöscht werden soll
     */
    public final void removeStatisticListener(final StatisticListener l) {
	statisticListenerVector.remove(l);
	if (statisticListenerVector.isEmpty()) {
	    statisticListenerVector = null;
	}
    }

    /**
     * Schickt das StatistikModel an alle registrierten Listener.
     */
    private void fireStatisticUpdated() {
	for (StatisticListener listener : statisticListenerVector) {
	    listener.statisticUpdated();
	}
    }

    /**
     * Zeigt an, ob Dateien im Vector sind.
     * 
     * @return liefert zurück, ob die Liste leer ist.
     */
    public final boolean isEmpty() {
	return getFilesToSynchronize().isEmpty();
    }

    // /**
    // * Prüft, ob Synch unterbrochen wurde.
    // *
    // * @return Status, ob unterbrochen.
    // */
    // public final boolean isInterrupted() {
    // return interrupted;
    // }
    //
    /**
     * Unterbricht Synch.
     */
    public final void interrupt() {
	setInterrupted(true);
    }

    /**
     * Setzt den Unterbrechungsstatus.
     * 
     * @param interrupted
     *            true, wenn unterbrochen wird.
     */
    public final void setInterrupted(final boolean interrupted) {
	this.interrupted = interrupted;
    }

    public List<CopyHelper> getFilesToSynchronize() {
	return filesToSynchronize;
    }

    public void setFilesToSynchronize(final List<CopyHelper> filesToSynchronize) {
	this.filesToSynchronize = filesToSynchronize;
    }
}
