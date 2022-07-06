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

        whenWishlistIsFull(wishlistDao, wishlistService, genericProductId, genericDefaultClientId);
        whenWishlistAlreadyHasTheProduct(wishlistDao, wishlistService, genericProductId, genericDefaultClientId);
        whenCanInsertSuccessfully(wishlistDao, wishlistService, genericProductId, genericDefaultClientId);
    }

    private void whenWishlistIsFull(WishlistDAO wishlistDao, WishlistService wishlistService, String genericProductId, String genericDefaultClientId) {
        Wishlist wishlist = new Wishlist();
        for (int i = 0; i < 20; i++)
            wishlist.add(new WishlistItem(genericDefaultClientId, String.valueOf(i)));
        when(wishlistDao.all(genericDefaultClientId))
                .thenReturn(wishlist);
        assertThrows(IllegalStateException.class,
                () -> wishlistService.add(genericProductId));
    }

    private void whenWishlistAlreadyHasTheProduct(WishlistDAO wishlistDao, WishlistService wishlistService, String genericProductId, String genericDefaultClientId) {
        Wishlist wishlist = new Wishlist();
        wishlist.add(new WishlistItem(genericDefaultClientId, genericProductId));
        when(wishlistDao.all(genericDefaultClientId))
                .thenReturn(wishlist);
        assertThrows(IllegalStateException.class,
                () -> wishlistService.add(genericProductId));
    }

    private void whenCanInsertSuccessfully(WishlistDAO wishlistDao, WishlistService wishlistService, String genericProductId, String genericDefaultClientId) {
        when(wishlistDao.all(genericDefaultClientId))
                .thenReturn(mock(Wishlist.class));
        wishlistService.add(genericProductId);
        verify(wishlistDao).insert(
                eq(new WishlistItem(genericDefaultClientId, genericProductId)));
    }
}