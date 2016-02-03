package UI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by PHIL on 11.12.2015.
 */
public class Preloader extends javafx.application.Preloader {
    private Stage preloaderStage;
    @FXML
    ProgressBar progressBar;
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.preloaderStage = primaryStage;

        VBox loading = new VBox(20);
        loading.setMaxWidth(Region.USE_PREF_SIZE);
        loading.setMaxHeight(Region.USE_PREF_SIZE);
        ProgressBar pb = new  ProgressBar();
        loading.getChildren().add(pb);
        loading.getChildren().add(new Label("Please wait..."));

        BorderPane root = new BorderPane(loading);
        Scene scene = new Scene(root);

        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setScene(scene);
        primaryStage.show();
        for(int i=0;i<100;i++){
            pb.progressProperty().setValue(i);
        }
        if(pb.getProgress()==100){

        }
    }
}
