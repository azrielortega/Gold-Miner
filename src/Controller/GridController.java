package Controller;
import  Model.GoldMiner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.TextField;

import java.io.IOException;

public class GridController {

    @FXML
    private TextField gridnum;
    @FXML
    private Pane gPane = new Pane();
    @FXML
    private Button PitButton;
    @FXML
    private Button GoldButton;
    @FXML
    private Button BeaconButton;

    private static int gridNumber;
    private static int resetClick = 0;
    private static int pitClick = 0;
    private static int removeClick = 0;
    private static int goldClick = 0;
    private static int beaconClick = 0;
    private static Rectangle[][] rec;
    private static Image image = new Image("/Images/pit.png");
    private static ImagePattern ipPit = new ImagePattern(image);
    private static Image pGold = new Image("/Images/gold.png");
    private static ImagePattern ipGold = new ImagePattern(pGold);
    private static Image pBeacon = new Image("/Images/beacon.png");
    private static ImagePattern ipBeacon = new ImagePattern(pBeacon);
    private static Image pDirt = new Image("/Images/dirt.png");
    private static ImagePattern ipDirt = new ImagePattern(pDirt);
    private static Image pMiner = new Image("/Images/miner.png");
    private static ImagePattern ipMiner = new ImagePattern(pMiner);

    private static GoldMiner game = new GoldMiner(1);

    public void initialize() {
        int gridsize = 16; // INITIALIZATION
        gridsize = gridNumber;
        int squareSize = 35;
        if (gridNumber > 34) {
            squareSize = 20;
        }
        int panesize = squareSize * gridsize;
        gPane.setPrefSize(panesize, panesize);
        rec = new Rectangle[gridsize][gridsize];
        for (int i = 0; i < gridsize; i++) {
            for (int j = 0; j < gridsize; j++) {
                rec[i][j] = new Rectangle();
                rec[i][j].setX(i * squareSize);
                rec[i][j].setY(j * squareSize);
                rec[i][j].setWidth(squareSize);
                rec[i][j].setHeight(squareSize);
                rec[i][j].setStroke(Color.rgb(77, 58, 3));
                rec[i][j].setFill(ipDirt);
                gPane.getChildren().add(rec[i][j]);
            }
            rec[0][0].setFill(ipMiner);
        }
    }

    public void clickPit() {
        resetClick = 1;
        pitClick = 1;
        goldClick = 0;
        beaconClick = 0;
        removeClick = 0;

    }

    public void clickSelect() {
        resetClick = 0;
        pitClick = 0;
        goldClick = 0;
        beaconClick = 0;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmation");
        alert.setContentText("SELECTION CONFIRMED!");
        alert.showAndWait();
    }


    public void clickGold() {
        resetClick = 1;
        goldClick = 1;
        beaconClick = 0;
        pitClick = 0;
        removeClick = 0;
    }

    public void clickBeacon() {
        resetClick = 1;
        beaconClick = 1;
        goldClick = 0;
        pitClick = 0;
        removeClick = 0;
    }

    public void clickRemove() {
        resetClick = 1;
        removeClick = 1;
        goldClick = 0;
        pitClick = 0;
        beaconClick = 0;

    }


    public void handle(MouseEvent me) {
        double posX = me.getX();
        double posY = me.getY();
        int width = 35;
        int colX = (int) (posX / width);
        int colY = (int) (posY / width);
        if (pitClick == 1) { // PIT
            if ((colX != 0 || colY != 0) && game.getSpaceType(colY, colX) == 1) {
                rec[colX][colY].setFill(ipPit);
                game.setSpaceType(colY, colX, 2);
            }
        }
        if (goldClick == 1) { // GOLD
            if ((colX != 0 || colY != 0) && game.getCtrGold() < 1 && game.getSpaceType(colY, colX) == 1) {
                rec[colX][colY].setFill(ipGold);
                game.setSpaceType(colY, colX, 4);
            }
        }
        if (beaconClick == 1) { //BEACON
            if ((colX != 0 || colY != 0) && game.getSpaceType(colY, colX) == 1) {
                rec[colX][colY].setFill(ipBeacon);
                game.setSpaceType(colY, colX, 3);
            }
        }
        if (removeClick == 1) { //REMOVE
            if ((colX != 0 || colY != 0) && game.getSpaceType(colY, colX) != 1) {
                rec[colX][colY].setFill(ipDirt);
                game.setSpaceType(colY, colX, 1);
            }
        }
        game.printBoard();
        System.out.println();
    }

    public void OpenGridMenu(ActionEvent event) {
        gridNumber = Integer.parseInt(gridnum.getText());
        if (gridNumber < 8 && gridNumber > 64) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Warning");
            alert.setContentText("Enter the values 8 to 64 ONLY!");
            alert.showAndWait();
        } else {
            Parent root;
            game.setBoard(gridNumber);
            try {
                root = FXMLLoader.load(getClass().getClassLoader().getResource("view/GridMenu.fxml"));
                javafx.stage.Stage stage = new Stage();
                stage.setTitle("Grid Menu");
                stage.setScene(new Scene(root, 800, 700));
                stage.setResizable(false);
                stage.show();
                close(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void StartGame() {
        System.out.println("GAME START");
        rec[game.getMinerY()][game.getMinerX()].setFill(ipDirt);
        game.move();
        rec[game.getMinerY()][game.getMinerX()].setFill(ipMiner);

        System.out.println(game.getMinerX());
        System.out.println(game.getMinerY());

        rec[game.getMinerY()][game.getMinerX()].setFill(ipDirt);
        game.move();
        rec[game.getMinerY()][game.getMinerX()].setFill(ipMiner);

        System.out.println(game.getMinerX());
        System.out.println(game.getMinerY());

    }

    public void close(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

}
