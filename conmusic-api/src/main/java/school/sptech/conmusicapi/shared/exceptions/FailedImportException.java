package school.sptech.conmusicapi.shared.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class FailedImportException extends RuntimeException {
    public FailedImportException(String message) {
        super(message);
    }
}
