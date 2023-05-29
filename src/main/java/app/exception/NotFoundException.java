package app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/*
 * @ControllerAdvice - another way of handling exception
 * @ControllerAdvice
 * public class ExceptionController {
 * @ExceptionHandler(NotFoundException.class)
 * public ResponseEntity<NotFoundException> handleNotFoundException() {
 * System.out.println("do i run");
 * return ResponseEntity.notFound().build();
 * }
 * }
 *  */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not Found")
public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    protected NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
