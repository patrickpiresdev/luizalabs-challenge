package com.ptk.luizalabschallenge;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.ptk.luizalabschallenge.dao.ProductDAO;
import com.ptk.luizalabschallenge.dao.WishlistDAO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LuizalabsChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(LuizalabsChallengeApplication.class, args);
	}

    @Bean
    public MongoDatabase getDatabase() {
        String mongodbConnectionString = "mongodb://localhost:27017";
        MongoClient mongoClient = MongoClients.create(mongodbConnectionString);
        return mongoClient.getDatabase("luizalabs");
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
