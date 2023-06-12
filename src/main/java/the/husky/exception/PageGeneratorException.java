package the.husky.exception;


public class PageGeneratorException extends RuntimeException {
    public PageGeneratorException(String message) {
        super(message);
    }

    public PageGeneratorException(String message, Throwable cause) {
        super(message, cause);
    }
}
