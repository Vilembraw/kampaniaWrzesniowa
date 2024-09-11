package Server;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class ServerController {
    @FXML
    public Slider slider;

    @FXML
    Label label;

    public static int value = 1;

    public static int getValue(){
        return value;
    }

    @FXML
    public void updateLabel(){
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int intValue = newValue.intValue();
            if(intValue % 2 == 1){
                label.setText(String.valueOf(intValue));
                value = intValue;
            }

        });
    }

}
