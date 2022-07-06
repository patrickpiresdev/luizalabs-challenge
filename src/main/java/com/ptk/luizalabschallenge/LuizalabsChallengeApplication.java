package com.ptk.luizalabschallenge;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.ptk.luizalabschallenge.dao.WishlistDAO;
import com.ptk.luizalabschallenge.service.WishlistService;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class LuizalabsChallengeApplication {
    @Value("${mongodb.uri}")
    private String mongodbUri;

    @Value("${mongodb.database}")
    private String mongodbDatabase;

	public static void main(String[] args) {
		SpringApplication.run(LuizalabsChallengeApplication.class, args);
	}

    @Bean
    public MongoDatabase getDatabase() {
        return MongoClients.create(mongodbUri).getDatabase(mongodbDatabase);
    }

    @Bean
    public WishlistDAO createWishlistDao(MongoDatabase database) {
        return new WishlistDAO(database);
    }

    @Bean
    public String getDefaultClientId(MongoDatabase database) {
        MongoCollection<Document> clientCollection = database.getCollection("client");
        Document document = clientCollection
                .find(Filters.eq("name", "default"))
                .first();
        if (document == null)
            return createDefaultClient(clientCollection);
        return ((ObjectId) document.get("_id")).toString();
    }

    private String createDefaultClient(MongoCollection<Document> clientCollection) {
        Document defaultClient = new Document("name", "default");
        clientCollection.insertOne(defaultClient);
        return ((ObjectId) defaultClient.get("_id")).toString();
    }

    @Bean
    public WishlistService getWishlistService(WishlistDAO wishlistDAO, String defaultClientId) {
        return new WishlistService(wishlistDAO, defaultClientId);
    }
}
