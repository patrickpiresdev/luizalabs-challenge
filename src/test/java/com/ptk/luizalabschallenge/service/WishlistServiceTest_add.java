package com.ptk.luizalabschallenge.service;

import com.ptk.luizalabschallenge.dao.WishlistDAO;
import com.ptk.luizalabschallenge.model.Wishlist;
import com.ptk.luizalabschallenge.model.WishlistItem;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class WishlistServiceTest_add {

    private final WishlistDAO wishlistDao;
    private final WishlistService wishlistService;
    private final String genericProductId;
    private final String genericDefaultClientId;

    public WishlistServiceTest_add(WishlistDAO wishlistDao, WishlistService wishlistService,
                                   String genericProductId, String genericDefaultClientId) {
        this.wishlistDao = wishlistDao;
        this.wishlistService = wishlistService;
        this.genericProductId = genericProductId;
        this.genericDefaultClientId = genericDefaultClientId;
    }

    public void whenWishlistIsFull() {
        Wishlist wishlist = new Wishlist();
        for (int i = 0; i < 20; i++)
            wishlist.add(new WishlistItem(genericDefaultClientId, String.valueOf(i)));
        when(wishlistDao.all(genericDefaultClientId))
                .thenReturn(wishlist);
        assertThrows(IllegalStateException.class,
                () -> wishlistService.add(genericProductId));
    }

    public void whenWishlistAlreadyHasTheProduct() {
        Wishlist wishlist = new Wishlist();
        wishlist.add(new WishlistItem(genericDefaultClientId, genericProductId));
        when(wishlistDao.all(genericDefaultClientId))
                .thenReturn(wishlist);
        assertThrows(IllegalStateException.class,
                () -> wishlistService.add(genericProductId));
    }

    public void whenCanInsertSuccessfully() {
        when(wishlistDao.all(genericDefaultClientId))
                .thenReturn(mock(Wishlist.class));
        wishlistService.add(genericProductId);
        verify(wishlistDao).insert(
                eq(new WishlistItem(genericDefaultClientId, genericProductId)));
    }
}
