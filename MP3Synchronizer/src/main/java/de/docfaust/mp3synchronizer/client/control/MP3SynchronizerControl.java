package de.docfaust.mp3synchronizer.client.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.mp3synchronizer.client.model.ConfigurationModel;
import de.docfaust.mp3synchronizer.client.model.StatisticModel;
import de.docfaust.mp3synchronizer.client.model.constants.CommandConstants;
import de.docfaust.mp3synchronizer.client.model.constants.MessageConstants;
import de.docfaust.mp3synchronizer.client.util.MP3Synchronizer;
import de.docfaust.mp3synchronizer.client.util.Messages;
import de.docfaust.mp3synchronizer.client.view.MP3SynchronizerGUI;
import de.docfaust.mp3synchronizer.client.view.SetFilterDialog;

/**
 * Controllerklasse. Hie laufen alle Fäden zusammen. Hier wird die GUI gestartet
 * und auf die Events reagiert.
 * 
 * @author XHU1011
 * 
 */
public class MP3SynchronizerControl implements ActionListener, StatisticListener {
	/** Megabyte. */
	private static final String MB = " MB";

	/**
	 * Konstante 1000.
	 */
	private static final int ONETHOUSAND = 1000;

	/**
	 * Konstante 100.
	 */
	private static final int ONEHUNDRED = 100;

	/**
	 * Faktor für Umrechnung in MB in B.
	 */
	private static final double FACTOR_MB = 0.00000095367431640625;

	/**
	 * Konstante für 1KB.
	 */
	private static final float ONE_KB_FLOAT = 1024F;

	/**
	 * Konstante für Zeitpattern.
	 */
	private static final String HH_MM_SS = "HH:mm:ss";

	/**
	 * Instanz der GUI.
	 */
	private MP3SynchronizerGUI gui = null;

	/**
	 * Instanz des Synchronizers.
	 */
	private MP3Synchronizer synchronizer = null;

	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Konstruktor des Controllers. Initialisiert die Instanzen.
	 * 
	 */
	public MP3SynchronizerControl() {
		synchronizer = new MP3Synchronizer();
		gui = new MP3SynchronizerGUI();
	}

	/**
	 * Startet den Controller. Hier wird die GUI gestartet und die Listener
	 * registriert.
	 * 
	 */
	public final void execute() {
		gui.addActionListener(this);
		gui.setVisible(true);
	}

	/**
	 * Startet das Synchronisieren.
	 */
	private void synchronize() {
		logger.debug("Beginne Synchronisierung");
		new Thread(new Runnable() {
			public void run() {
				gui.getButtonPanel().setButtonsEnabled(false);
				gui.getMenu().setEnabled(false);

				synchronizer.addStatisticListener(MP3SynchronizerControl.this);
				logger.info("Sammle Daten");
				synchronizer.collectFiles();
				if (synchronizer.isEmpty()) {
					JOptionPane.showMessageDialog(gui,
							Messages.getString(MessageConstants.MP3SYNCHRONIZER_CONTROL_NOFILES_MESSAGE),
							Messages.getString(MessageConstants.MP3SYNCHRONIZER_CONTROL_NOFILES_TITLE),
							JOptionPane.INFORMATION_MESSAGE);
				}

				logger.info("Synchronisiere");
				synchronizer.synchronize();
				synchronizer.removeStatisticListener(MP3SynchronizerControl.this);

				gui.getButtonPanel().setButtonsEnabled(true);
				gui.getMenu().setEnabled(true);
			}
		}, "SynchronizerThread").start();
	}

	/**
	 * Callback Methode für das Empfangen von ActionEvents. Wird immer
	 * aufgerufen, wenn von der GUI ein Event geschickt wird.
	 * 
	 * @param e
	 *            Eventobjekt
	 */
	public final void actionPerformed(final ActionEvent e) {
		String sourceDir = gui.getInputPanel().getSourceDirField().getText();
		String targetDir = gui.getInputPanel().getTargetDirField().getText();

		ConfigurationModel.getInstance().setLastSourceDir(sourceDir);
		ConfigurationModel.getInstance().setLastTargetDir(targetDir);

		synchronizer.setSourceDirectory(sourceDir);
		synchronizer.setTargetDirectory(targetDir);

		if (CommandConstants.QUELLVERZEICHNIS_DURCHSUCHEN.equals(e.getActionCommand())) {
			searchSource();
		} else if (CommandConstants.ZIELVERZEICHNIS_DURCHSUCHEN.equals(e.getActionCommand())) {
			searchTarget();
		} else if (CommandConstants.SYNCHRONIZE.equals(e.getActionCommand())) {
			startSynchronize();
		} else if (CommandConstants.ABBRECHEN.equals(e.getActionCommand())) {
			stopSynchronize();
		} else if (CommandConstants.ZIEL_LOESCHEN.equals(e.getActionCommand())) {
			deleteTarget();
		} else if (CommandConstants.FILTER_SETZEN.equals(e.getActionCommand())) {
			setFilter();
		} else if (CommandConstants.CLEAN.equals(e.getActionCommand())) {
			clean();
		}
	}

