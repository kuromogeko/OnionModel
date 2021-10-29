package datev.ddd.example.Onion.domain.entity.exception;

import com.github.cstettler.dddttc.stereotype.BusinessException;

@BusinessException
public class ElementAlreadyInCartException extends Exception {
    public ElementAlreadyInCartException(String message) {
        super(message);
    }
}
