package the.husky.exception;

public class PageGeneratorException extends RuntimeException {
    public PageGeneratorException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
