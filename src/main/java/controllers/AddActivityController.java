package controllers;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.ResourceBundle;
import dao.ActividadesDao;
import dao.UsuarioDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.AlertUtil;

public class AddActivityController implements Initializable {
	
	
	/**
	 * Nombre de usuario
	 */
	@FXML
	private Text usernameText;
	/**
	 * Elemento en el que se recoge el nombre que le vamos a dar a la actividad
	 */
	@FXML
	private TextField textNombreActividad;
	
	/**
	 * Bot�n utilizado para volver
	 */
	@FXML
	private Button btnVolver;

	/**
	 * Elemento en el que se recoge la descripci�n que le vamos a dar a la actividad
	 */
	@FXML
	private TextArea textDescripcionActividad;

	/**
	 * Bot�n con el que a�adimos una imagen (no funcional)
	 */
	@FXML
	private Button btnAddImage;

	/**
	 * Bot�n para a�adir la actividad a la base de datos
	 */
	@FXML
	private Button btnAddActividad;

	/**
	 * Elemento utilizado para seleccionar la fecha de la actividad
	 */
	@FXML
	private DatePicker datePicker;

	/**
	 * M�todo utilizado para volver a la anterior pantalla.
	 * 
	 * @param event
	 */
	@FXML
	private void onClickVolver(MouseEvent event) {
		try {
			Node node = (Node) event.getSource();
			Stage stage = (Stage) node.getScene().getWindow();
			stage.close();
			Scene scene;
			scene = new Scene((Parent) FXMLLoader.load(getClass().getResource("/fxml/OnBoard.fxml")));
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * M�todo utilizado para a�adir la actividad en la base de datos.
	 * @param event
	 */
	@FXML
	private void onClickAddActivity(MouseEvent event) {

		String activityName = textNombreActividad.getText();
		String activityDescription = textDescripcionActividad.getText();
		try {
			java.util.Date date = java.util.Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			
			if (!activityName.isEmpty() && !activityDescription.isEmpty()) {	
				ActividadesDao.insertActivity(activityName, activityDescription, sqlDate);
				Node node = (Node) event.getSource();
				Stage stage = (Stage) node.getScene().getWindow();
				AlertUtil.showAlert(AlertType.INFORMATION, "Actividad registrada", "La actividad ha sido registrada en el sistema", stage);
			} else {
				Node node = (Node) event.getSource();
				Stage stage = (Stage) node.getScene().getWindow();
				AlertUtil.showAlert(AlertType.INFORMATION, "Rellena todos los campos", "Para poder registrar la actividad debes rellenar todos los campos", stage);
			}
			
		} catch (NullPointerException e) {
			Node node = (Node) event.getSource();
			Stage stage = (Stage) node.getScene().getWindow();
			AlertUtil.showAlert(AlertType.INFORMATION, "Rellena todos los campos", "Para poder registrar la actividad debes rellenar todos los campos", stage);
		}
		

		

	}

	/**
	 * Constructor del controlador de la clase.
	 */
	public AddActivityController() {
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		usernameText.setText(UsuarioDao.getLoggedUser());
	}

}