package datev.ddd.example.Onion.domain.entity;

import com.github.cstettler.dddttc.stereotype.AggregateId;
import datev.ddd.example.Onion.domain.entity.exception.MaximumAmountReachedException;


public interface ShoppingCartElement {

    @AggregateId
    String getName();

    void setQuantity(Quantity quantity) throws MaximumAmountReachedException;

    Money getCalculatedPrice();
}
