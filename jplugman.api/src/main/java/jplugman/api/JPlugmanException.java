package jplugman.api;

public class JPlugmanException extends RuntimeException {

    public JPlugmanException(String message) {
        super(message);
    }

    public JPlugmanException(String message, Throwable cause) {
        super(message, cause);
    }
}
