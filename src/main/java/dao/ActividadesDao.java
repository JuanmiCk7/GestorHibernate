package dao;

import java.sql.Date;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Actividades;
import utils.HibernateConnectionUtil;

/**
 * Esta clase contiene todos los métodos estáticos necesarios para la inserción,
 * modificación, lectura y borrado de actividades en la base de datos.
 * 
 * @author Juanmi
 *
 */
public class ActividadesDao {

	/**
	 * Actividad seleccionada para modificar
	 */
	public static int selectedActivity;

	public static int getSelectedActivity() {
		return selectedActivity;
	}

	public static void setSelectedActivity(int select) {
		selectedActivity = select;
	}

	/**
	 * Este método devuelve una lista observable con todas las actividades de la
	 * base de datos.
	 *
	 * @return -- ObservableList<Actividades> con todas las actividades de la base
	 *         de datos.
	 */
	@SuppressWarnings("unchecked")
	public static ObservableList<Actividades> getAllActividades() {

		List<Actividades> lista = HibernateConnectionUtil.createSession().createQuery("from Actividades")
				.getResultList();
		ObservableList<Actividades> listActividades = FXCollections.observableArrayList(lista);

		return listActividades;
	}

	/**
	 * Este método introduce una actividad en la base de datos.
	 *
	 *
	 */

	public static void insertActivity(String name, String description, Date date) {

		Session session = HibernateConnectionUtil.createSession();
		Actividades activity = new Actividades();
		activity.setId(0);
		activity.setName(name);
		activity.setDescription(description);
		activity.setDate(date);
		session.save(activity);
		session.beginTransaction().commit();
		session.close();

	}


	@SuppressWarnings("unchecked")
	public static ObservableList<Actividades> searchActivities(String name) {

		List<Actividades> listActividades = HibernateConnectionUtil.createSession()
				.createQuery("from Actividades a where a.name = :name").setParameter("name", name).getResultList();

		ObservableList<Actividades> list = FXCollections.observableArrayList(listActividades);

		return list;
	}

	public static Actividades searchActivitiesWithId(int id) {
		Actividades actividad;
		try {
			actividad = (Actividades) HibernateConnectionUtil.createSession()
					.createQuery("from Actividades a where a.id = :id").setParameter("id", id).getSingleResult();
		} catch (NoResultException e) {
			actividad = null;
		}

		return actividad;
	}
	
	public static boolean deleteActivity(int id) {
		boolean deleted = false;

		Session session = HibernateConnectionUtil.createSession();

		try {
			session.beginTransaction();
			Query q = session.createQuery("delete Actividades a where a.id = :id").setParameter("id", id);
			q.executeUpdate();
			session.getTransaction().commit();
			deleted = true;

		} catch (NoResultException e) {
			return deleted;
		}

		return deleted;
	}

	public static boolean updateActivity(int id, String name, String description, Date date) {
		
		boolean updated = false;
		Session session = HibernateConnectionUtil.createSession();
		
		try {
			session.beginTransaction();
			Query query = session.createQuery(
					"update Actividades a set a.name = :name, a.description = :description, a.date = :date where a.id = :id")
					.setParameter("id", id).setParameter("name", name).setParameter("description", description)
					.setParameter("date", date);
			query.executeUpdate();
			updated = true;
			session.getTransaction().commit();
		} catch(NoResultException e) {
			return updated;
		}
		
		return updated;
	}
	
	

}
