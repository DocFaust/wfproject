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
	 * Men� Datei.
	 */
	private JMenu dateiMenu = new JMenu("Datei");

	/**
	 * Men� Datei -&gt; Quellverzeichnis durchsuchen.
	 */
	private JMenuItem searchSourceItem = new JMenuItem(
			CommandConstants.QUELLVERZEICHNIS_DURCHSUCHEN);

	/**
	 * Men� Datei -&gt; Zielverzeichnis durchsuchen.
	 */
	private JMenuItem searchTargetItem = new JMenuItem(
			CommandConstants.ZIELVERZEICHNIS_DURCHSUCHEN);

	/**
	 * Men� Datei -&gt; Filter setzen.
	 */
	private JMenuItem setFilterItem = new JMenuItem(
			CommandConstants.FILTER_SETZEN);

	/**
	 * Men� Bearbeiten.
	 */
	private JMenu bearbeitenMenu = new JMenu("Bearbeiten");

	/**
	 * Men� Bearbeiten -&gt; Synchronisieren.
	 */
	private JMenuItem synchronizeItem = new JMenuItem(
			CommandConstants.SYNCHRONIZE);;

	/**
	 * Men� Bearbeiten -&gt; Abbrechen.
	 */
	private JMenuItem abbrechenItem = new JMenuItem(CommandConstants.ABBRECHEN);

	/**
	 * Men� Bearbeiten -&gt; Ziel l�schen.
	 */
	private JMenuItem deleteTargetItem = new JMenuItem(
			CommandConstants.ZIEL_LOESCHEN);

	/**
	 * Men� Bearbeiten -&gt; Aufr�umen.
	 */
	private JMenuItem cleanTargetItem = new JMenuItem(CommandConstants.CLEAN);

	/**
	 * Erzeugt das Men�.
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
	 * F�gt einen ActionListener hinzu.
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
	 * Aktiviert und Deaktiviert die Men�s.
	 * 
	 * @param enabled
	 *            Status der Men�s
	 */
	public final void setEnabled(final boolean enabled) {
		synchronizeItem.setEnabled(enabled);
		deleteTargetItem.setEnabled(enabled);
	}
}
