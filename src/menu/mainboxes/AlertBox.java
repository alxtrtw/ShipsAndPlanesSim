package menu.mainboxes;

import javafx.fxml.FXMLLoader;
import javafx.stage.*;
import javafx.scene.*;
import javafx.geometry.*;

import java.io.IOException;


public class AlertBox {

    /** Displays an alert box with given title and message */
    public void display(String title, String message) throws IOException {
        Stage alertBoxStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AlertBox.fxml"));
        Parent root = loader.load();

        alertBoxStage.setTitle(title);
        AlertBoxController alertBoxController = loader.getController();
        alertBoxController.setMessageLabel(message, alertBoxStage);
        alertBoxStage.initModality(Modality.APPLICATION_MODAL);

        alertBoxStage.setScene(new Scene(root, 350, 175));
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        alertBoxStage.setX((screenBounds.getWidth() - 350) / 2);
        alertBoxStage.setY((screenBounds.getHeight() - 175) / 2);
        alertBoxStage.setResizable(false);
        alertBoxStage.show();

    }

}
