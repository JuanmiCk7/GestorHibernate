package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import dao.ActividadesDao;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Actividades;
import utils.AlertUtil;

public class ActivitiesController implements Initializable {

	/**
	 * Boton utilizado para eliminar un registro de la base de datos
	 */
	@FXML
	private Button btnEliminar;

	/**
	 * Campo de texto desde el que recogemos el ID del campo que vamos a borrar
	 */
	@FXML
	private TextField textEliminar;

	/**
	 * Campo de texto desde el que recogemos el nombre del campo que vamos a buscar
	 */
	@FXML
	private TextField textBuscar;

	/**
	 * Boton utilizado para volver a la anterior ventana.
	 */
	@FXML
	private Button btnVolver;

	/**
	 * Boton utilizado para buscar
	 */
	@FXML
	private Button btnBuscar;

	/**
	 * Tabla en la que se mostrar�n las actividades encontradas
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
	 * Contiene el indice del elemento a eliminar
	 */
	int elementoEliminar = 0;

	/**
	 * Contiene el nombre del elemnto a buscar
	 */
	private String searchName;

	/**
	 * Actividad actual
	 */
	Actividades actividad;

	/**
	 * Constructor del controlador de la clase
	 */
	public ActivitiesController() {

	}

	/**
	 * Metodo utilizado para inicializar el controlador
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		showActividades();
	}

	/**
	 * Metodo utilizado para eliminar un registro de la base de datos en funci�n a
	 * un ID que introducimos por un TextField.
	 * 
	 * @param event
	 */
	@FXML
	private void onClickEliminar(MouseEvent event) {

		try {
			elementoEliminar = tableActividades.getSelectionModel().getSelectedItem().getId();
			System.out.println("Elemento a eliminar: " + elementoEliminar);
			ActividadesDao.deleteActivity(elementoEliminar);
			showActividades();
			Node node = (Node) event.getSource();
			Stage stage = (Stage) node.getScene().getWindow();
			AlertUtil.showAlert(AlertType.INFORMATION, "Eliminar", "Actividad eliminada con exito", stage);
		} catch(NullPointerException e) {
			Node node = (Node) event.getSource();
			Stage stage = (Stage) node.getScene().getWindow();
			AlertUtil.showAlert(AlertType.INFORMATION, "Eliminar", "Para eliminar una actividad debes seleccionarla en la tabla", stage);
		}
	}

	/**
	 * Metodo utilizado para buscar elementos en la base de datos que coincidan con
	 * un criterio de b�squeda. Este m�todo comprueba que el TextField de b�squeda
	 * no esta vac�o y llama al m�todo showActividades.
	 * 
	 * @param event
	 */
	@FXML
	private void onClickBuscar(MouseEvent event) {
		searchName = textBuscar.getText();
		if (!searchName.isEmpty()) {
			searchActividades(searchName);
		} else {
			Node node = (Node) event.getSource();
			Stage stage = (Stage) node.getScene().getWindow();
			AlertUtil.showAlert(AlertType.INFORMATION, "Rellena los campos", "Escribe algo en el campo de busqueda",
					stage);
		}
	}

	/**
	 * Metodo utilizado para volver a la pantalla anterior.
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
			e.printStackTrace();
		}
	}
	
	@FXML
	private void onClickVer(MouseEvent event) {
		
	}
	
	@FXML
	private void onClickModificar(MouseEvent event) {
		try {
			ActividadesDao.setSelectedActivity(tableActividades.getSelectionModel().getSelectedItem().getId());
			Node node = (Node) event.getSource();
			Stage stage = (Stage) node.getScene().getWindow();
			stage.close();
			Scene scene;
			scene = new Scene((Parent) FXMLLoader.load(getClass().getResource("/fxml/ModifyActivity.fxml")));
			stage.setScene(scene);
			stage.show();
		} catch(NullPointerException e) {
			Node node = (Node) event.getSource();
			Stage stage = (Stage) node.getScene().getWindow();
			AlertUtil.showAlert(AlertType.INFORMATION, "Modificar", "Para modificar una actividad debes seleccionarla en la tabla", stage);
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Metodo que se encarga de llevarnos a la ventana en la que podemos incluir una actividad.
	 * 
	 * @param event -- Evento de raton
	 */
	@FXML
	private void onClickAdd(MouseEvent event) {
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
	 * Metodo que se encarga de mostrar las actividades en la tabla.
	 * 
	 * @param searchName -- Cadena de texto que utilizaremos para filtrar en la
	 *                   consulta
	 */
	public void showActividades() {
		ObservableList<Actividades> observableListActividades = ActividadesDao.getAllActividades();

		colid.setCellValueFactory(new PropertyValueFactory<Actividades, Integer>("id"));
		colname.setCellValueFactory(new PropertyValueFactory<Actividades, String>("name"));
		coldescription.setCellValueFactory(new PropertyValueFactory<Actividades, String>("description"));
		coldate.setCellValueFactory(new PropertyValueFactory<Actividades, Date>("date"));

		tableActividades.setItems(observableListActividades);
	}

	public void searchActividades(String name) {
		ObservableList<Actividades> observableListActividades = ActividadesDao.searchActivities(name);

		colid.setCellValueFactory(new PropertyValueFactory<Actividades, Integer>("id"));
		colname.setCellValueFactory(new PropertyValueFactory<Actividades, String>("name"));
		coldescription.setCellValueFactory(new PropertyValueFactory<Actividades, String>("description"));
		coldate.setCellValueFactory(new PropertyValueFactory<Actividades, Date>("date"));

		tableActividades.setItems(observableListActividades);
	}

}
