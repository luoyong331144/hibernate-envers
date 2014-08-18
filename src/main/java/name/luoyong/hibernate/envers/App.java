package name.luoyong.hibernate.envers;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;

import name.luoyong.hibernate.basic.entity.User;

public class App {
	
	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
	
    public static void main( String[] args )  {
    	
    	persist();
    	update();
    	reader();
    	
    	entityManagerFactory.close();
    }
    
    private static void persist() {
    	EntityManager entityManager = entityManagerFactory.createEntityManager();
    	entityManager.getTransaction().begin();
    	
    	User ly = new User();
    	ly.setUsername("shulei");
    	ly.setPassword("asdfasf");
    	
    	entityManager.persist(ly);
    	
    	entityManager.getTransaction().commit();
    	entityManager.close();
    }
    
    
    private static void update() {
    	EntityManager entityManager = entityManagerFactory.createEntityManager();
    	entityManager.getTransaction().begin();
    	
    	User user = entityManager.find(User.class, 1L);
    	user.setPassword("xc");
    	
    	entityManager.getTransaction().commit();
    	entityManager.close();
    }
    
    private static void reader() {
    	EntityManager entityManager = entityManagerFactory.createEntityManager();
    	entityManager.getTransaction().begin();
    	
    	AuditReader reader = AuditReaderFactory.get(entityManager);
    	
    	User user1 = reader.find(User.class, 1L, 1);
    	User user2 = reader.find(User.class, 1L, 2);
    	
    	System.out.println(user1.getUsername());
    	System.out.println(user1.getPassword());
    	
    	System.out.println(user2.getUsername());
    	System.out.println(user2.getPassword());
    	
    	entityManager.getTransaction().commit();
    	entityManager.close();
    }

}
