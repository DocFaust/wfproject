package de.docfaust.mp3synchronizer.client.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * Zeigt die Statistiken an.
 * 
 * @author XHU1011
 * 
 */
public class ProgressPanel extends JPanel {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 8072127484374973676L;

	private static final String WARTE = "Warte ...";

	private static final String ZERO = "0";

	private static final String FORTSCHRITT = "Fortschritt:";

	private static final String DURCHSCHNITTSGESCHWINDIGKEIT = "Durchschnittsgeschwindigkeit: ";

	private static final String AKTUELL_ZEIT = "Aktuell Zeit:";

	private static final String AKTUELL_LAENGE = "Aktuell Länge:";

	private static final String AKTUELL_DATEIEN = "Aktuell Dateien:";

	private static final String TOTAL_ZEIT = "Total Zeit:";

	private static final String TOTAL_LAENGE = "Total Länge:";

	private static final String TOTAL_DATEIEN = "Total Dateien:";

	private static final String DURCHSCHNITTSZEIT = "Durchschnittszeit: ";

	private JPanel progressLabelPanel = new JPanel(new GridLayout(10, 1));

	private JPanel progressFieldPanel = new JPanel(new GridLayout(10, 1));

	private JLabel totalprogressLabel = new JLabel(FORTSCHRITT);

	private JProgressBar totalProgress = new JProgressBar();

	private JLabel currentFilesLabel = new JLabel(AKTUELL_DATEIEN);

	private JLabel currentFiles = new JLabel(ZERO);

	private JLabel currentTimeLabel = new JLabel(AKTUELL_ZEIT);

	private JLabel currentTime = new JLabel(ZERO);

	private JLabel currentLengthLabel = new JLabel(AKTUELL_LAENGE);

	private JLabel currentLength = new JLabel(ZERO);

	private JLabel totalTimeLabel = new JLabel(TOTAL_ZEIT);

	private JLabel totalTime = new JLabel(ZERO);

	private JLabel totalFilesLabel = new JLabel(TOTAL_DATEIEN);

	private JLabel totalFiles = new JLabel(ZERO);

	private JLabel totalLengthLabel = new JLabel(TOTAL_LAENGE);

	private JLabel totalLength = new JLabel(ZERO);

	private JLabel averageTimeLabel = new JLabel(DURCHSCHNITTSZEIT);

	private JLabel averageTime = new JLabel(ZERO);

	private JLabel averageSpeedLabel = new JLabel(DURCHSCHNITTSGESCHWINDIGKEIT);

	private JLabel averageSpeed = new JLabel(ZERO);

	private JLabel statusLabel = new JLabel("Status:");

	private JLabel status = new JLabel(WARTE);

	/**
	 * Baut das Panel auf.
	 */
	public ProgressPanel() {
		totalProgress.setStringPainted(true);

		progressLabelPanel.add(totalFilesLabel);
		progressLabelPanel.add(totalLengthLabel);
		progressLabelPanel.add(totalTimeLabel);
		progressLabelPanel.add(currentFilesLabel);
		progressLabelPanel.add(currentLengthLabel);
		progressLabelPanel.add(currentTimeLabel);
		progressLabelPanel.add(averageTimeLabel);
		progressLabelPanel.add(averageSpeedLabel);
		progressLabelPanel.add(totalprogressLabel);
		progressLabelPanel.add(statusLabel);

		progressFieldPanel.add(totalFiles);
		progressFieldPanel.add(totalLength);
		progressFieldPanel.add(totalTime);
		progressFieldPanel.add(currentFiles);
		progressFieldPanel.add(currentLength);
		progressFieldPanel.add(currentTime);
		progressFieldPanel.add(averageTime);
		progressFieldPanel.add(averageSpeed);
		progressFieldPanel.add(totalProgress);
		progressFieldPanel.add(status);

		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEtchedBorder());

		this.add(progressLabelPanel, BorderLayout.WEST);
		this.add(progressFieldPanel, BorderLayout.CENTER);
	}

	public final JProgressBar getTotalProgress() {
		return totalProgress;
	}

	public final JLabel getCurrentFiles() {
		return currentFiles;
	}

	public final JLabel getCurrentTime() {
		return currentTime;
	}

	public final JLabel getCurrentLength() {
		return currentLength;
	}

	public final JLabel getTotalTime() {
		return totalTime;
	}

	public final JLabel getTotalFiles() {
		return totalFiles;
	}

	public final JLabel getTotalLength() {
		return totalLength;
	}

	public final JLabel getAverageTime() {
		return averageTime;
	}

	public final JLabel getAverageSpeed() {
		return averageSpeed;
	}

	public final JLabel getStatus() {
		return status;
	}

}
