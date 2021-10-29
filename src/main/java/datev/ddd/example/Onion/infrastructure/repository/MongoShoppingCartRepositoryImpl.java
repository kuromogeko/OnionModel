package datev.ddd.example.Onion.infrastructure.repository;

import datev.ddd.example.Onion.domain.entity.ShoppingCart;
import datev.ddd.example.Onion.domain.entity.exception.CartNotFoundException;
import datev.ddd.example.Onion.domain.repository.ShoppingCartRepository;
import datev.ddd.example.Onion.infrastructure.repository.mongodb.SpringMongoShoppingCartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class MongoShoppingCartRepositoryImpl implements ShoppingCartRepository {
    private final SpringMongoShoppingCartRepository shoppingCartRepository;

    @Override
    public ShoppingCart findById(UUID id) throws CartNotFoundException {
        return shoppingCartRepository.findById(id)
                                     .orElseThrow(() -> new CartNotFoundException("The shopping cart was not found in the repository"));
    }

    @Override
    public ShoppingCart save(ShoppingCart shoppingCart) {
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public void delete(UUID id) throws CartNotFoundException {
        shoppingCartRepository.delete(findById(id));
    }
}
