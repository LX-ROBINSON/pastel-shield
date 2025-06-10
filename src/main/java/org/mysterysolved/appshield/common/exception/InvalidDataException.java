package org.mysterysolved.appshield.common.exception;

public class InvalidDataException extends RuntimeException {

    private int code;

    public InvalidDataException() {
    }

    public InvalidDataException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
