package app.exceptions;

public class DirectoryAccessException extends RuntimeException {
    public DirectoryAccessException(String msg) {
        super(msg);
    }
}
