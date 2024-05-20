package sapo.intern.mock.carstore.global.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotFoundException extends RuntimeException{
    private String errorMessage;
}
