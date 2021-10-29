package datev.ddd.example.Onion.domain.entity;

import com.github.cstettler.dddttc.stereotype.Aggregate;
import com.github.cstettler.dddttc.stereotype.AggregateFactory;
import com.github.cstettler.dddttc.stereotype.AggregateId;
import datev.ddd.example.Onion.domain.entity.exception.CartElementNotFoundException;
import datev.ddd.example.Onion.domain.entity.exception.ElementAlreadyInCartException;
import datev.ddd.example.Onion.domain.entity.exception.MaximumAmountReachedException;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Aggregate
// Also an Entity
public class ShoppingCart {
    private final UUID id;
    private final List<ShoppingCartElement> content;

    @AggregateId
    public UUID getId() {
        return id;
    }

    public List<ShoppingCartElement> getContent() {
        return content;
    }

    public ShoppingCart() {
        this.id = UUID.randomUUID();
        this.content = new ArrayList<>();
    }

    public ShoppingCart(List<ShoppingCartElement> content) {
        this.id = UUID.randomUUID();
        this.content = content;
    }

    // Doesn't REALLY belong into a domain object, but its okay
    @PersistenceConstructor
    private ShoppingCart(UUID id, List<ShoppingCartElement> content) {
        this.id = id;
        this.content = content;
    }

    public void addToWarenkorb(ShoppingCartElement addMeElement, Quantity quantity) throws ElementAlreadyInCartException, MaximumAmountReachedException {
        var identifyingElementAlreadyPresend = this.content
                .stream()
                .anyMatch(cartElement -> cartElement.getName().equals(addMeElement.getName()));
        if (identifyingElementAlreadyPresend) {
            throw new ElementAlreadyInCartException("An Element of this type is already in the cart. Consider changing its quantity");
        }
        addMeElement.setQuantity(quantity);
        this.content.add(addMeElement);
    }

    public void findElementAndSetQuantity(String elementName, Quantity quantity) throws CartElementNotFoundException, MaximumAmountReachedException {
        var element = searchInCartByName(elementName);
        element.setQuantity(quantity);
    }


    public void findElementAndRemove(String elementName) throws CartElementNotFoundException {
        var element = this.searchInCartByName(elementName);
        this.content.remove(element);
    }


    public Money calculatePrices() {
        return this.content.stream()
                           .map(ShoppingCartElement::getCalculatedPrice)
                           .reduce(Money::add)
                           // In a perfect world we would have a domain exception for this, but lets scope it out
                           .orElseThrow();
    }

    private ShoppingCartElement searchInCartByName(String elementName) throws CartElementNotFoundException {
        return this.content
                .stream()
                .filter(shoppingCartElement -> shoppingCartElement.getName().equals(elementName))
                .findFirst()
                .orElseThrow(() -> new CartElementNotFoundException(String.format("An element with the name %s could not be found", elementName)));
    }

    @AggregateFactory(ShoppingCart.class)
    public static ShoppingCart createDefaultShoppingCart() {
        // If there was default settings for a cart they would be here
        return new ShoppingCart();
    }

}
