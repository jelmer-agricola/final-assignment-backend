package nl.autogarage.finalassignmentbackendmain.exceptions;

public class InvoiceAlreadyExistsException extends RuntimeException {

    public InvoiceAlreadyExistsException() {
        super();
    }

    public InvoiceAlreadyExistsException(String message){
        super(message);

    }
}

