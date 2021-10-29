package datev.ddd.example.Onion.infrastructure.publisher;

import datev.ddd.example.Onion.domain.publisher.CreateInvoiceEvent;
import datev.ddd.example.Onion.domain.publisher.InvoiceEventPublisher;
import datev.ddd.example.Onion.domain.entity.ShoppingCartElement;
import org.springframework.stereotype.Component;

@Component
public class ConsoleInvoiceEventPublisherImpl implements InvoiceEventPublisher {

    //THIS should be calling a remote system which has the actual task of doing invoices, but we are alittle lazy....
    @Override
    public void createInvoice(CreateInvoiceEvent event) {
        System.out.println("\n--------------Invoice--------------");
        // For example there should be a running number "invoice-number" which the other systems context would have
        for (ShoppingCartElement elem : event.getCart().getContent()) {
            System.out.printf("Item: %s Price: %,.2f%n", elem.getName(), elem.getCalculatedPrice().getMoney().doubleValue());
        }
        System.out.printf("\nTotal sum: %,.2f \n", event.getCart().calculatePrices().getMoney());
        System.out.printf("Payment due/ Payment target: %s\n", event.getInvoiceDate().toString());
    }
}
