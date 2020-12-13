package Controller;

import Model.GoldMiner;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.Key;

import javafx.util.Duration;

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
    @FXML
    private Label MoveCounter;
    @FXML
    private Label RotateCounter;
    @FXML
    private Label ScanCounter;

    private static int gridNumber;
    private static int resetClick = 0;
    private static int pitClick = 0;
    private static int removeClick = 0;
    private static int goldClick = 0;
    private static int beaconClick = 0;
    private static int startClick = 0;
    private static int diffClick = 0;
    private static int speed = 500;
    private static Rectangle[][] rec;
    private static Image image = new Image("/Images/pit.png");
    private static ImagePattern ipPit = new ImagePattern(image);
    private static Image pGold = new Image("/Images/gold.png");
    private static ImagePattern ipGold = new ImagePattern(pGold);
    private static Image pBeacon = new Image("/Images/beacon.png");
    private static ImagePattern ipBeacon = new ImagePattern(pBeacon);
    private static Image pDirt = new Image("/Images/dirt.png");
    private static ImagePattern ipDirt = new ImagePattern(pDirt);
    //RIGHT
    private static Image pMinerRight = new Image("/Images/miner.png");
    private static ImagePattern ipMinerRight = new ImagePattern(pMinerRight);
    //LEFT
    private static Image pMinerLeft = new Image("/Images/left view.png");
    private static ImagePattern ipMinerLeft = new ImagePattern(pMinerLeft);
    //UP
    private static Image pMinerUp = new Image("/Images/top view.png");
    private static ImagePattern ipMinerUp = new ImagePattern(pMinerUp);
    //DOWN
    private static Image pMinerDown = new Image("/Images/down view.png");
    private static ImagePattern ipMinerDown = new ImagePattern(pMinerDown);

    //MINER IN GOLD
    private static Image pMinerGold = new Image("/Images/MinerInGold.png");
    private static ImagePattern ipMinerGold = new ImagePattern(pMinerGold);

    //MINER IN PIT
    private static Image pMinerPit = new Image("/Images/MinerInPit.png");
    private static ImagePattern ipMinerPit = new ImagePattern(pMinerPit);

    //MINER IN BEACON
    private static Image pMinerBeacon = new Image("/Images/MinerInBeacon.png");
    private static ImagePattern ipMinerBeacon = new ImagePattern(pMinerBeacon);

    //SCANNING TILE
    private static Image pScanTile = new Image("/Images/scanTile.png");
    private static ImagePattern ipScanTile = new ImagePattern(pScanTile);

    private static ImagePattern ipMiner = new ImagePattern(pMinerRight);

    private static GoldMiner game = new GoldMiner(1);

    private Timeline animation;
    private Timeline temp;

    private int tempCtr = 0;

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
        game.printBoard();
        System.out.println("X- " + game.getMinerX());
        System.out.println("Y- " + game.getMinerY());
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
        if (startClick != 1) {
            double posX = me.getX();
            double posY = me.getY();
            int width = 35;
            int colX = (int) (posX / width);
            int colY = (int) (posY / width);
            if (pitClick == 1) { // PIT
                if ((colX != 0 || colY != 0) && game.getSpaceType(colY, colX) == 1 && game.getCtrGold() == 1 && game.isPitValid(colY, colX)) {
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
                if ((colX != 0 || colY != 0) && game.getSpaceType(colY, colX) == 1 && game.getCtrGold() == 1 && game.isBeaconValid(colY, colX)) {
                    System.out.println(game.isBeaconValid(colY, colX));
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
    }

    public void SmartClick(){
    diffClick = 1;
    }

    public void RandomClick(){
    diffClick=2;
    }

    public void fastForward(){ speed = 50; animationMove(); }
    public void normalPlay(){ speed = 300; }

    public void OpenGridMenu(ActionEvent event) {
        gridNumber = Integer.parseInt(gridnum.getText());
        if (gridNumber < 8 && gridNumber > 64) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Warning");
            alert.setContentText("Enter the values 8 to 64 ONLY!");
            alert.showAndWait();
        }
        else if (diffClick == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Warning");
            alert.setContentText("Select a Difficulty level!");
            alert.showAndWait();
        }
        else {
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

    public void gridMenu(ActionEvent event) {
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

    public void openGoldFound(ActionEvent event) {
        Parent root;
        try {
            animation.stop();
            root = FXMLLoader.load(getClass().getClassLoader().getResource("view/GoldFound.fxml"));
            javafx.stage.Stage stage = new Stage();
            stage.setTitle("CONGRATULATIONS: GOLD FOUND!");
            stage.setScene(new Scene(root, 427, 239));
            stage.setResizable(false);
            stage.show();
            // close(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openGameOver(ActionEvent event) {
        Parent root;
        try {
            //animation.stop();
            root = FXMLLoader.load(getClass().getClassLoader().getResource("view/GameOver.fxml"));
            javafx.stage.Stage stage = new Stage();
            stage.setTitle("GAME OVER!");
            stage.setScene(new Scene(root, 427, 239));
            stage.setResizable(false);
            stage.show();

            //close(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void rotate(char direction) {
        System.out.println("ROTATED");
        game.rotate(direction);
        RotateCounter.setText(Integer.toString(game.getRotate()));
        switch (game.getMiner().getFront()) {
            case 1://UP
                ipMiner = new ImagePattern(pMinerUp);
                break;
            case 2://RIGHT
                ipMiner = new ImagePattern(pMinerRight);
                break;
            case 3://DOWN
                ipMiner = new ImagePattern(pMinerDown);
                break;
            case 4://LEFT
                ipMiner = new ImagePattern(pMinerLeft);
                break;
        }
        rec[game.getMinerY()][game.getMinerX()].setFill(ipMiner);
    }


    public void rotateTill(String direction) {
        switch (direction) {
            case "Up"://UP
                while (game.getMiner().getFront() != 1) {
                    rotate('R');
                }
                break;
            case "Right"://RIGHT
                while (game.getMiner().getFront() != 2) {
                    rotate('R');
                }
                break;
            case "Down"://DOWN
                while (game.getMiner().getFront() != 3) {
                    rotate('R');
                }
                break;
            case "Left"://LEFT
                while (game.getMiner().getFront() != 4) {
                    rotate('R');
                }
                break;
        }
    }

    public int scan() {
        int temp = game.scan();
        ScanCounter.setText(Integer.toString(game.getScan()));
        return temp;
    }

    public void ExitGame() {
    }

    public void close(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    public void StartGame() {
        normalPlay();
        startClick = 1;
        System.out.println("GAME START");
        //smartAI();
        animationMove();
        //smartAI();
    }

    public void move() {
        game.setSpaceType(game.getMinerX(), game.getMinerY(), 1);
        rec[game.getMinerY()][game.getMinerX()].setFill(ipDirt);
        game.move();
        game.setSpaceType(game.getMinerX(), game.getMinerY(), 5);
        rec[game.getMinerY()][game.getMinerX()].setFill(ipMiner);

        game.updateMemory(game.getMinerX(), game.getMinerY(), 5);

        MoveCounter.setText(Integer.toString(game.getMove()));
    }


    public void animationMove() {
        animation = new Timeline(new KeyFrame(Duration.millis(speed), event -> {
            //gameMove(x, y);
            //smartAI();
            //rotateTill("Left");
            //randomAI();

            if (diffClick == 1){
                smartAI();
            }
            else if (diffClick==2){
                //randomAI();
            }
            else{

            }

        }));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    //--------------------------------------------MINER AI SMART-------------------------------------------------------
    public void smartAI() {
        /*
        int i = 0;
        System.out.println(game.getMinerX() + " " + game.getMinerY());
        game.updateMemory(game.getMinerX(), game.getMinerY(), 1);

        String result = search(game.getMinerX(), game.getMinerY() + 1, "NULL");
        ActionEvent event = new ActionEvent();

        if (result == "true") {
            animation.stop();
            openGoldFound(event);

        } else {
            animation.stop();
            openGameOver(event);

        }

        boolean result = randomAI ();
        ActionEvent event = new ActionEvent();

        if (result){
            openGoldFound(event);
        }
        else{
            openGameOver(event);
        }*/

        String result = searchTwo(game.getMinerX(), game.getMinerY() + 1, "NULL");
        ActionEvent event = new ActionEvent();
        if (result == "true") {
            animation.stop();
            openGoldFound(event);


        } else {
            animation.stop();
            openGameOver(event);

        }
    }


    public String search(int x, int y, String direction) {//memory
        tempCtr++;
        String right = "";
        String left = "";
        String up = "";
        String down = "";
        boolean scan = false;
        String status = "false";
        System.out.println("Front - " + game.getMiner().getFront());
        game.printMemory();

        if (game.isEdge(x, y))
            return "false";

        if (game.getSpaceMemory(x, y) == 1) {//unexplored path
            System.out.println("Scanned");
            rotateTill(direction);
            int temp = scan();
            scan = true;

            /*
            if(temp == 4){ //GO TOWARDS GOLD ONCE FOUND
                while(game.getSpaceMemory(game.getMinerX(), game.getMinerY()) != 4) {
                    if(!game.isEdge(game.getMinerX() + 1, game.getMinerY()) && game.getSpaceMemory(game.getMinerX() + 1, game.getMinerY()) == 4)
                        return "true";
                    if(!game.isEdge(game.getMinerX() - 1, game.getMinerY()) && game.getSpaceMemory(game.getMinerX() - 1, game.getMinerY()) == 4)
                        return "true";
                    if(!game.isEdge(game.getMinerX(), game.getMinerY() + 1) && game.getSpaceMemory(game.getMinerX(), game.getMinerY() + 1) == 4)
                        return "true";
                    if(!game.isEdge(game.getMinerX(), game.getMinerY() - 1) &&game.getSpaceMemory(game.getMinerX(), game.getMinerY() - 1) == 4)
                        return "true";

                    move();

                    System.out.println("move is called");
                }

                if(game.getSpaceMemory(x, y) == 4){
                    return "true";
                }
            }

            if (temp == 3){//GO TO BEACON
                boolean arrived = false;
                while(!arrived) {
                    if(!game.isEdge(game.getMinerX() + 1, game.getMinerY()) && game.getSpaceMemory(game.getMinerX() + 1, game.getMinerY()) == 5) {
                        move();
                        arrived = true;
                    }
                    if(!game.isEdge(game.getMinerX() - 1, game.getMinerY()) && game.getSpaceMemory(game.getMinerX() - 1, game.getMinerY()) == 5) {
                        move();
                        arrived = true;
                    }
                    System.out.println(!game.isEdge(game.getMinerX(), game.getMinerY() + 1) && game.getSpaceMemory(game.getMinerX(), game.getMinerY() + 1) == 3);
                    if(!game.isEdge(game.getMinerX(), game.getMinerY() + 1) && game.getSpaceMemory(game.getMinerX(), game.getMinerY() + 1) == 5) {
                        move();
                        arrived = true;
                    }
                    if(!game.isEdge(game.getMinerX(), game.getMinerY() - 1) &&game.getSpaceMemory(game.getMinerX(), game.getMinerY() - 1) == 5) {
                        move();
                        arrived = true;
                    }
                    System.out.println("Memory");
                    game.printMemory();
                    System.out.println("Board");
                    game.printBoard();
                    System.out.println();
                    if (!arrived)
                        move();
                }

                if(game.getSpaceMemory(x, y) == 3){
                    move();
                    System.out.println("move is called");
                    arrived = true;
                }

                if (arrived){
                    if (findGold())
                        return "true";
                }
            }*/
        }

        if (game.getSpaceMemory(x, y) == 4) {
            return "true";
        }

        if (game.getSpaceMemory(x, y) == 7) //explored path
            return "false";

        if (game.getSpaceMemory(x, y) == 3 && tempCtr != 1) //pit
            return "false";
        else if (game.getSpaceMemory(x, y) == 3 && tempCtr == 1) {
            rotate('R');
            x++;
            y--;
        }

        if (!scan && direction != "NULL") {
            rotateTill(direction);
        }

        game.updateMemory(x, y, 7);
        move();
        System.out.println("MOVED " + direction);
        status = "moved";
        //UP
        up = search(x - 1, y, "Up");
        if (up == "true")
            return "true";
        else if (up == "moved") {
            System.out.println("Move Back! - Down");
            rotateTill("Down");
            move();
        }
        //RIGHT
        right = search(x, y + 1, "Right");
        if (right == "true")
            return "true";
        else if (right == "moved") {
            System.out.println("Move Back! - Left");
            rotateTill("Left");
            move();
            System.out.println("move is called");
        }
        //DOWN
        down = search(x + 1, y, "Down");
        if (down == "true")
            return "true";
        else if (down == "moved") {
            System.out.println("Move Back! - Up");
            rotateTill("Up");
            move();
            System.out.println("move is called");
        }
        //LEFT
        left = search(x, y - 1, "Left");
        if (left == "true")
            return "true";
        else if (left == "moved") {
            System.out.println("Move Back! - Right");
            rotateTill("Right");
            move();
            System.out.println("move is called");
        }

        boolean found = right == "true" || left == "true" || down == "true" || up == "true";

        if (found) {
            System.out.println("GOLD FOUND");
            return "true";
        } else {
            game.updateMemory(x, y, 3);
            System.out.println("Dead End");
        }

        return status;
    }

    public boolean findGold() {
        boolean found = false;
        int ctr = 0;
        while (!found) {
            if (ctr == 0)
                rotate('R');

            else
                rotate('L');

            int temp = scan();
            if (temp == 4) { //GO TOWARDS GOLD ONCE FOUND
                while (game.getSpaceMemory(game.getMinerX(), game.getMinerY()) != 4) {
                    if (!game.isEdge(game.getMinerX() + 1, game.getMinerY()) && game.getSpaceMemory(game.getMinerX() + 1, game.getMinerY()) == 4) {
                        found = true;
                        break;
                    }
                    if (!game.isEdge(game.getMinerX() - 1, game.getMinerY()) && game.getSpaceMemory(game.getMinerX() - 1, game.getMinerY()) == 4) {
                        found = true;
                        break;
                    }
                    if (!game.isEdge(game.getMinerX(), game.getMinerY() + 1) && game.getSpaceMemory(game.getMinerX(), game.getMinerY() + 1) == 4) {
                        found = true;
                        break;
                    }
                    if (!game.isEdge(game.getMinerX(), game.getMinerY() - 1) && game.getSpaceMemory(game.getMinerX(), game.getMinerY() - 1) == 4) {
                        found = true;
                        break;
                    }
                    move();
                }
                if (game.getSpaceMemory(game.getMinerX(), game.getMinerY()) == 4) {
                    found = true;
                }
            }
        }

        return found;
    }

//----------------------------------------------Miner AI Random---------------------------------------------------------

    public void randomAI() {

        //If miner is not in pit or in gold, continue;
        System.out.println("SPACE TYPE - " + game.getSpaceType(game.getMinerX(), game.getMinerY()));
        if (game.getSpaceTypeInMiner(game.getMinerX(), game.getMinerY()) == 2) { //PIT HULOG
            ActionEvent event = new ActionEvent();
            openGameOver(event);
        }
        if (game.getSpaceTypeInMiner(game.getMinerX(), game.getMinerY()) == 4) { //GOLD FOUND
            ActionEvent event = new ActionEvent();
            openGoldFound(event);
        }
        game.printBoard();
        System.out.println(randomNumber());
        int temp = randomNumber();

        switch (temp) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6://MOVE
                System.out.println("MOVED");
                boolean valid = true;
                switch (game.getMiner().getFront()) {
                    case 1:
                        System.out.println("Up");
                        if (game.isEdge(game.getMinerX() - 1, game.getMinerY())) {
                            rotate('R');
                            valid = false;
                        }
                        break;
                    case 2:
                        System.out.println("Right");
                        if (game.isEdge(game.getMinerX(), game.getMinerY() + 1)) {
                            rotate('R');
                            valid = false;
                        }
                        break;
                    case 3:
                        System.out.println("Down");
                        if (game.isEdge(game.getMinerX() + 1, game.getMinerY())) {
                            rotate('R');
                            valid = false;
                        }
                        break;
                    case 4:
                        System.out.println("Left");
                        if (game.isEdge(game.getMinerX(), game.getMinerY() - 1)) {
                            rotate('R');
                            valid = false;
                        }
                        break;
                }
                if (valid)
                    move();
                break;
            case 7:
            case 8://SCAN
                System.out.println("SCANNED");
                scan();
                break;
            case 9:
            case 10://ROTATE
                System.out.println("ROTATED");
                if (temp == 9)
                    rotate('R');
                else
                    rotate('L');
                break;
        }
    }

    public int randomNumber() {
        int max = 10;
        int min = 1;

        return (int) (Math.random() * (max - min + 1) + min);
    }
    //----------------------------------------------Miner Smart 2---------------------------------------------------------

    public String searchTwo(int x, int y, String direction) {//memory
        tempCtr++;
        String right = "";
        String left = "";
        String up = "";
        String down = "";
        boolean scan = false;
        String status = "false";
        System.out.println("Front - " + game.getMiner().getFront());
        game.printMemory();

        if (game.isEdge(x, y))
            return "false";

        System.out.println("MEMORY SPACE TYPE - " + game.getSpaceMemory(x, y));

        if (game.getSpaceMemory(x, y) == 1 || game.getSpaceMemory(x, y) == 6) {//UNEXPLORED PATH
            rotateTill(direction);
            int temp = scan();
            scan = true;
        }

        if (game.getSpaceMemory(x, y) == 8) {//GOLD ROUTE FOUND
            boolean found = true;

           temp = new Timeline(new KeyFrame(Duration.millis(300), event -> {
                System.out.println("Space Type - " + game.getSpaceType(game.getMinerX() + 1, game.getMinerY()));
                if(!game.isEdge(game.getMinerX() + 1, game.getMinerY()) && game.getSpaceType(game.getMinerX() + 1, game.getMinerY()) == 4) {
                   temp.stop();
                }
                System.out.println("Space Type - " + game.getSpaceType(game.getMinerX() - 1, game.getMinerY()));
                if(!game.isEdge(game.getMinerX() - 1, game.getMinerY()) && game.getSpaceType(game.getMinerX() - 1, game.getMinerY()) == 4) {
                   temp.stop();
                }
                System.out.println("Space Type - " + game.getSpaceType(game.getMinerX(), game.getMinerY() + 1));
                if(!game.isEdge(game.getMinerX(), game.getMinerY() + 1) && game.getSpaceType(game.getMinerX(), game.getMinerY() + 1) == 4) {
                    System.out.println("STOP");
                   temp.stop();
                }
                System.out.println("Space Type - " + game.getSpaceType(game.getMinerX(), game.getMinerY() - 1));
                if(!game.isEdge(game.getMinerX(), game.getMinerY() - 1) && game.getSpaceType(game.getMinerX(), game.getMinerY() - 1) == 4) {
                   temp.stop();
                }

                move();
            }));
            temp.setCycleCount(Timeline.INDEFINITE);
            temp.play();

            /*
            while(game.getSpaceType(game.getMinerX(), game.getMinerY()) != 4) {
                if(!game.isEdge(game.getMinerX() + 1, game.getMinerY()) && game.getSpaceType(game.getMinerX() + 1, game.getMinerY()) == 4)
                    return "true";
                if(!game.isEdge(game.getMinerX() - 1, game.getMinerY()) && game.getSpaceType(game.getMinerX() - 1, game.getMinerY()) == 4)
                    return "true";
                if(!game.isEdge(game.getMinerX(), game.getMinerY() + 1) && game.getSpaceType(game.getMinerX(), game.getMinerY() + 1) == 4)
                    return "true";
                if(!game.isEdge(game.getMinerX(), game.getMinerY() - 1) && game.getSpaceType(game.getMinerX(), game.getMinerY() - 1) == 4)
                    return "true";

                System.out.println("BOARD");
                game.printBoard();
                System.out.println("MEMORY");
                game.printMemory();

                move();
            }*/

            if(game.getSpaceType(game.getMinerX(), game.getMinerY()) == 4){
                return "true";
            }
        }

        if (game.getSpaceMemory(x, y) == 5) {//EXPLORED PATH
            return "false";
        }

        if(game.getSpaceType(x, y) == 2 && game.getSpaceMemory(x, y) == 6){//PIT DISCOVERED
            game.updateMemory(x, y, 2);
            return "false";
        }

        if (game.getSpaceMemory(x, y) == 2 && tempCtr != 1) //PIT
            return "false";
        else if (game.getSpaceMemory(x, y) == 2 && tempCtr == 1) {
            rotate('R');
            x++;
            y--;
        }

        System.out.println("BOARD");
        game.printBoard();
        System.out.println("MEMORY");
        game.printMemory();

        // DON'T EDIT ANYTHING BELOW //
        if (!scan && direction != "NULL") {
            rotateTill(direction);
        }

        move();
        System.out.println("MOVED " + direction);
        status = "moved";

        //UP
        System.out.println("Up");
        up = searchTwo(x - 1, y, "Up");
        if (up == "true")
            return "true";
        else if (up == "moved") {
            System.out.println("Move Back! - Down");
            rotateTill("Down");
            move();
        }
        //RIGHT
        System.out.println("Right");
        right = searchTwo(x, y + 1, "Right");
        if (right == "true")
            return "true";
        else if (right == "moved") {
            System.out.println("Move Back! - Left");
            rotateTill("Left");
            move();
        }
        //DOWN
        System.out.println("Down");
        down = searchTwo(x + 1, y, "Down");
        if (down == "true")
            return "true";
        else if (down == "moved") {
            System.out.println("Move Back! - Up");
            rotateTill("Up");
            move();
        }
        //LEFT
        System.out.println("Left");
        left = searchTwo(x, y - 1, "Left");
        if (left == "true")
            return "true";
        else if (left == "moved") {
            System.out.println("Move Back! - Right");
            rotateTill("Right");
            move();
        }

        boolean found = right == "true" || left == "true" || down == "true" || up == "true";

        if (found) {
            System.out.println("GOLD FOUND");
            return "true";
        } else {
            game.updateMemory(x, y, 2);
            System.out.println("DEAD END");
        }

        return status;
    }

}
