package de.docfaust.mp3synchronizer.javafx.control;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import de.docfaust.mp3synchronizer.client.model.ExtensionsModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SetFilterController implements Initializable {

    @FXML
    private TextField fldNewExtension;
    @FXML
    private ListView<String> listExtensions;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnClose;

    private ResourceBundle resources;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnOk;

    @FXML
    public void handleAdd(ActionEvent event) {
	String ext = fldNewExtension.getText().toUpperCase();
	fldNewExtension.setText("");
	listExtensions.getItems().add(ext);
    }

    @FXML
    public void handleDelete(ActionEvent event) {
	String ext = listExtensions.getSelectionModel().getSelectedItem();
	listExtensions.getItems().remove(ext);
    }

    @FXML
    public void handleOk(ActionEvent event) {
	ExtensionsModel.getInstance().setExtensions(listExtensions.getItems());
	ExtensionsModel.getInstance().saveExtensions();
	((Stage) btnOk.getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
	this.resources = resources;
	List<String> extensions = ExtensionsModel.getInstance().getExtensions();
	listExtensions.getItems().addAll(extensions);
    }

    @FXML
    public void handleCancel(ActionEvent event) {
	((Stage) btnCancel.getScene().getWindow()).close();
    }

}
