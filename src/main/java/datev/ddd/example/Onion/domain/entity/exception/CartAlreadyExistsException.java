package datev.ddd.example.Onion.domain.entity.exception;

import com.github.cstettler.dddttc.stereotype.BusinessException;

@BusinessException
public class CartAlreadyExistsException extends Exception {
    public CartAlreadyExistsException(String message) {
        super(message);
    }
}
