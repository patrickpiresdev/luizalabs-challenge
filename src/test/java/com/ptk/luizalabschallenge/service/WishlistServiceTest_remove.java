package com.ptk.luizalabschallenge.service;

import com.ptk.luizalabschallenge.dao.WishlistDAO;
import com.ptk.luizalabschallenge.model.WishlistItem;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WishlistServiceTest_remove {

    private final WishlistDAO wishlistDao;
    private final String genericProductId;
    private final String genericDefaultClientId;
    private final WishlistService wishlistService;

    public WishlistServiceTest_remove(WishlistDAO wishlistDao, String genericProductId, String genericDefaultClientId, WishlistService wishlistService) {
        this.wishlistDao = wishlistDao;
        this.genericProductId = genericProductId;
        this.genericDefaultClientId = genericDefaultClientId;
        this.wishlistService = wishlistService;
    }

    public void whenProductIsNotOnWishlist() {
        WishlistItem wishlistItem = new WishlistItem(genericDefaultClientId, genericProductId);
        when(wishlistDao.find(wishlistItem))
                .thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class,
                () -> wishlistService.remove(genericProductId));
    }

    public void whenCanRemoveSuccessfully() {
        WishlistItem wishlistItem = new WishlistItem(genericDefaultClientId, genericProductId);
        when(wishlistDao.find(wishlistItem))
                .thenReturn(Optional.of(wishlistItem));
        wishlistService.remove(genericProductId);
        verify(wishlistDao).remove(wishlistItem);
    }
}
