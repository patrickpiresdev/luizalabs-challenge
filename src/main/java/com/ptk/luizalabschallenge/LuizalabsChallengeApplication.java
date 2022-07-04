package com.ptk.luizalabschallenge;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.ptk.luizalabschallenge.dao.ProductDAO;
import com.ptk.luizalabschallenge.dao.WishlistDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class LuizalabsChallengeApplication {
    @Value("${mongodb.connection-string}")
    private String mongodbConnectionString;

    @Value("${mongodb.database}")
    private String mongodbDatabase;

	public static void main(String[] args) {
		SpringApplication.run(LuizalabsChallengeApplication.class, args);
	}

    @Bean
    public MongoDatabase getDatabase() {
        MongoClient mongoClient = MongoClients.create(mongodbConnectionString);
        return mongoClient.getDatabase(mongodbDatabase);
    }

    @Bean
    public WishlistDAO createWishlistDao(MongoDatabase database) {
        return new WishlistDAO(database);
    }

	@Bean
    public ProductDAO createProductDao(MongoDatabase database) {
        return new ProductDAO(database);
    }
}
