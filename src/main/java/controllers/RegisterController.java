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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.AlertUtil;

public class RegisterController implements Initializable {

	/**
	 * Campo de texto que recoger� el nombre de usuario
	 */
	@FXML
	private TextField txtUsername;

	/**
	 * Campo de texto que recoger� la contrase�a
	 */
	@FXML
	private TextField txtPassword;

	/**
	 * Campo de texto que recoger� la contrase�a repetida
	 */
	@FXML
	private TextField txtPasswordRepeat;

	/**
	 * Bot�n con el que registramos al usuario
	 */
	@FXML
	private Button btnSignup;

	/**
	 * Bot�n para volver a la pantalla de login
	 */
	@FXML
	private Button btnBack;

	/**
	 * Almacena el nombre de usuario
	 */
	String username;

	/**
	 * Almacena la contrase�a
	 */
	String password;

	/**
	 * Almacena la contrase�a repetida
	 */
	String repeatPassword;

	StandardServiceRegistry sr;

	SessionFactory sf;

	/**
	 * Comprueba si hay un clic en un bot�n y elige que acci�n se debe realizar En
	 * caso de pulsar el bot�n de registro, comprobar� 3 cosas: Si el usuario existe
	 * en la DB, si las contrase�as son iguales y si los campos est�n vac�os. En
	 * cualquiera de los 3 casos devolver� un error. En caso de ser correcto el
	 * usuario se registrar� en la DB
	 * 
	 * Si se pulsa el bot�n de volver, nos llevar� a la ventana de login
	 * 
	 * @param event
	 */
	@FXML
	public void onClickBack(MouseEvent event) {

		try {

			Node node = (Node) event.getSource();
			Stage stage = (Stage) node.getScene().getWindow();
			stage.close();
			Scene scene = new Scene((Parent) FXMLLoader.load(getClass().getResource("/fxml/Login.fxml")));
			stage.setScene(scene);
			stage.show();

		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}

	}

	public void onClickRegister(MouseEvent event) {
		System.out.println("Registro pulsado!!");
		username = txtUsername.getText();
		password = txtPassword.getText();
		repeatPassword = txtPasswordRepeat.getText();

		if (!username.isEmpty() && !password.isEmpty() && !repeatPassword.isEmpty()) {
			System.out.println("Primera contrase�a: " + password);
			System.out.println("Segunda contrase�a: " + repeatPassword);
			if (password.equals(repeatPassword)) {

				if (UsuarioDao.insertUser(username, password)) {
					Node node = (Node) event.getSource();
					Stage stage = (Stage) node.getScene().getWindow();
					AlertUtil.showAlert(AlertType.INFORMATION, "El usuario se ha introducido con éxito",
							"El usuario se ha añadido a la base de datos", stage);
				} else {
					Node node = (Node) event.getSource();
					Stage stage = (Stage) node.getScene().getWindow();
					AlertUtil.showAlert(AlertType.ERROR, "El usuario ya existe",
							"El usuario ya existe en la base de datos", stage);
				}

			} else {
				Node node = (Node) event.getSource();
				Stage stage = (Stage) node.getScene().getWindow();
				AlertUtil.showAlert(AlertType.ERROR, "Contrase�a incorrecta", "Ambas contrase�as deben coincidir",
						stage);
			}
		} else {
			Node node = (Node) event.getSource();
			Stage stage = (Stage) node.getScene().getWindow();
			AlertUtil.showAlert(AlertType.ERROR, "Rellena todos los campos",
					"Para poder registrarte debes rellenar todos los campos", stage);
		}
	}

	/**
	 * Constructor del controlador de la clase
	 */
	public RegisterController() {

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}

}
