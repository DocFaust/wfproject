package de.docfaust.mp3synchronizer.client.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import de.docfaust.mp3synchronizer.client.model.ExtensionsModel;

/**
 * Dialog zum Setzen des Filters.
 * 
 * @author XHU1011
 *
 */
public class SetFilterDialog extends JDialog implements ActionListener {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 8341856031090343191L;

	private static final String ABBRECHEN = "Abbrechen";

	private static final String OK = "Ok";

	private static final String LOESCHEN = "Löschen";

	private static final String ERWEITERUNGEN = "Erweiterungen: ";

	private static final String HINZUFUEGEN = "Hinzufügen";

	private static final String NEUE_ERWEITERUNG = "Neue Erweiterung: ";

	private JPanel addPanel = null;

	private JLabel addLabel = null;

	private JTextField addField = null;

	private JButton addButton = null;

	private JPanel listPanel = null;

	private JLabel listLabel = null;

	private JScrollPane listPane = null;

	private JList extensions = null;

	private JButton deleteButton = null;

	private JPanel okButtonPanel = null;

	private JButton okButton = null;

	private JButton abbrechenButton = null;

	private JPanel delPanel;

	private JPanel listLabelPanel;

	private Vector<String> myExtensions = null;

	public SetFilterDialog(final JFrame parent) {
		super(parent, true);
		myExtensions = new Vector<String>(ExtensionsModel.getInstance()
				.getExtensions());
		addPanel = new JPanel(new GridLayout(1, 3));
		addLabel = new JLabel(NEUE_ERWEITERUNG);
		addField = new JTextField(5);
		addButton = new JButton(HINZUFUEGEN);
		addButton.setActionCommand(HINZUFUEGEN);
		addButton.addActionListener(this);

		addPanel.add(addLabel);
		addPanel.add(addField);
		addPanel.add(addButton);

		listPanel = new JPanel(new GridLayout(1, 3));
		listLabel = new JLabel(ERWEITERUNGEN);
		listLabelPanel = new JPanel(new BorderLayout());
		listLabelPanel.add(listLabel, BorderLayout.NORTH);
		listLabelPanel.add(new JPanel(), BorderLayout.CENTER);

		extensions = new JList((Vector<String>) ExtensionsModel.getInstance()
				.getExtensions());
		listPane = new JScrollPane(extensions,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		delPanel = new JPanel(new BorderLayout());
		deleteButton = new JButton(LOESCHEN);
		deleteButton.setActionCommand(LOESCHEN);
		deleteButton.addActionListener(this);
		delPanel.add(deleteButton, BorderLayout.NORTH);
		delPanel.add(new JPanel(), BorderLayout.CENTER);

		listPanel.add(listLabelPanel);
		listPanel.add(listPane);
		listPanel.add(delPanel);

		okButtonPanel = new JPanel(new FlowLayout());
		okButton = new JButton(OK);
		okButton.setActionCommand(OK);
		okButton.setMnemonic('O');
		okButton.addActionListener(this);
		abbrechenButton = new JButton(ABBRECHEN);
		abbrechenButton.setActionCommand(ABBRECHEN);
		abbrechenButton.setMnemonic('A');
		abbrechenButton.addActionListener(this);
		okButtonPanel.add(okButton);
		okButtonPanel.add(abbrechenButton);

		this.getContentPane().add(addPanel, BorderLayout.NORTH);
		this.getContentPane().add(listPanel, BorderLayout.CENTER);
		this.getContentPane().add(okButtonPanel, BorderLayout.SOUTH);
		this.setTitle("Filter setzen");
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(parent);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	@Override
	public final void actionPerformed(final ActionEvent e) {
		if (HINZUFUEGEN.equals(e.getActionCommand())) {
			myExtensions.addElement(addField.getText().toUpperCase());
			addField.setText("");
			extensions.setListData(myExtensions);
			repaint();
		} else if (LOESCHEN.equals(e.getActionCommand())) {
			String value = (String) extensions.getSelectedValue();
			myExtensions.remove(value);
			extensions.setListData(myExtensions);
			repaint();
		} else if (OK.equals(e.getActionCommand())) {
			ExtensionsModel.getInstance().setExtensions(myExtensions);
			ExtensionsModel.getInstance().saveExtensions();
			this.setVisible(false);
		} else if (ABBRECHEN.equals(e.getActionCommand())) {
			this.setVisible(false);
		}
	}
}
