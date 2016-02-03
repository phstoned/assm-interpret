package UI;/**
 * Created by PHIL on 21.10.2015.
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Launcher extends Application {
private final String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH:mm:ss").format(Calendar.getInstance().getTime());
    public Label lbl;
    @FXML
ProgressBar progressBar;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException, SQLException {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        Scene scene = new Scene(root,640,480);
        System.out.println(timeStamp);

        String css = Launcher.class.getResource("mac_os.css").toExternalForm();
        try {
            scene.getStylesheets().add(css);
        }
        catch (NullPointerException e){
            System.out.println("cant make it");
        }

            primaryStage.setTitle("Курсовой проект");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();

    }
}
