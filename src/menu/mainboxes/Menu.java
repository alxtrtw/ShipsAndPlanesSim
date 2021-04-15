package menu.mainboxes;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class Menu extends Application{


    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Control Panel");
        primaryStage.setScene(new Scene(root, 275, 125));
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((screenBounds.getWidth() - 275) / 2 - 575);
        primaryStage.setY((screenBounds.getHeight() - 125) / 2 - 275);
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            Platform.exit();
        });

        primaryStage.show();

    }
}
