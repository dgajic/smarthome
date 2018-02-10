package org.eclipse.smarthome.binding.unipievok.internal;

public class UniPiServiceException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 8193158391923634841L;

    public UniPiServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UniPiServiceException(String message) {
        super(message);
    }

}
