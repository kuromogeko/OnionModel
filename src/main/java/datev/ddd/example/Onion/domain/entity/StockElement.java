package datev.ddd.example.Onion.domain.entity;

public interface StockElement {
    Quantity getMaxQuantity();

    Money getSingleUnitPrice();
}
