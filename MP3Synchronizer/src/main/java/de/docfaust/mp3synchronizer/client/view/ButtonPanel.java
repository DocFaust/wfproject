package de.docfaust.mp3synchronizer.client.view;

import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import de.docfaust.mp3synchronizer.client.model.constants.CommandConstants;

/**
 * Panel mit den Buttons.
 * 
 * @author XHU1011
 *
 */
public class ButtonPanel extends JPanel {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 9116639750879865714L;

    /**
     * OK-Button.
     */
    private JButton okButton = new JButton(CommandConstants.SYNCHRONIZE);

    /**
     * Abbrechen-Button.
     */
    private JButton abbrechenButton = new JButton(CommandConstants.ABBRECHEN);

    /**
     * Löschen-Button.
     */
    private JButton deleteButton = new JButton(CommandConstants.ZIEL_LOESCHEN);

    /**
     * Aufräumen Button.
     */
    private JButton cleanButton = new JButton(CommandConstants.CLEAN);

    /**
     * Erzeugt ein Buttonpanel inkl. Komponenten.
     */
    public ButtonPanel() {
	okButton.setActionCommand(CommandConstants.SYNCHRONIZE);
	okButton.setMnemonic('S');
	okButton.setToolTipText("Synchronisiert das Quellverzeichnis mit dem Zielverzeichnis.");
	okButton.setName("okButton");
	abbrechenButton.setActionCommand(CommandConstants.ABBRECHEN);
	abbrechenButton.setMnemonic('A');
	abbrechenButton.setToolTipText("Bricht die Synchronisierung ab.");
	deleteButton.setActionCommand(CommandConstants.ZIEL_LOESCHEN);
	deleteButton.setMnemonic('Z');
	deleteButton.setToolTipText("Löscht das Zielverzeichnis.");
	cleanButton.setActionCommand(CommandConstants.CLEAN);
	cleanButton.setMnemonic('B');
	cleanButton.setToolTipText("Bereinigt das Zielverzeichns von Dateien, die im Quellverzeichnis nicht vorhanden sind.");

	this.add(okButton);
	this.add(abbrechenButton);
	this.add(deleteButton);
	this.add(cleanButton);
	this.setBorder(BorderFactory.createEtchedBorder());
	this.setButtonsEnabled(true);
    }

    /**
     * Fügt einen ActionListener hinzu.
     * 
     * @param listener
     *            ActionListener
     */
    public final void addActionListener(final ActionListener listener) {
	okButton.addActionListener(listener);
	abbrechenButton.addActionListener(listener);
	deleteButton.addActionListener(listener);
	cleanButton.addActionListener(listener);
    }

    /**
     * Aktiviert und Deaktiviert die Buttons.
     * 
     * @param enabled
     *            Status der Buttons
     */
    public final void setButtonsEnabled(final boolean enabled) {
	okButton.setEnabled(enabled);
	deleteButton.setEnabled(enabled);

	// Immer genau umgekehrt, da dieser Button Ja enabled sein soll, wenn
	// sync läuft.
	abbrechenButton.setEnabled(!enabled);
	cleanButton.setEnabled(enabled);
    }
}
