package school.sptech.conmusicapi.shared.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class UserForbiddenActionException extends RuntimeException {

    public UserForbiddenActionException(String message) {
        super(message);
    }
}
