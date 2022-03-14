package utils;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

public class AlertUtil {
	
	public static Optional<ButtonType> showAlert(AlertType type, String title, String message, Stage owner) {
		Optional<ButtonType> action = null;
		Alert alert = new Alert(type);
		if (owner != null)
			alert.initOwner(owner);
		alert.setHeaderText(null);
		alert.setTitle(title);
		alert.setContentText(message);
		
		if (type == AlertType.CONFIRMATION)  {
			action = alert.showAndWait();
		} else {
			alert.showAndWait();
		}
		return action;
	}

}
