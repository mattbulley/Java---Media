package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;



public class Main extends Application {

    static Player player;
    FileChooser fileChooser;
    static Boolean closed = false;

    @Override
    public void start(final Stage primaryStage){

        MenuItem open = new MenuItem("Open");
        MenuItem close = new MenuItem("Close");
        MenuItem exit = new MenuItem("Exit");
        Menu file = new Menu("File");
        MenuBar menu = new MenuBar();

        file.getItems().add(open);
        file.getItems().add(close);
        file.getItems().add(exit);
        menu.getMenus().add(file);

        fileChooser = new FileChooser();

        open.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                player.player.pause();
                File file = fileChooser.showOpenDialog(primaryStage);
                if(file != null){
                    try {
                        player = new Player(file.toURI().toURL().toExternalForm());
                        player.setTop(menu);
                        Scene scene = new Scene(player, 720, 406, Color.BLACK);
                        primaryStage.setScene(scene);
                        closed = false;

                    } catch (MalformedURLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                player.player.pause();
                setBlank();
                closed = true;


                }

        });

        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);


            }

        });




        player = new Player("file:/Users/mattbulley/Movies/The.Simpsons.S25E21.HDTV.x264-LOL.mp4");
        player.setTop(menu);
        Scene scene = new Scene(player, 720, 406, Color.BLACK);

        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("file:/Users/mattbulley/Pictures/yin.jpg"));
        primaryStage.setTitle("Media Player");
        primaryStage.show();


    }



    public static void main(String[] args) {
        launch(args);
    }

    public static void setScene(){
        player.setPane();
    }

    public static void setBlank(){
        player.setBlank();

    }
}
