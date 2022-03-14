package dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.Session;


import models.Usuario;
import utils.HibernateConnectionUtil;


/**
 * Esta clase contiene todos los métodos estáticos necesarios para la inserción, modificación, lectura y borrado
 * de usuarios en la base de datos.
 * 
 * @author Juanmi
 *
 */
public class UsuarioDao {
	
	public static String loggedUser;
	
	public static String getLoggedUser() {
		return loggedUser;
	}
	
	public static void setLoggedUser(String username) {
		loggedUser = username;
	}
	
	
	/**
     * Comprueba si las credenciales proporcionadas por el usuario concuerdan con algún registro en
     * la base de datos.
     * 
     * @param username -- Nombre de usuario
     * @param password -- Contraseña
     * @param user -- Objeto usuario que va a ser comparado
     * @return exist -- Indica si el usuario existe en la base de datos
     */
	public static boolean checkUserCredentials(String username,String password, Usuario user) {
    	boolean exist = false;
    	
    	try {
    		Usuario userFD = (Usuario) HibernateConnectionUtil.createSession().createQuery("from Usuario u where u.userName = :username and u.userPassword = :password")
    				.setParameter("username", username)
    				.setParameter("password", password)
    				.getSingleResult();
    		
    		if(userFD.getUserName().equals(user.getUserName()) && userFD.getUserPassword().equals(user.getUserPassword())) {
        		exist = true;
        	}
    		
    	} catch(NoResultException e) {
    		exist = false;
    	}
    	return exist;
    }
	
	/**
	 * Este método comprueba si un usuario existe en la base de datos, de ser así devuelve true.
	 * 
	 * @param username -- Nombre de usuario
	 * @param user -- Objeto usuario que va a ser comparado
	 * @return -- Si el usuario existe o no
	 */
	public static boolean userNameExist(String username, Usuario user) {
    	boolean exist = false;
    	
    	Usuario userFD = (Usuario) HibernateConnectionUtil.createSession().createQuery("from Usuario u where u.userName = :username")
		.setParameter("username", username)
		.getSingleResult();
    	
    	if(userFD.getUserName().equals(user.getUserName())) {
    		exist = true;
    	}
    	
    	return exist;
    }
	


	/**
	 * Metodo que inserta un usuario en la base de datos.
	 * 
	 * @param username -- Nombre de usuario
	 * @param password -- Password del usuario
	 * @return -- Si el usuario se ha insertado o no
	 */
	public static boolean insertUser(String username, String password) {
		boolean inserted = false;
		
		Session session = HibernateConnectionUtil.createSession();
		// Creamos el usuario
		Usuario user = new Usuario();
		user.setId(0);
		user.setUserName(username);
		user.setUserPassword(password);
		
		try {
			if (UsuarioDao.userNameExist(username, user)) {
				inserted = false;
			} else {
				session.save(user);
				session.beginTransaction().commit();
				inserted = true;
				
			}
		} catch (NoResultException e) {
			session.save(user);
			session.beginTransaction().commit();
			inserted = true;
		}
		
		session.close();
		
		return inserted;
	}
	
	/**
	 * Metodo para eliminar un usuario de la base de datos.
	 * 
	 * @param id -- ID del usuario que vamos a eliminar
	 * @return -- Si el usuario ha sido eliminado o no
	 */
	public static boolean deleteUser(int id) {
		boolean deleted = false;
		
		Session session = HibernateConnectionUtil.createSession();
		
		try {
			Usuario user = (Usuario) HibernateConnectionUtil.createSession().createQuery("from Usuario u where u.id = :id")
					.setParameter("id", id)
					.getSingleResult();
			
			session.delete(user);
			deleted = true;
			
		} catch(NoResultException e) {
			return deleted;
		}

		return deleted;
	}
	
	/**
	 * Metodo que actualiza la password del usuario en la base de datos
	 * 
	 * @param name -- Nombre del usuario
	 * @param password -- Password del usuario
	 * @return -- Si se ha cambiado la password o no
	 */
	public static boolean updatePassword(String name, String password) {
		boolean updated = false;
		Session session = HibernateConnectionUtil.createSession();
		
		try {
			session.beginTransaction();
			Query query = session.createQuery(
					"update Usuario u set u.userPassword = :password where u.userName = :name")
					.setParameter("password", password).setParameter("name", name);
			query.executeUpdate();
			updated = true;
			session.getTransaction().commit();
		} catch(NoResultException e) {
			return updated;
		}
		
		return updated;
	}
	
}
