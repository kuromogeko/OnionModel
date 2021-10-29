package datev.ddd.example.Onion.domain.entity;

import com.github.cstettler.dddttc.stereotype.Aggregate;
import com.github.cstettler.dddttc.stereotype.AggregateId;
import datev.ddd.example.Onion.domain.entity.exception.MaximumAmountReachedException;
import lombok.Getter;

@Aggregate
@Getter
public class ScrewCartElement implements CartElement {

    private final String name;
    private Quantity quantity;
    private final Quantity maxQuantity;
    private final Money singleUnitPrice;

    public ScrewCartElement(String name, Quantity maxQuantity, Money singleUnitPrice) {
        this.name = name;
        this.maxQuantity = maxQuantity;
        this.singleUnitPrice = singleUnitPrice;
        this.quantity = new Quantity(1);
    }

    @AggregateId
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setQuantity(Quantity quantity) throws MaximumAmountReachedException {
        if (quantity.greaterThanOrEquals(this.maxQuantity)) {
            throw new MaximumAmountReachedException(String.format("The given quantity %d is bigger than the maximum %d", quantity.getQuantity(), this.maxQuantity.getQuantity()));
        }
        this.quantity = quantity;
    }

    @Override
    public Money getCalculatedPrice() {
        return this.singleUnitPrice.times(this.quantity).the25PercentDiscount();
    }
}
