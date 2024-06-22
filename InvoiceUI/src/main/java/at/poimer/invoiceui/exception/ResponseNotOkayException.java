package at.poimer.invoiceui.exception;

public class ResponseNotOkayException extends Exception {
    public ResponseNotOkayException(String errorMessage) {
        super(errorMessage);
    }
}
