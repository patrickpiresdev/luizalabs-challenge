package com.ptk.luizalabschallenge;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.ptk.luizalabschallenge.dao.WishlistDAO;
import com.ptk.luizalabschallenge.model.Client;
import com.ptk.luizalabschallenge.model.Product;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LuizalabsChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(LuizalabsChallengeApplication.class, args);
	}

	@Bean
	public Client createTmpData() {
        Client client = new Client("John Tmp Smith");
		client.addToWishlist(new Product("Tmp 1", "Temporary product 1"));
		client.addToWishlist(new Product("Tmp 2", "Temporary product 2"));
		client.addToWishlist(new Product("Tmp 3", "Temporary product 3"));
		return client;
	}

    @Bean
    public WishlistDAO createWishlistDao() {
        String mongodbConnectionString = "mongodb://localhost:27017";
        MongoClient mongoClient = MongoClients.create(mongodbConnectionString);
        MongoDatabase database = mongoClient.getDatabase("luizalabs");
        return new WishlistDAO(database);
    }
}
