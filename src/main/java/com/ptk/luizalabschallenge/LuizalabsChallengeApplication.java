package com.ptk.luizalabschallenge;

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

}
