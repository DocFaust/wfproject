package de.docfaust.mp3synchronizer.javafx.control;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.mp3synchronizer.client.control.StatisticListener;
import de.docfaust.mp3synchronizer.client.model.ConfigurationModel;
import de.docfaust.mp3synchronizer.client.model.StatisticModel;
import de.docfaust.mp3synchronizer.client.model.constants.MessageConstants;
import de.docfaust.mp3synchronizer.client.util.MP3Synchronizer;
import de.docfaust.mp3synchronizer.client.util.Messages;
import de.docfaust.mp3synchronizer.javafx.dialog.SetFilterDialog;
import de.docfaust.mp3synchronizer.javafx.model.Statistic;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

public class MP3SynchronizerController implements Initializable, StatisticListener {

    @FXML
    Label lblTotalFiles;
    @FXML
    Label lblTotalLength;
    @FXML
    Label lblTotalTime;
    @FXML
    Label lblCurrentFiles;
    @FXML
    Label lblCurrentLength;
    @FXML
    Label lblCurrentTime;
    @FXML
    Label lblAvgTime;
    @FXML
    Label lblAvgSpeed;
    @FXML
    ProgressBar pbProgress;
    @FXML
    Label lblStatus;
    @FXML
    TextField fldSourceDir;
    @FXML
    TextField fldTargetDir;
    private ResourceBundle resources;

    private MP3Synchronizer mP3Synchronizer = new MP3Synchronizer();
    @FXML
    MenuItem menuSynchronize;
    @FXML
    MenuItem menuCancel;
    @FXML
    MenuItem menuDelete;
    @FXML
    MenuItem menuClean;
    @FXML
    Button btnSynchronize;
    @FXML
    Button btnCancel;
    @FXML
    Button btnDelete;
    @FXML
    Button btnClean;
    private Logger logger = LoggerFactory.getLogger(getClass());
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

    private final Statistic statistic = new Statistic();
    @FXML
    private MenuItem menuSetFilter;

    @FXML
    public void handleSearchSource(ActionEvent event) {
	DirectoryChooser chooser = new DirectoryChooser();
	chooser.setTitle(Messages.getString("chooser.open"));
	File dir = new File(ConfigurationModel.getInstance().getLastSourceDir());
	if (dir.exists()) {
	    chooser.setInitialDirectory(dir);
	}
	File f = chooser.showDialog(fldSourceDir.getScene().getWindow());
	if (f != null) {
	    fldSourceDir.setText(f.getAbsolutePath());
	    ConfigurationModel.getInstance().setLastSourceDir(f.getAbsolutePath());
	}
    }

    @FXML
    public void handleSearchTarget(ActionEvent event) {
	DirectoryChooser chooser = new DirectoryChooser();
	chooser.setTitle(Messages.getString("chooser.open"));
	File dir = new File(ConfigurationModel.getInstance().getLastTargetDir());
	if (dir.exists()) {
	    chooser.setInitialDirectory(dir);
	}
	File f = chooser.showDialog(fldSourceDir.getScene().getWindow());
	if (f != null) {
	    fldTargetDir.setText(f.getAbsolutePath());
	    ConfigurationModel.getInstance().setLastTargetDir(f.getAbsolutePath());
	}
    }

    @FXML
    public void handleSetFilter(ActionEvent event) {
	SetFilterDialog dialog = new SetFilterDialog(btnSynchronize.getScene().getWindow());
	dialog.show();
    }

    @FXML
    public void handleSynchronize(ActionEvent event) {
	if ("".equals(fldSourceDir.getText()) || "".equals(fldTargetDir.getText())) {
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle(Messages.getString(MessageConstants.MP3SYNCHRONIZER_CONTROL_NOPATH_TITLE));
	    alert.setContentText(Messages.getString(MessageConstants.MP3SYNCHRONIZER_CONTROL_NOPATH_MESSAGE));
	    alert.showAndWait();
	} else {
	    synchronize();
	}

    }

    private void synchronize() {
	new Thread(new Runnable() {
	    public void run() {
		setDisabled(true);
		mP3Synchronizer.addStatisticListener(MP3SynchronizerController.this);
		mP3Synchronizer.setSourceDirectory(fldSourceDir.getText());
		mP3Synchronizer.setTargetDirectory(fldTargetDir.getText());
		mP3Synchronizer.collectFiles();
		if (mP3Synchronizer.isEmpty()) {
		    Platform.runLater(new Runnable() {

			@Override
			public void run() {
			    Alert alert = new Alert(AlertType.INFORMATION);
			    alert.setTitle(Messages.getString(MessageConstants.MP3SYNCHRONIZER_CONTROL_NOFILES_TITLE));
			    alert.setContentText(Messages.getString(MessageConstants.MP3SYNCHRONIZER_CONTROL_NOFILES_MESSAGE));
			    alert.showAndWait();
			}
		    });
		}

		logger.info("Synchronisiere");
		mP3Synchronizer.synchronize();
		mP3Synchronizer.removeStatisticListener(MP3SynchronizerController.this);
		setDisabled(false);
	    }
	}).start();

    }

    private void setDisabled(boolean disabled) {
	btnClean.setDisable(disabled);
	btnDelete.setDisable(disabled);
	btnCancel.setDisable(!disabled);
	btnSynchronize.setDisable(disabled);
	menuClean.setDisable(disabled);
	menuCancel.setDisable(!disabled);
	menuSynchronize.setDisable(disabled);
	menuDelete.setDisable(disabled);
    }