	/**
	 * Bereinigt das Ziel.
	 */
	private void clean() {
		synchronizer.cleanUp();
		JOptionPane.showMessageDialog(gui,
				Messages.getString(MessageConstants.MP3SYNCHRONIZER_CONTROL_CLEANREADY_MESSAGE),
				Messages.getString(MessageConstants.MP3SYNCHRONIZER_CONTROL_CLEANREADY_TITLE),
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Setzt den Filter.
	 */
	private void setFilter() {
		SetFilterDialog dialog = new SetFilterDialog(gui);
		dialog.setVisible(true);
	}

	/**
	 * Löscht das Ziel.
	 */
	private void deleteTarget() {
		int answer = JOptionPane.showConfirmDialog(gui,
				Messages.getString(MessageConstants.MP3SYNCHRONIZER_CONTROL_CONFIRMDELETE_MESSAGE),
				Messages.getString(MessageConstants.MP3SYNCHRONIZER_CONTROL_CONFIRMDELETE_TITLE),
				JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
		synchronizer.setSourceDirectory(gui.getInputPanel().getSourceDirField().getText());
		synchronizer.setTargetDirectory(gui.getInputPanel().getTargetDirField().getText());
		if (answer == JOptionPane.YES_OPTION) {
			try {
				synchronizer.deleteTarget();
				JOptionPane.showMessageDialog(gui,
						Messages.getString(MessageConstants.MP3SYNCHRONIZER_CONTROL_DELETEREADY_MESSAGE),
						Messages.getString(MessageConstants.MP3SYNCHRONIZER_CONTROL_DELETEREADY_TITLE),
						JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(gui,
						Messages.getString(MessageConstants.MP3SYNCHRONIZER_CONTROL_COULDNOTDELETE_MESSAGE),
						Messages.getString(MessageConstants.MP3SYNCHRONIZER_CONTROL_COULDNOTDELETE_TITLE),
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Stoppt die Synchronisierung.
	 */
	private void stopSynchronize() {
		synchronizer.interrupt();
	}

	/**
	 * Startet die Synchronisierung.
	 */
	private void startSynchronize() {
		synchronizer.setInterrupted(false);
		if ("".equals(gui.getInputPanel().getSourceDirField().getText())
				|| "".equals(gui.getInputPanel().getTargetDirField().getText())) {
			JOptionPane.showMessageDialog(gui,
					Messages.getString(MessageConstants.MP3SYNCHRONIZER_CONTROL_NOPATH_MESSAGE),
					Messages.getString(MessageConstants.MP3SYNCHRONIZER_CONTROL_NOPATH_TITLE),
					JOptionPane.ERROR_MESSAGE);
		} else {
			synchronize();
		}
	}

	/**
	 * Durchsucht das Ziel.
	 */
	private void searchTarget() {
		JFileChooser chooser = new JFileChooser(gui.getInputPanel().getTargetDirField().getText());
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int option = chooser.showOpenDialog(gui);
		if (option == JFileChooser.APPROVE_OPTION) {
			String dir = chooser.getSelectedFile().getAbsolutePath();
			ConfigurationModel.getInstance().setLastTargetDir(dir);
			gui.getInputPanel().getTargetDirField().setText(dir);
		}
	}

	/**
	 * Durchsucht die Quelle.
	 */
	private void searchSource() {
		JFileChooser chooser = new JFileChooser(gui.getInputPanel().getSourceDirField().getText());
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int option = chooser.showOpenDialog(gui);
		if (option == JFileChooser.APPROVE_OPTION) {
			String dir = chooser.getSelectedFile().getAbsolutePath();
			ConfigurationModel.getInstance().setLastSourceDir(dir);
			gui.getInputPanel().getSourceDirField().setText(dir);
		}
	}

	/**
	 * Wird aufgerufen, wenn es eine neue Statistik gibt.
	 * 
	 */
	@Override
	public final void statisticUpdated() {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				SimpleDateFormat format = new SimpleDateFormat(HH_MM_SS);

				StatisticModel statisticModel = StatisticModel.getInstance();

				gui.getProgressPanel().getTotalFiles().setText(String.valueOf(statisticModel.getTotalFiles()));
				gui.getProgressPanel().getTotalLength().setText(
						String.valueOf((float) statisticModel.getTotalLength() / (ONE_KB_FLOAT * ONE_KB_FLOAT)) + MB);

				Calendar cal = new GregorianCalendar(1, 0, 1, 0, 0, 0);
				cal.add(Calendar.MILLISECOND, (int) statisticModel.getTotalTime());
				gui.getProgressPanel().getTotalTime().setText(format.format(cal.getTime()));

				gui.getProgressPanel().getCurrentFiles().setText(String.valueOf(statisticModel.getCurrentFiles()));
				gui.getProgressPanel().getCurrentLength().setText(
						String.valueOf((float) statisticModel.getCurrentLength() / (ONE_KB_FLOAT * ONE_KB_FLOAT)) + MB);

				cal = new GregorianCalendar(1, 0, 1, 0, 0, 0);
				cal.add(Calendar.MILLISECOND, (int) statisticModel.getCurrentTime());

				gui.getProgressPanel().getCurrentTime().setText(format.format(cal.getTime()));

				gui.getProgressPanel().getAverageTime()
						.setText(String.valueOf((float) (statisticModel.getAverageTime() / FACTOR_MB)) + " ms/MB");

				gui.getProgressPanel().getAverageSpeed().setText(
						String.valueOf(((statisticModel.getAverageSpeed() / ONE_KB_FLOAT) * ONETHOUSAND)) + " KB/s");

				gui.getProgressPanel().getStatus().setText(statisticModel.getStatus());
				gui.getProgressPanel().getTotalProgress()
						.setValue((int) Math.round(
								((double) statisticModel.getCurrentLength() / (double) statisticModel.getTotalLength())
										* ONEHUNDRED));

			}
		});
	}

}
