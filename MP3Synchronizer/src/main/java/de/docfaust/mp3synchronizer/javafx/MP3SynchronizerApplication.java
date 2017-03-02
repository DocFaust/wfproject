package de.docfaust.mp3synchronizer.javafx;

import java.io.IOException;
import java.util.ResourceBundle;

import de.docfaust.mp3synchronizer.client.model.constants.MessageConstants;
import de.docfaust.mp3synchronizer.client.util.Messages;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MP3SynchronizerApplication extends Application {

    @Override
    public void start(final Stage primaryStage) throws IOException {
	ResourceBundle resources = ResourceBundle.getBundle("messages");
	Parent root = FXMLLoader.load(ClassLoader.getSystemResource("mp3synchronizer.fxml"), resources);

	Scene scene = new Scene(root);
	scene.getStylesheets().add(ClassLoader.getSystemResource("mp3synchronizer.css").toExternalForm());
	primaryStage.getIcons().add(new Image(ClassLoader.getSystemResourceAsStream("MP3Synchronizer.jpg")));
	primaryStage.setTitle(Messages.getString(MessageConstants.MP3SYNCHRONIZER_TITLE));
	primaryStage.setScene(scene);
	primaryStage.show();
    }

    public static void main(String[] args) {
	launch(args);
    }
}
