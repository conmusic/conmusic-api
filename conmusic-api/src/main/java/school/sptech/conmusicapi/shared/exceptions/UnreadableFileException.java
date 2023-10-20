package school.sptech.conmusicapi.shared.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UnreadableFileException extends RuntimeException{
    public UnreadableFileException(String message) {
        super(message);
    }
}
