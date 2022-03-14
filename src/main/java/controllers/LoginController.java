package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import dao.UsuarioDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Usuario;
import utils.AlertUtil;

public class LoginController implements Initializable {

	/**
	 * Campo de texto en el que se introduce el nombre de usuario
	 */
	@FXML
	private TextField txtUsername;

	/**
	 * Campo de texto en el que se introduce la contrase�a
	 */
	@FXML
	private TextField txtPassword;

	/**
	 * Bot�n para probar las credenciales y acceder a la aplicaci�n
	 */
	@FXML
	private Button btnSignin;

	/**
	 * Bot�n que nos lleva a la pantalla de registro.
	 */
	@FXML
	private Button btnSignup;

	/**
	 * Método que comprueba si las credenciales del usuario son correctas al pulsar el botón de Login
	 * 
	 * @param event -- Evento de click
	 */
	@FXML
	public void onClickLogin(MouseEvent event) {

		if (logIn()) {
			try {
				Node node = (Node) event.getSource();
				Stage stage = (Stage) node.getScene().getWindow();
				stage.close();
				Scene scene = new Scene((Parent) FXMLLoader.load(getClass().getResource("/fxml/OnBoard.fxml")));
				stage.setScene(scene);
				stage.show();

			} catch (IOException ex) {
				System.err.println(ex.getMessage());
			}
		} else {
			Node node = (Node) event.getSource();
			Stage stage = (Stage) node.getScene().getWindow();
			AlertUtil.showAlert(AlertType.ERROR, "Credenciales incorrectas",
					"Las credenciales proporcionadas son incorrectas", stage);
		}

	}
	
	/**
	 * Método que te lleva a la pantalla de registro al pulsar el botón de Registro 
	 * 
	 * @param event -- Evento de click
	 */

	public void onClickRegister(MouseEvent event) {
		try {
			Node node = (Node) event.getSource();
			Stage stage = (Stage) node.getScene().getWindow();
			stage.close();
			Scene scene;
			scene = new Scene((Parent) FXMLLoader.load(getClass().getResource("/fxml/Register.fxml")));
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}

	/**
	 * Constructor del controlador de la clase.
	 */
	public LoginController() {

	}

	/**
	 * M�todo que comprueba si hay un usuario en la base de datos que coincida con
	 * las credenciales introducidas
	 * 
	 * @return -- Devuelve un String indicando si la operaci�n a sido exitosa o no
	 */
	private boolean logIn() {
		boolean error = false;
		String username = txtUsername.getText();
		String password = txtPassword.getText();
		if (username.isEmpty() || password.isEmpty()) {
			error = true;
		} else {
			Usuario user = new Usuario();
			user.setUserName(username);
			user.setUserPassword(password);
			if (UsuarioDao.checkUserCredentials(username, password, user)) {
				error = true;
				UsuarioDao.setLoggedUser(username);
			} else {
				error = false;
			}
		}

		return error;
	}

}
