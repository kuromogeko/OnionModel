package datev.ddd.example.Onion.domain.entity.exception;

import com.github.cstettler.dddttc.stereotype.BusinessException;

@BusinessException
public class MaximumAmountReachedException extends Exception {
    public MaximumAmountReachedException(String message) {
        super(message);
    }
}
