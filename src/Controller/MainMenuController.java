package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class MainMenuController {
	
	private Object Stage;

	    /**
	     * Exits the Program
	     */
	public void exitprogram() {
		System.exit(0);
	}
	
	public void displayMainMenu (ActionEvent ev) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("view/MainMenu.fxml"));
			javafx.stage.Stage stage = new Stage();
            stage.setTitle("Main Menu");
            stage.setScene(new Scene(root, 600, 600));
            stage.setResizable(false);
            stage.getIcons().add(new Image("/Gold Miner Logo.png"));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
		}
		catch (IOException e) {
            e.printStackTrace();
        }
	}
	
}
