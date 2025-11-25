package group20.Exceptions;

/**Thrown when a an Invalid File Format is specified for some purpose */
public class InvalidFileFormatException extends Exception {
    public InvalidFileFormatException(String message) { super(message); }
    public InvalidFileFormatException(String message, Throwable cause) { super(message, cause); }
}

