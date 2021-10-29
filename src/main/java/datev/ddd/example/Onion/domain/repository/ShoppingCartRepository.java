package datev.ddd.example.Onion.domain.repository;

import datev.ddd.example.Onion.domain.entity.ShoppingCart;
import datev.ddd.example.Onion.domain.entity.exception.CartNotFoundException;

import java.util.UUID;


public interface ShoppingCartRepository {
    ShoppingCart findById(UUID id) throws CartNotFoundException;

    ShoppingCart save(ShoppingCart shoppingCart);

    void delete(UUID id) throws CartNotFoundException;
}
