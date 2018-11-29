package Core;

/**
 * This class represents the exception when there are duplicate resources.
 * @author Noah Lenagan
 * @version 1.0
 */
public class ResourceDuplicateException extends IllegalArgumentException {

    /**
     * Creates the class.
     * @param message The exception message.
     */
    ResourceDuplicateException(String message) {
        super(message);
    }

}
