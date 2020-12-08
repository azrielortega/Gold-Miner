import Controller.MainMenuController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.util.Duration;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Button btn = new Button("ClickMe");
        Group group = new Group(btn);
        Scene scene = new Scene(group, 600, 600);

        Parent root = FXMLLoader.load(getClass().getResource("view/MainMenu.fxml"));
        primaryStage.setTitle("Gold Miner");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
