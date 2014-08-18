
#### pom.xml
``` xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>name.luoyong.hibernate</groupId>
	<artifactId>hibernate-envers</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>hibernate-envers</name>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<junit.version>4.11</junit.version>
		<hibernate.version>4.3.6.Final</hibernate.version>
		<mysql.version>5.1.6</mysql.version>
	</properties>

	<dependencies>

		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<version>${junit.version}</version>
			<scope>test</scope>
		</dependency>

		<dependency>
			<groupId>org.hibernate</groupId>
			<artifactId>hibernate-entitymanager</artifactId>
			<version>${hibernate.version}</version>
		</dependency>

		<dependency>
			<groupId>org.hibernate</groupId>
			<artifactId>hibernate-envers</artifactId>
			<version>${hibernate.version}</version>
		</dependency>

		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<version>${mysql.version}</version>
		</dependency>

	</dependencies>

</project>
```

#### persistence.xml
``` xml
<persistence xmlns="http://java.sun.com/xml/ns/persistence"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd"
             version="2.0">

    <persistence-unit name="org.hibernate.tutorial.jpa">

        <properties>
            <property name="javax.persistence.jdbc.driver" value="com.mysql.jdbc.Driver" />
            <property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/hibernate" />
            <property name="javax.persistence.jdbc.user" value="root" />
            <property name="javax.persistence.jdbc.password" value="****" />
            
            <property name="hibernate.show_sql" value="true" />
            <property name="hibernate.format_sql" value="true" />
            <property name="hibernate.hbm2ddl.auto" value="update" />
        </properties>

    </persistence-unit>

</persistence>
```

#### java test
``` java
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
```