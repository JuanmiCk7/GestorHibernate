package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;

import dao.UsuarioDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Usuario;
import utils.AlertUtil;

public class UserSettingsController implements Initializable {

	/**
	 * Campo para introducir la password actual.
	 */
	@FXML
	private TextField actualPassword;
	
	/**
	 * Campo para introducir la nueva password.
	 */
	@FXML
	private TextField newPassword;
	
	
	/**
	 * Campo para introducir la nueva password de nuevo.
	 */
	@FXML
	private TextField newPasswordRepeat;
	
	
	/**
	 * Campo de texto para mostrar el nombre de usuario.
	 */
	@FXML
	private Text usernameText;
	
	/**
	 * Campo que muestra el nombre de usuario en el menu.
	 */
	@FXML
	private Text usernameTextMenu;
	
	/**
	 * Almacena el nombre de usuario
	 */
	String username;
	
	/**
	 * Almacena el nombre de usuario
	 */
	String passwordActual;

	/**
	 * Almacena la password
	 */
	String passwordNew;

	/**
	 * Almacena la password repetida
	 */
	String repeatPasswordNew;
	
	StandardServiceRegistry sr;

	SessionFactory sf;
	
	/*
	 * Metodo para volver al menu principal
	 */
	@FXML
	private void onClickVolver(MouseEvent event) {
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
	}
	
	/**
	 * Metodo para cambiar la password del usuario con la sesion iniciada
	 * 
	 * @param event -- Evento de clic 
	 */
	@FXML
	private void onClickChangePassword(MouseEvent event) {
		String passwordActual = actualPassword.getText();
		String passwordNew = newPassword.getText();
		String repeatPasswordNew = newPasswordRepeat.getText();
		
		if (!passwordActual.isEmpty() && !passwordNew.isEmpty() && !repeatPasswordNew.isEmpty()) {
			if (passwordNew.equals(repeatPasswordNew)) {
				Usuario user = new Usuario();
				user.setUserName(UsuarioDao.getLoggedUser());
				user.setUserPassword(passwordActual);
				if(UsuarioDao.checkUserCredentials(UsuarioDao.getLoggedUser(), passwordActual, user)) {
					UsuarioDao.updatePassword(UsuarioDao.getLoggedUser(), repeatPasswordNew);
				}
			} else {
				Node node = (Node) event.getSource();
				Stage stage = (Stage) node.getScene().getWindow();
				AlertUtil.showAlert(AlertType.ERROR, "Modificar", "Las contraseñas no coinciden.", stage);
			}
		} else {
			Node node = (Node) event.getSource();
			Stage stage = (Stage) node.getScene().getWindow();
			AlertUtil.showAlert(AlertType.ERROR, "Modificar", "Para poder registrar la actividad debes rellenar todos los campos", stage);
		}
	}
	
	/**
	 * Contructor del controlador
	 */
	public UserSettingsController() {
		
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		usernameTextMenu.setText(UsuarioDao.getLoggedUser());
	}
	
}
