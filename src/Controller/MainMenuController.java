package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import Model.GoldMiner;

import java.io.IOException;

public class MainMenuController {

    @FXML
    private TextField gridNum;

    public void OpenSettings(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("view/SettingsMenu.fxml"));
            javafx.stage.Stage stage = new Stage();
            stage.setTitle("Settings");
            stage.setScene(new Scene(root, 600, 400));
            stage.setResizable(false);
            stage.show();

            close(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void OpenStart(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("view/GridSize.fxml"));
            javafx.stage.Stage stage = new Stage();
            stage.setTitle("Enter Grid Size");
            stage.setScene(new Scene(root, 600, 600));
            stage.setResizable(false);
            stage.show();

            close(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Exit(){
        System.exit(0);
    }

    public void close(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
