package de.docfaust.mp3synchronizer.client.view;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class MP3SynchronizerGUI extends JFrame {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -4429533403555940687L;

    private static final String IMG_MP3SYNCHRONIZER_ICO = "MP3Synchronizer.jpg";

    private DropDownMenu menu = null;

    private InputPanel inputPanel = null;

    private ProgressPanel progressPanel = null;

    private ButtonPanel buttonPanel = null;

    public MP3SynchronizerGUI() {
	try {
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	} catch (Exception e) {
	    e.printStackTrace();
	}

	inputPanel = new InputPanel();
	progressPanel = new ProgressPanel();
	buttonPanel = new ButtonPanel();
	menu = new DropDownMenu();

	this.setJMenuBar(menu);
	this.getContentPane().add(inputPanel, BorderLayout.NORTH);
	this.getContentPane().add(progressPanel, BorderLayout.CENTER);
	this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

	this.setTitle("MP3Synchronizer");
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setLocationByPlatform(true);

	URL url = ClassLoader.getSystemResource(IMG_MP3SYNCHRONIZER_ICO);
	Image i = Toolkit.getDefaultToolkit().getImage(url);
	this.setIconImage(i);
	this.pack();
	this.setResizable(false);
    }

    public final void addActionListener(final ActionListener listener) {
	inputPanel.addActionListener(listener);
	buttonPanel.addActionListener(listener);
	menu.addActionListener(listener);
    }

    public final ButtonPanel getButtonPanel() {
	return buttonPanel;
    }

    public final InputPanel getInputPanel() {
	return inputPanel;
    }

    public final ProgressPanel getProgressPanel() {
	return progressPanel;
    }

    public final DropDownMenu getMenu() {
	return menu;
    }
}
