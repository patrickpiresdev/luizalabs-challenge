package com.ptk.luizalabschallenge.service;

import com.ptk.luizalabschallenge.dao.WishlistDAO;
import com.ptk.luizalabschallenge.model.Product;
import com.ptk.luizalabschallenge.model.Wishlist;
import com.ptk.luizalabschallenge.model.WishlistItem;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WishlistServiceTest {
    @Test
    void add() {
        WishlistDAO wishlistDao = mock(WishlistDAO.class);
        String genericDefaultClientId = "876c5b4a321";
        String genericProductId = "123a4b5c678";
        WishlistService wishlistService = new WishlistService(wishlistDao, genericDefaultClientId);
        WishlistServiceTest_add wishlistServiceTest_add =
                new WishlistServiceTest_add(wishlistDao, wishlistService, genericProductId, genericDefaultClientId);

        wishlistServiceTest_add.whenWishlistIsFull();
        wishlistServiceTest_add.whenWishlistAlreadyHasTheProduct();
        wishlistServiceTest_add.whenCanInsertSuccessfully();
    }

    @Test
    void remove() {
        WishlistDAO wishlistDao = mock(WishlistDAO.class);
        String genericDefaultClientId = "876c5b4a321";
        String genericProductId = "123a4b5c678";
        WishlistService wishlistService = new WishlistService(wishlistDao, genericDefaultClientId);

        WishlistServiceTest_remove wishlistServiceTest_remove =
                new WishlistServiceTest_remove(wishlistDao, genericProductId, genericDefaultClientId, wishlistService);

        wishlistServiceTest_remove.whenProductIsNotOnWishlist();
        wishlistServiceTest_remove.whenCanRemoveSuccessfully();
    }

    @Test
    void all() {
        WishlistDAO wishlistDao = mock(WishlistDAO.class);
        String genericDefaultClientId = "876c5b4a321";
        String genericProductId = "123a4b5c678";
        WishlistService wishlistService = new WishlistService(wishlistDao, genericDefaultClientId);

        List<Product> products = Arrays.asList(
                new Product(genericProductId, "product 1", "product 1 description"),
                new Product(genericProductId, "product 2", "product 2 description"),
                new Product(genericProductId, "product 3", "product 3 description"));

        Wishlist wishlist = new Wishlist();
        products.stream()
                .map(product -> new WishlistItem(genericDefaultClientId, product.getId(), product))
                .forEach(wishlist::add);

        when(wishlistDao.all(genericDefaultClientId))
                .thenReturn(wishlist);

        assertEquals(wishlistService.all(), products);
    }
}