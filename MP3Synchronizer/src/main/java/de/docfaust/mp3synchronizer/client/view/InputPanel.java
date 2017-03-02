package de.docfaust.mp3synchronizer.client.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.docfaust.mp3synchronizer.client.model.ConfigurationModel;
import de.docfaust.mp3synchronizer.client.model.constants.CommandConstants;

public class InputPanel extends JPanel {

	private static final int TWENTY = 20;

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -1597926285156841384L;

	private static final String ZIELVERZEICHNIS = "Zielverzeichnis:";

	private static final String QUELLVERZEICHNIS = "Quellverzeichnis:";

	private JPanel labelPanel = new JPanel(new GridLayout(2, 1));

	private JPanel fieldPanel = new JPanel(new GridLayout(2, 1));

	private JPanel searchButtonPanel = new JPanel(new GridLayout(2, 1));

	private JLabel sourceDirLabel = new JLabel(QUELLVERZEICHNIS);

	private JLabel targetDirLabel = new JLabel(ZIELVERZEICHNIS);

	private JTextField sourceDirField = new JTextField(TWENTY);

	private JTextField targetDirField = new JTextField(TWENTY);

	private JButton searchSourceButton = new JButton(
			CommandConstants.QUELLVERZEICHNIS_DURCHSUCHEN);

	private JButton searchTargetButton = new JButton(
			CommandConstants.ZIELVERZEICHNIS_DURCHSUCHEN);

	public InputPanel() {
		labelPanel.add(sourceDirLabel);
		labelPanel.add(targetDirLabel);

		sourceDirField.setText(ConfigurationModel.getInstance()
				.getLastSourceDir());
		sourceDirField
				.setToolTipText("Geben sie hier das Quellverzeichnis ein, oder wählen Sie eines durch den nebenstehenden Button!");
		targetDirField.setText(ConfigurationModel.getInstance()
				.getLastTargetDir());
		targetDirField
				.setToolTipText("Geben sie hier das Zielverzeichnis ein, oder wählen Sie eines durch den nebenstehenden Button!");
		fieldPanel.add(sourceDirField);
		fieldPanel.add(targetDirField);

		searchSourceButton
				.setActionCommand(CommandConstants.QUELLVERZEICHNIS_DURCHSUCHEN);
		searchSourceButton.setMnemonic('D');
		searchSourceButton.setToolTipText("Wählen Sie ein Quellverzeichnis");
		searchTargetButton
				.setActionCommand(CommandConstants.ZIELVERZEICHNIS_DURCHSUCHEN);
		searchTargetButton.setMnemonic('u');
		searchTargetButton.setToolTipText("Wählen Sie ein Zielverzeichnis");

		searchButtonPanel.add(searchSourceButton);
		searchButtonPanel.add(searchTargetButton);

		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEtchedBorder());

		this.add(labelPanel, BorderLayout.WEST);
		this.add(fieldPanel, BorderLayout.CENTER);
		this.add(searchButtonPanel, BorderLayout.EAST);
	}

	public final void addActionListener(final ActionListener listener) {
		searchSourceButton.addActionListener(listener);
		searchTargetButton.addActionListener(listener);
	}

	public final JTextField getSourceDirField() {
		return sourceDirField;
	}

	public final void setSourceDirField(final JTextField sourceDirField) {
		this.sourceDirField = sourceDirField;
	}

	public final JTextField getTargetDirField() {
		return targetDirField;
	}

	public final void setTargetDirField(final JTextField targetDirField) {
		this.targetDirField = targetDirField;
	}
}
