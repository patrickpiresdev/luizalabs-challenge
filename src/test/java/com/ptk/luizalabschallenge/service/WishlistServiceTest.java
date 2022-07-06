package com.ptk.luizalabschallenge.service;

import com.ptk.luizalabschallenge.dao.WishlistDAO;
import com.ptk.luizalabschallenge.model.Wishlist;
import com.ptk.luizalabschallenge.model.WishlistItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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
}