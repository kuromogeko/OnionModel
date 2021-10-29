package datev.ddd.example.Onion.infrastructure.repository;

import datev.ddd.example.Onion.domain.entity.*;
import datev.ddd.example.Onion.domain.entity.exception.CartElementNotFoundException;
import datev.ddd.example.Onion.domain.repository.CartElementRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class HashCartElementRepositoryImpl implements CartElementRepository {
    private final HashMap<String, CartElement> totallyARepo = new HashMap<>();

    public HashCartElementRepositoryImpl() {
        var elem1 = new ToolCartElement("Hammer", new Quantity(10), new Money(BigDecimal.valueOf(10)));
        this.totallyARepo.put(elem1.getName(), elem1);
        var elem2 = new ToolCartElement("Drill", new Quantity(5), new Money(BigDecimal.valueOf(30)));
        this.totallyARepo.put(elem2.getName(), elem2);
        var elem3 = new ScrewCartElement("Wood Screw", new Quantity(500), new Money(BigDecimal.valueOf(1)));
        this.totallyARepo.put(elem3.getName(), elem3);
        var elem4 = new ScrewCartElement("Concrete screw", new Quantity(1000), new Money(BigDecimal.valueOf(0.5)));
        this.totallyARepo.put(elem4.getName(), elem4);
        var elem5 = new ScrewCartElement("Masonry screw", new Quantity(300), new Money(BigDecimal.valueOf(1.5)));
        this.totallyARepo.put(elem5.getName(), elem5);
    }

    @Override
    public CartElement findByName(String name) throws CartElementNotFoundException {
        var found = this.totallyARepo.get(name);
        if (null == found) {
            throw new CartElementNotFoundException("The cart element was not found in repo");
        }
        return found;
    }

    @Override
    public List<CartElement> findAll() {
        return new ArrayList<>(totallyARepo.values());
    }
}
