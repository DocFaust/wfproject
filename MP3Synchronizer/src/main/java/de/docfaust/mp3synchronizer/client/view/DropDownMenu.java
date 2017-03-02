package de.docfaust.mp3synchronizer.client.view;

import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import de.docfaust.mp3synchronizer.client.model.constants.CommandConstants;

/**
 * Dropdownmenu.
 * 
 * @author XHU1011
 * 
 */
public class DropDownMenu extends JMenuBar {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -5592458945553792252L;

	/**
	 * Menü Datei.
	 */
	private JMenu dateiMenu = new JMenu("Datei");

	/**
	 * Menü Datei -&gt; Quellverzeichnis durchsuchen.
	 */
	private JMenuItem searchSourceItem = new JMenuItem(
			CommandConstants.QUELLVERZEICHNIS_DURCHSUCHEN);

	/**
	 * Menü Datei -&gt; Zielverzeichnis durchsuchen.
	 */
	private JMenuItem searchTargetItem = new JMenuItem(
			CommandConstants.ZIELVERZEICHNIS_DURCHSUCHEN);

	/**
	 * Menü Datei -&gt; Filter setzen.
	 */
	private JMenuItem setFilterItem = new JMenuItem(
			CommandConstants.FILTER_SETZEN);

	/**
	 * Menü Bearbeiten.
	 */
	private JMenu bearbeitenMenu = new JMenu("Bearbeiten");

	/**
	 * Menü Bearbeiten -&gt; Synchronisieren.
	 */
	private JMenuItem synchronizeItem = new JMenuItem(
			CommandConstants.SYNCHRONIZE);;

	/**
	 * Menü Bearbeiten -&gt; Abbrechen.
	 */
	private JMenuItem abbrechenItem = new JMenuItem(CommandConstants.ABBRECHEN);

	/**
	 * Menü Bearbeiten -&gt; Ziel löschen.
	 */
	private JMenuItem deleteTargetItem = new JMenuItem(
			CommandConstants.ZIEL_LOESCHEN);

	/**
	 * Menü Bearbeiten -&gt; Aufräumen.
	 */
	private JMenuItem cleanTargetItem = new JMenuItem(CommandConstants.CLEAN);

	/**
	 * Erzeugt das Menü.
	 */
	public DropDownMenu() {
		dateiMenu.setMnemonic('D');
		bearbeitenMenu.setMnemonic('B');

		searchSourceItem.setMnemonic('Q');
		searchSourceItem
				.setActionCommand(CommandConstants.QUELLVERZEICHNIS_DURCHSUCHEN);
		searchTargetItem
				.setActionCommand(CommandConstants.ZIELVERZEICHNIS_DURCHSUCHEN);
		setFilterItem.setActionCommand(CommandConstants.FILTER_SETZEN);

		dateiMenu.add(searchSourceItem);
		dateiMenu.add(searchTargetItem);
		dateiMenu.add(setFilterItem);

		synchronizeItem.setActionCommand(CommandConstants.SYNCHRONIZE);
		abbrechenItem.setActionCommand(CommandConstants.ABBRECHEN);
		deleteTargetItem.setActionCommand(CommandConstants.ZIEL_LOESCHEN);
		cleanTargetItem.setActionCommand(CommandConstants.CLEAN);

		bearbeitenMenu.add(synchronizeItem);
		bearbeitenMenu.add(abbrechenItem);
		bearbeitenMenu.add(deleteTargetItem);
		bearbeitenMenu.add(cleanTargetItem);

		this.add(dateiMenu);
		this.add(bearbeitenMenu);
	}

	/**
	 * Fügt einen ActionListener hinzu.
	 * 
	 * @param listener
	 *            ActionListener
	 */
	public final void addActionListener(final ActionListener listener) {
		searchSourceItem.addActionListener(listener);
		searchTargetItem.addActionListener(listener);
		setFilterItem.addActionListener(listener);
		synchronizeItem.addActionListener(listener);
		abbrechenItem.addActionListener(listener);
		deleteTargetItem.addActionListener(listener);
	}

	/**
	 * Aktiviert und Deaktiviert die Menüs.
	 * 
	 * @param enabled
	 *            Status der Menüs
	 */
	public final void setEnabled(final boolean enabled) {
		synchronizeItem.setEnabled(enabled);
		deleteTargetItem.setEnabled(enabled);
	}
}
