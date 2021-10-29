package datev.ddd.example.Onion.domain.entity.exception;

import com.github.cstettler.dddttc.stereotype.BusinessException;

@BusinessException
public class CartElementNotFoundException extends Exception {
    public CartElementNotFoundException(String message) {
        super(message);
    }
}
