package pl.bbkon.microblog.tags;

public class TagAlreadyExistsException extends RuntimeException {
    public TagAlreadyExistsException(String message) {
        super(message);
    }
}
