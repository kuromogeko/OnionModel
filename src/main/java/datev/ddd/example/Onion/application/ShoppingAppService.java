package datev.ddd.example.Onion.application;

import com.github.cstettler.dddttc.stereotype.ApplicationService;
import datev.ddd.example.Onion.domain.entity.ShoppingCart;
import datev.ddd.example.Onion.domain.entity.exception.CartNotFoundException;
import datev.ddd.example.Onion.domain.repository.CartElementRepository;
import datev.ddd.example.Onion.domain.repository.ShoppingCartRepository;
import datev.ddd.example.Onion.domain.service.ShoppingService;
import datev.ddd.example.Onion.infrastructure.boundary.dto.CreateInvoiceDto;
import datev.ddd.example.Onion.infrastructure.boundary.dto.PriceDto;
import datev.ddd.example.Onion.infrastructure.boundary.dto.StockElementDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationService
@Service
@AllArgsConstructor
public class ShoppingAppService {
    private final ShoppingService shoppingService;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartElementRepository cartElementRepository;

    // USE CASE 0.2
    public ShoppingCart getCart(String id) throws CartNotFoundException {
        // Look a mapping layer :)
        UUID uuid = UUID.fromString(id);
        return shoppingCartRepository.findById(uuid);
    }

    // USE CASE 2
    public List<StockElementDto> listStockElements() {
        return cartElementRepository.findAll().stream().map(StockElementDto::new).collect(Collectors.toList());
    }


    public void createInvoice(CreateInvoiceDto createInvoiceDto) throws CartNotFoundException {
        UUID uuid = UUID.fromString(createInvoiceDto.getId());
        shoppingService.createInvoice(uuid);
    }

    public PriceDto getPriceOfCart(String id) throws CartNotFoundException {
        UUID uuid = UUID.fromString(id);
        var price = shoppingService.calculatePriceOfCart(uuid);
        return new PriceDto(price.getMoney().doubleValue());
    }


}
