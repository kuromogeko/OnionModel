package datev.ddd.example.Onion.infrastructure.boundary;

import datev.ddd.example.Onion.application.ShoppingAppService;
import datev.ddd.example.Onion.domain.entity.Quantity;
import datev.ddd.example.Onion.domain.entity.exception.CartElementNotFoundException;
import datev.ddd.example.Onion.domain.entity.exception.CartNotFoundException;
import datev.ddd.example.Onion.domain.entity.exception.ElementAlreadyInCartException;
import datev.ddd.example.Onion.domain.entity.exception.InvalidQuantityException;
import datev.ddd.example.Onion.domain.entity.exception.MaximumAmountReachedException;
import datev.ddd.example.Onion.domain.service.ShoppingService;
import datev.ddd.example.Onion.infrastructure.boundary.dto.CreateInvoiceDto;
import datev.ddd.example.Onion.infrastructure.boundary.dto.ExceptionDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api")
@AllArgsConstructor
public class ShoppingController {
    private final ShoppingAppService shoppingAppService;
    private final ShoppingService shoppingService;

    @GetMapping(path = "/carts/{id}")
    public ResponseEntity<?> getShoppingCartById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(shoppingAppService.getCart(id));
        } catch (CartNotFoundException e) {
            return ResponseEntity.badRequest().body(new ExceptionDto(e));
        }
    }

    @PostMapping(path = "/carts")
    public ResponseEntity<?> createCart() {
        return ResponseEntity.ok(this.shoppingService.createShoppingCart());
    }

    @DeleteMapping(path = "/carts/{id}")
    public ResponseEntity<?> deleteCart(@PathVariable UUID id) {
        try {
            this.shoppingService.deleteShoppingCart(id);
            return ResponseEntity.ok().build();
        } catch (CartNotFoundException e) {
            return ResponseEntity.badRequest().body(new ExceptionDto(e));
        }
    }

    @GetMapping(path = "/elements")
    public ResponseEntity<?> listPossibleCartElements() {
        return ResponseEntity.ok(shoppingAppService.listStockElements());
    }

    @PostMapping(path = "/invoice")
    public ResponseEntity<?> createInvoiceForCart(@RequestBody CreateInvoiceDto createInvoiceDto) {
        try {
            shoppingAppService.createInvoice(createInvoiceDto);
            return ResponseEntity.accepted().body(createInvoiceDto);
        } catch (CartNotFoundException e) {
            return ResponseEntity.badRequest().body(new ExceptionDto(e));
        }
    }

    @GetMapping(path = "/carts/{id}/price")
    public ResponseEntity<?> getPriceOfCart(@PathVariable String id) {
        try {
            return ResponseEntity.ok(shoppingAppService.getPriceOfCart(id));
        } catch (CartNotFoundException e) {
            return ResponseEntity.badRequest().body(new ExceptionDto(e));
        }
    }

    // We can see that immediately going to the Domain layer is allowed too!
    @PutMapping(path = "/carts/{id}/elements/{elem}")
    public ResponseEntity<?> addElementToCart(@PathVariable UUID id, @PathVariable String elem, @RequestBody Quantity quantity) {
        try {
            return ResponseEntity.ok(this.shoppingService.addElementToShoppingCart(id, elem, quantity));
        } catch (CartNotFoundException | CartElementNotFoundException | MaximumAmountReachedException | ElementAlreadyInCartException | InvalidQuantityException e) {
            return ResponseEntity.badRequest().body(new ExceptionDto(e));
        }
    }

    @PatchMapping(path = "/carts/{id}/elements/{elem}")
    public ResponseEntity<?> changeQuantityOfElementInCart(@PathVariable UUID id, @PathVariable String elem, @RequestBody Quantity quantity) {
        try {
            return ResponseEntity.ok(this.shoppingService.changeQuantityOfElementInShoppingCart(id, elem, quantity));
        } catch (CartNotFoundException | CartElementNotFoundException | MaximumAmountReachedException | InvalidQuantityException e) {
            return ResponseEntity.badRequest().body(new ExceptionDto(e));
        }
    }

    @DeleteMapping(path = "/carts/{id}/elements/{elem}")
    public ResponseEntity<?> removeElementFromCart(@PathVariable UUID id, @PathVariable String elem) {
        try {
            return ResponseEntity.ok(this.shoppingService.deleteElementFromShoppingCart(id, elem));
        } catch (CartNotFoundException | CartElementNotFoundException e) {
            return ResponseEntity.badRequest().body(new ExceptionDto(e));
        }
    }

}
