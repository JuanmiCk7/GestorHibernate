package utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateConnectionUtil {
	
	public static SessionFactory sf;
	
	public static Session createSession() {
		
		if(sf==null) {
			StandardServiceRegistry sr = new StandardServiceRegistryBuilder()
	  				.configure()
	  				.build();
			sf = new MetadataSources(sr).buildMetadata().buildSessionFactory();
		}
		
		Session session = sf.openSession();
		
		return session;
	}

}
