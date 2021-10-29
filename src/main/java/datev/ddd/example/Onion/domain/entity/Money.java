package datev.ddd.example.Onion.domain.entity;

import com.github.cstettler.dddttc.stereotype.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;

import java.math.BigDecimal;

@ValueObject
@With
@AllArgsConstructor
@Getter
public class Money {
    private final BigDecimal money;

    //Because we are totally a bank and when we calculate stuff we wouldnt need all that rounding stuff, right?
    public Money add(Money moreMoney) {
        return new Money(this.money.add(moreMoney.getMoney()));
    }

    public Money times(Quantity quantity) {
        return new Money(this.money.multiply(BigDecimal.valueOf(quantity.getQuantity())));
    }

    public Money the25PercentDiscount() {
        return new Money(this.money.multiply(BigDecimal.valueOf(0.75)));
    }

}
