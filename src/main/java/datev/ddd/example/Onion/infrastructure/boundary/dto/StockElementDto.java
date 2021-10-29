package datev.ddd.example.Onion.infrastructure.boundary.dto;


import datev.ddd.example.Onion.domain.entity.CartElement;
import datev.ddd.example.Onion.domain.entity.Money;
import datev.ddd.example.Onion.domain.entity.Quantity;
import lombok.Getter;

@Getter
public class StockElementDto {
    private final String name;
    private final Quantity maxQuantity;
    private final Money singleUnitPrice;

    public StockElementDto(CartElement cartElement) {
        this.maxQuantity = cartElement.getMaxQuantity();
        this.singleUnitPrice = cartElement.getSingleUnitPrice();
        this.name = cartElement.getName();
    }
}