    @FXML
    public void handleCancel(ActionEvent event) {
	setDisabled(false);
    }

    @FXML
    public void handleDeleteTarget(ActionEvent event) {
	Alert alert = new Alert(AlertType.CONFIRMATION);
	alert.setTitle(Messages.getString(MessageConstants.MP3SYNCHRONIZER_CONTROL_DELETEREADY_TITLE));
	alert.setContentText(Messages.getString(MessageConstants.MP3SYNCHRONIZER_CONTROL_CONFIRMDELETE_MESSAGE));
	Optional<ButtonType> optional = alert.showAndWait();
	if (optional.isPresent() && optional.get() == ButtonType.OK) {

	    mP3Synchronizer.setSourceDirectory(fldSourceDir.getText());
	    mP3Synchronizer.setTargetDirectory(fldTargetDir.getText());
	    mP3Synchronizer.deleteTarget();

	    Alert alertReady = new Alert(AlertType.INFORMATION);
	    alertReady.setTitle(Messages.getString(MessageConstants.MP3SYNCHRONIZER_CONTROL_DELETEREADY_TITLE));
	    alertReady.setContentText(Messages.getString(MessageConstants.MP3SYNCHRONIZER_CONTROL_DELETEREADY_MESSAGE));
	    alertReady.showAndWait();
	}

    }

    @FXML
    public void handleCleanTarget(ActionEvent event) {
	Alert alert = new Alert(AlertType.CONFIRMATION);
	alert.setTitle(Messages.getString(MessageConstants.MP3SYNCHRONIZER_CONTROL_CONFIRMCLEAN_TITLE));
	alert.setContentText(Messages.getString(MessageConstants.MP3SYNCHRONIZER_CONTROL_CONFIRMCLEAN_MESSAGE));
	Optional<ButtonType> optional = alert.showAndWait();
	if (optional.isPresent() && optional.get() == ButtonType.OK) {

	    mP3Synchronizer.setSourceDirectory(fldSourceDir.getText());
	    mP3Synchronizer.setTargetDirectory(fldTargetDir.getText());
	    mP3Synchronizer.cleanUp();
	    Alert alertReady = new Alert(AlertType.INFORMATION);
	    alertReady.setTitle(Messages.getString(MessageConstants.MP3SYNCHRONIZER_CONTROL_CLEANREADY_TITLE));
	    alertReady.setContentText(Messages.getString(MessageConstants.MP3SYNCHRONIZER_CONTROL_CLEANREADY_MESSAGE));
	    alertReady.showAndWait();
	}
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
	this.resources = resources;
	setDisabled(false);
	fldSourceDir.setText(ConfigurationModel.getInstance().getLastSourceDir());
	fldTargetDir.setText(ConfigurationModel.getInstance().getLastTargetDir());

	lblTotalFiles.textProperty().bind(statistic.getTotalFilesProperty());
	lblTotalLength.textProperty().bind(statistic.getTotalLengthProperty());
	lblTotalTime.textProperty().bind(statistic.getTotalTimeProperty());
	lblCurrentFiles.textProperty().bind(statistic.getCurrentFilesProperty());
	lblCurrentLength.textProperty().bind(statistic.getCurrentLengthProperty());
	lblAvgTime.textProperty().bind(statistic.getAvgTimeProperty());
	lblAvgSpeed.textProperty().bind(statistic.getAvgSpeedProperty());
	pbProgress.progressProperty().bind(statistic.getProgressProperty());
	lblStatus.textProperty().bind(statistic.getStatusProperty());
    }

    @Override
    public void statisticUpdated() {
	SimpleDateFormat format = new SimpleDateFormat(HH_MM_SS);

	StatisticModel statisticModel = StatisticModel.getInstance();

	Platform.runLater(new Runnable() {
	    @Override
	    public void run() {

		statistic.setTotalFiles(statisticModel.getTotalFiles() + " Dateien");
		statistic.setTotalLength((float) statisticModel.getTotalLength() / (ONE_KB_FLOAT * ONE_KB_FLOAT) + MB);

		Calendar cal = new GregorianCalendar(1, 0, 1, 0, 0, 0);
		cal.add(Calendar.MILLISECOND, (int) statisticModel.getTotalTime());
		statistic.setTotalTime(format.format(cal.getTime()));

		statistic.setCurrentFiles(statisticModel.getCurrentFiles() + "Dateien");
		statistic.setCurrentLength((float) statisticModel.getCurrentLength() / (ONE_KB_FLOAT * ONE_KB_FLOAT) + MB);

		cal = new GregorianCalendar(1, 0, 1, 0, 0, 0);
		cal.add(Calendar.MILLISECOND, (int) statisticModel.getCurrentTime());

		statistic.setCurrentTime(format.format(cal.getTime()));

		statistic.setAvgTime((float) (statisticModel.getAverageTime() / FACTOR_MB) + " ms/MB");

		statistic.setAvgSpeed(((statisticModel.getAverageSpeed() / ONE_KB_FLOAT) * ONETHOUSAND) + " KB/s");

		statistic.setStatus(statisticModel.getStatus());
		statistic.setProgress((int) Math.round(((double) statisticModel.getCurrentLength() / (double) statisticModel.getTotalLength()) * ONEHUNDRED));
	    }
	});

    }

}
