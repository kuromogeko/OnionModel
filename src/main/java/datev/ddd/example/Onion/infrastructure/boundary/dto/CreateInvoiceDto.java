package datev.ddd.example.Onion.infrastructure.boundary.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;


@Getter
public class CreateInvoiceDto {
    private final String id;

    @JsonCreator
    public CreateInvoiceDto(String id) {
        this.id = id;
    }
}
