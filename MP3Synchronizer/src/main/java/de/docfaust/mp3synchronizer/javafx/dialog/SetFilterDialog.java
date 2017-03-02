package de.docfaust.mp3synchronizer.javafx.dialog;

import java.io.IOException;
import java.util.ResourceBundle;

import de.docfaust.mp3synchronizer.client.model.constants.MessageConstants;
import de.docfaust.mp3synchronizer.client.util.Messages;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class SetFilterDialog {
    private Window owner;

    public SetFilterDialog(Window owner) {
	this.owner = owner;

    }

    public void show() {
	try {
	    Stage dialog = new Stage();
	    ResourceBundle resources = ResourceBundle.getBundle("messages");
	    Parent root = FXMLLoader.load(ClassLoader.getSystemResource("setfilter.fxml"), resources);

	    Scene scene = new Scene(root);
	    scene.getStylesheets().add(ClassLoader.getSystemResource("mp3synchronizer.css").toExternalForm());
	    dialog.getIcons().add(new Image(ClassLoader.getSystemResourceAsStream("MP3Synchronizer.jpg")));
	    dialog.setTitle(Messages.getString(MessageConstants.MP3SYNCHRONIZER_SETFILTER_TITLE));
	    dialog.setScene(scene);
	    dialog.setAlwaysOnTop(true);
	    dialog.initModality(Modality.APPLICATION_MODAL);
	    dialog.initOwner(owner);
	    dialog.showAndWait();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

}
