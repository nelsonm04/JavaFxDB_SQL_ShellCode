package org.example.javafxdb_sql_shellcode;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import org.example.javafxdb_sql_shellcode.db.ConnDbOps;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class PrimaryController implements Initializable {

    private final ConnDbOps db = new ConnDbOps();
    private final ObservableList<Person> data = FXCollections.observableArrayList();

    @FXML
    private TextField first_name, last_name, department, major;

    @FXML
    private TableView<Person> tv;

    @FXML
    private TableColumn<Person, Integer> tv_id;

    @FXML
    private TableColumn<Person, String> tv_fn, tv_ln, tv_dept, tv_major;

    @FXML
    private ImageView img_view;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tv_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tv_fn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tv_ln.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tv_dept.setCellValueFactory(new PropertyValueFactory<>("dept"));
        tv_major.setCellValueFactory(new PropertyValueFactory<>("major"));

        loadPeopleFromDB(); // Fill table from Azure DB

        // Keyboard shortcuts - wait until scene is ready
        Platform.runLater(() -> {
            tv.getScene().getAccelerators().put(
                    new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN),
                    this::openFileAction
            );
        });
    }

    private void loadPeopleFromDB() {
        data.clear();
        data.addAll(db.getAllPeople()); // Pull from Azure
        tv.setItems(data);
    }

    @FXML
    private void addNewRecord() {
        Person p = new Person(null,
                first_name.getText(),
                last_name.getText(),
                department.getText(),
                major.getText()
        );
        db.insertPerson(p);      // Add to Azure
        loadPeopleFromDB();      // Refresh UI
        clearForm();
    }

    @FXML
    private void deleteRecord() {
        Person selected = tv.getSelectionModel().getSelectedItem();
        if (selected != null) {
            db.deletePersonById(selected.getId()); // Delete from Azure
            loadPeopleFromDB();                    // Refresh UI
            clearForm();
        }
    }

    @FXML
    private void editRecord() {
        // Placeholder — you'll add db.updatePerson() here
        System.out.println("Edit clicked. Not implemented yet.");
    }

    @FXML
    private void clearForm() {
        first_name.clear();
        last_name.clear();
        department.clear();
        major.clear();
    }

    @FXML
    private void closeApplication() {
        System.exit(0);
    }

    @FXML
    private void showImage() {
        File file = new FileChooser().showOpenDialog(img_view.getScene().getWindow());
        if (file != null) {
            img_view.setImage(new Image(file.toURI().toString()));
        }
    }

    @FXML
    private void selectedItemTV(MouseEvent event) {
        Person selected = tv.getSelectionModel().getSelectedItem();
        if (selected != null) {
            first_name.setText(selected.getFirstName());
            last_name.setText(selected.getLastName());
            department.setText(selected.getDept());
            major.setText(selected.getMajor());
        }
    }

    @FXML
    private void openFileAction() {
        System.out.println("Ctrl + F was pressed.");
        // Add useful functionality here, like opening a file dialog or showing about info
    }
}
