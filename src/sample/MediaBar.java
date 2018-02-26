package sample;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
public class MediaBar extends HBox {

    Slider time = new Slider();
    Slider vol = new Slider();

    Button playButton = new Button(">");

    Label volume = new Label("Volume: ");

    Media media;
    MediaPlayer player;
    MediaPlayer.Status status;
    MediaView view;
    Pane mpane;
    MediaBar bar;
    Boolean first = true;

    public MediaBar(MediaPlayer play){

        player = play;

        setAlignment(Pos.CENTER);
        setPadding(new Insets(5,10,5,10));

        vol.setPrefWidth(70);
        vol.setMinWidth(30);
        vol.setValue(100);

        HBox.setHgrow(time, Priority.ALWAYS);

        playButton.setPrefWidth(30);

        getChildren().add(playButton);
        getChildren().add(time);
        getChildren().add(volume);
        getChildren().add(vol);

        playButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent e) {
                if(Main.closed == true)
                    return;
                if(first) {

                    status = MediaPlayer.Status.PAUSED;
                    Main.setScene();
                } else {
                    status = player.getStatus();
                }
                first = false;
                if(status == MediaPlayer.Status.PLAYING){
                    if(player.getCurrentTime().greaterThanOrEqualTo(player.getTotalDuration())){
                        player.seek(player.getStartTime());
                        player.play();
                    }
                    else {
                        player.pause();
                        playButton.setText(">");
                    }
                }

                if(status == MediaPlayer.Status.PAUSED || status == MediaPlayer.Status.HALTED || status == MediaPlayer.Status.STOPPED ){
                    player.play();
                    playButton.setText("||");
                }
            }
        });

        player.currentTimeProperty().addListener(new InvalidationListener() {

            public void invalidated(Observable observable) {
                updateValues();
            }
        });

        time.valueProperty().addListener(new InvalidationListener() {

            public void invalidated(Observable observable) {
                if(time.isPressed()){
                    player.seek(player.getMedia().getDuration().multiply(time.getValue()/100));
                }
            }
        });

        vol.valueProperty().addListener(new InvalidationListener() {

            public void invalidated(Observable observable) {
                if(vol.isPressed()){
                    player.setVolume(vol.getValue()/100);
                }
            }
        });


    }

    protected void updateValues(){
        Platform.runLater(new Runnable(){
            public void run(){
                time.setValue(player.getCurrentTime().toMillis()/player.getTotalDuration().toMillis()*100);
            }
        });


    }
}
