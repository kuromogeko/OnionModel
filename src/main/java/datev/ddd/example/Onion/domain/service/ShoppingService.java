package datev.ddd.example.Onion.domain.service;

import com.github.cstettler.dddttc.stereotype.DomainService;
import datev.ddd.example.Onion.domain.publisher.CreateInvoiceEvent;
import datev.ddd.example.Onion.domain.publisher.InvoiceEventPublisher;
import datev.ddd.example.Onion.domain.entity.Money;
import datev.ddd.example.Onion.domain.entity.Quantity;
import datev.ddd.example.Onion.domain.entity.ShoppingCart;
import datev.ddd.example.Onion.domain.entity.exception.CartElementNotFoundException;
import datev.ddd.example.Onion.domain.entity.exception.CartNotFoundException;
import datev.ddd.example.Onion.domain.entity.exception.ElementAlreadyInCartException;
import datev.ddd.example.Onion.domain.entity.exception.InvalidQuantityException;
import datev.ddd.example.Onion.domain.entity.exception.MaximumAmountReachedException;
import datev.ddd.example.Onion.domain.repository.CartElementRepository;
import datev.ddd.example.Onion.domain.repository.ShoppingCartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@DomainService
@AllArgsConstructor
public class ShoppingService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartElementRepository cartElementRepository;
    private final InvoiceEventPublisher invoiceEventPublisher;

    // USE CASE 0
    public ShoppingCart createShoppingCart() {
        return shoppingCartRepository.save(ShoppingCart.createDefaultShoppingCart());
    }

    // USE CASE 0
    public void deleteShoppingCart(UUID id) throws CartNotFoundException {
        shoppingCartRepository.delete(id);
    }

    // USE CASE 0.1
    public ShoppingCart addElementToShoppingCart(UUID shoppingCartId, String cartElementName, Quantity quantity) throws CartNotFoundException, CartElementNotFoundException, MaximumAmountReachedException, ElementAlreadyInCartException, InvalidQuantityException {
        quantity.validate();
        var cart = shoppingCartRepository.findById(shoppingCartId);
        var item = cartElementRepository.findByName(cartElementName);
        cart.addToWarenkorb(item, quantity);
        return shoppingCartRepository.save(cart);
    }

    // USE CASE 0.1
    public ShoppingCart changeQuantityOfElementInShoppingCart(UUID shoppingCartId, String cartElementName, Quantity quantity) throws CartNotFoundException, CartElementNotFoundException, MaximumAmountReachedException, InvalidQuantityException {
        quantity.validate();
        var cart = shoppingCartRepository.findById(shoppingCartId);
        cart.findElementAndSetQuantity(cartElementName, quantity);
        return shoppingCartRepository.save(cart);
    }

    // USE CASE 0.1
    public ShoppingCart deleteElementFromShoppingCart(UUID shoppingCartId, String cartElementName) throws CartNotFoundException, CartElementNotFoundException {
        var cart = shoppingCartRepository.findById(shoppingCartId);
        cart.findElementAndRemove(cartElementName);
        return shoppingCartRepository.save(cart);
    }

    // USE CASE 1
    public Money calculatePriceOfCart(UUID shoppingCartId) throws CartNotFoundException {
        var cart = shoppingCartRepository.findById(shoppingCartId);
        return cart.calculatePrices();
    }

    //USE CASE 1.1
    public void createInvoice(UUID shoppingCartId) throws CartNotFoundException {
        var cart = shoppingCartRepository.findById(shoppingCartId);
        var event = new CreateInvoiceEvent(cart, LocalDate.now());
        invoiceEventPublisher.createInvoice(event);
    }

}
