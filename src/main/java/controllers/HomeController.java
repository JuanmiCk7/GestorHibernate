package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import dao.ActividadesDao;
import dao.UsuarioDao;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Actividades;

public class HomeController implements Initializable {
	
	@FXML
	private Text usernameText;
	
	/**
	 * Tabla en la que se muestra todos los registros de la base de datos
	 */
	@FXML
	private TableView<Actividades> tableActividades;

	@FXML
	private TableColumn<Actividades, Integer> colid;

	@FXML
	private TableColumn<Actividades, String> colname;

	@FXML
	private TableColumn<Actividades, String> coldescription;

	@FXML
	private TableColumn<Actividades, Date> coldate;

	/**
	 * Boton que nos lleva a nuestro perfil
	 */
	@FXML
	private Button btnPerfil;

	/**
	 * Boton que nos lleva a las actividades
	 */
	@FXML
	private Button btnActividades;

	@FXML
	private Button btnForo;

	@FXML
	private Button btnCalendario;

	/**
	 * Boton que nos lleva a la ventana de incluir la actividad
	 */
	@FXML
	private Button btnAddActividad;

	@FXML
	private Button btnRedes;

	@FXML
	private Button btnContacto;

	@FXML
	private Button btnActualizar;

	@FXML
	private Button btnSalir;

	Actividades actividad;

	/**
	 * Constructor del controlador de la clase
	 */
	public HomeController() {
		
	}
	
	/**
	 * Inicializa la el controlador, mostrando las actividades de la base de datos
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		showActividades();
		usernameText.setText(UsuarioDao.getLoggedUser());
	}
	
	/**
	 * Metodo utilizado para mostrar la lista de actividades en la tabla.
	 */
	public void showActividades() {
		ObservableList<Actividades> observableListActividades = ActividadesDao.getAllActividades();
		
		colid.setCellValueFactory(new PropertyValueFactory<Actividades, Integer>("id"));
		colname.setCellValueFactory(new PropertyValueFactory<Actividades, String>("name"));
		coldescription.setCellValueFactory(new PropertyValueFactory<Actividades, String>("description"));
		coldate.setCellValueFactory(new PropertyValueFactory<Actividades, Date>("date"));
		
		tableActividades.setItems(observableListActividades);
	}
	
	/**
	 * Metodo que nos lleva a la venta de cambiar la password
	 * 
	 * @param event
	 */
	@FXML
	private void onClickBtnPerfil(MouseEvent event) {
		try {
			Node node = (Node) event.getSource();
			Stage stage = (Stage) node.getScene().getWindow();
			stage.close();
			Scene scene;
			scene = new Scene((Parent) FXMLLoader.load(getClass().getResource("/fxml/UserSettings.fxml")));
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que nos lleva a la venta de actividades
	 * 
	 * @param event
	 */
	@FXML
	private void onClickBtnActividades(MouseEvent event) {
		try {
			Node node = (Node) event.getSource();
			Stage stage = (Stage) node.getScene().getWindow();
			stage.close();
			Scene scene;
			scene = new Scene((Parent) FXMLLoader.load(getClass().getResource("/fxml/Activities.fxml")));
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Metodo que nos lleva a la venta de incluir la actividad
	 * 
	 * @param event
	 */
	@FXML
	private void onClickBtnAddActividad(MouseEvent event) {
		try {
			Node node = (Node) event.getSource();
			Stage stage = (Stage) node.getScene().getWindow();
			stage.close();
			Scene scene;
			scene = new Scene((Parent) FXMLLoader.load(getClass().getResource("/fxml/AddActivity.fxml")));
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo para cerrar sesion y volver al Login
	 * 
	 * @param event
	 */
	@FXML
	private void onClickSalir(MouseEvent event) {
		try {
			Node node = (Node) event.getSource();
			Stage stage = (Stage) node.getScene().getWindow();
			stage.close();
			Scene scene;
			scene = new Scene((Parent) FXMLLoader.load(getClass().getResource("/fxml/Login.fxml")));
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
