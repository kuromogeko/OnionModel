package datev.ddd.example.Onion.domain.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.github.cstettler.dddttc.stereotype.ValueObject;
import datev.ddd.example.Onion.domain.entity.exception.InvalidQuantityException;
import lombok.Getter;
import lombok.With;

@ValueObject
@Getter
@With
public class Quantity {
    private final int quantity;

    public boolean greaterThanOrEquals(Quantity theOtherQuantity) {
        return this.quantity >= theOtherQuantity.getQuantity();
    }

    @JsonCreator
    public Quantity(int quantity) {
        this.quantity = quantity;
    }

    public void validate() throws InvalidQuantityException {
        if(this.quantity < 0){
            throw new InvalidQuantityException();
        }
    }
}
