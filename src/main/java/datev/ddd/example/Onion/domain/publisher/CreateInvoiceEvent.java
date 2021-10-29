package datev.ddd.example.Onion.domain.publisher;

import com.github.cstettler.dddttc.stereotype.DomainEvent;
import datev.ddd.example.Onion.domain.entity.ShoppingCart;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@DomainEvent
@AllArgsConstructor
@Getter
public class CreateInvoiceEvent {
    ShoppingCart cart;

    // For the interested reader, this is a terrible way to do this.
    // UTC time would be appropriate, but this is an example
    LocalDate invoiceDate;
}
