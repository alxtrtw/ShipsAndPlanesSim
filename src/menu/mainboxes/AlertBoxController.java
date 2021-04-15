package menu.mainboxes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AlertBoxController {

    @FXML
    private Label messageLabel;
    @FXML
    private Button okButton;

    /** Sets the message to be displayed on the box and sets up the "OK" button */
    public void setMessageLabel(String message, Stage alertBoxStage){
        messageLabel.setText(message);
        messageLabel.setText(message);
        okButton.setOnAction(e -> {
            e.consume();
            alertBoxStage.close();
        });
    }

}
