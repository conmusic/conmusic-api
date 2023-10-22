package school.sptech.conmusicapi.shared.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PAYLOAD_TOO_LARGE)
public class FileIsTooLargeException extends RuntimeException {
    public FileIsTooLargeException(String message) {
        super(message);
    }
}
