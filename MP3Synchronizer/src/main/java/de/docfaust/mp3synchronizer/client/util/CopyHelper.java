package de.docfaust.mp3synchronizer.client.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utillityklasse für das Kopieren zweier Dateien.
 * 
 * @author XHU1011
 * 
 */
public class CopyHelper {

    /** Größe des Puffers. */
    private static final int BUFFERSIZE = 0xFFFF;

    /**
     * Quelldatei.
     */
    private File sourceFile = null;

    /**
     * Zieldatei.
     */
    private File targetFile = null;

    /**
     * Loggerinstanz.
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Konstuktor. Setzt Quelle und Ziel.
     * 
     * @param sourceFile
     *            Quelldatei.
     * @param targetFile
     *            Zieldatei.
     */
    public CopyHelper(final File sourceFile, final File targetFile) {
	this.sourceFile = sourceFile;
	this.targetFile = targetFile;
    }

    /**
     * Kopiert die Quell datei auf die Zieldatei.
     * 
     * @return true wenn das Kopieren erfolgreich war.
     */
    public final boolean copy() {

	if (sourceFile == null || targetFile == null) {
	    return false;
	}

	try {
	    targetFile.getParentFile().mkdirs();
	    InputStream in = new FileInputStream(sourceFile);
	    OutputStream out = new FileOutputStream(targetFile);

	    byte[] c = new byte[BUFFERSIZE];
	    int len = 0;
	    while ((len = in.read(c)) != -1) {
		out.write(c, 0, len);
	    }
	    in.close();
	    out.close();
	    logger.debug(sourceFile.getAbsolutePath() + " nach " + targetFile.getAbsolutePath() + " kopiert");
	} catch (IOException e) {
	    logger.debug("Fehler beim Kopieren: " + e.getMessage());
	    return false;
	}
	return true;
    }

    /**
     * Gibt die Quelldatei zurück.
     * 
     * @return Quelldatei
     */
    public final File getSourceFile() {
	return sourceFile;
    }

    /**
     * Gibt die Zieldatei zurück.
     * 
     * @return Zieldatei
     */
    public final File getTargetFile() {
	return targetFile;
    }
}
