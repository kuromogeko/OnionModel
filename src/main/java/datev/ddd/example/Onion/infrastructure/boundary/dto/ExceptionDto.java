package datev.ddd.example.Onion.infrastructure.boundary.dto;

import lombok.Getter;

@Getter
public class ExceptionDto {
    private final String message;

    public ExceptionDto(Exception e) {
        this.message = e.getMessage();
    }
}
