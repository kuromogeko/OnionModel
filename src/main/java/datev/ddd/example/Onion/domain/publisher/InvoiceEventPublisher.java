package datev.ddd.example.Onion.domain.publisher;

// Domain event publisher
public interface InvoiceEventPublisher {
    void createInvoice(CreateInvoiceEvent event);
}
