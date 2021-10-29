package datev.ddd.example.Onion.infrastructure.repository.mongodb;

import datev.ddd.example.Onion.domain.entity.ShoppingCart;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

//Fun Fact: Spring gets confused when naming this interface MongoShoppingCartRepository and won't start
public interface SpringMongoShoppingCartRepository extends MongoRepository<ShoppingCart, UUID> {
}
