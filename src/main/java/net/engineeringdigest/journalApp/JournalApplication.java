package net.engineeringdigest.journalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class JournalApplication {

	public static void main(String[] args) {
		SpringApplication.run(JournalApplication.class, args);
	}
	//we have enabled or informed spring in main class that transaction needs to be managed but it requires a transaction manager

	//MongoTransactionmanger-implementation class of platformtransactionmanager reuires a database which in our case will be mongodatabasefatory
	//mongodatabasefactory helps in creating connection with database.

	@Bean
	public PlatformTransactionManager falana(MongoDatabaseFactory dbFactory){
		return new MongoTransactionManager(dbFactory);
	}
}

//1411vikashchaturvedi
//87V6kCA9GxBQAJt4


