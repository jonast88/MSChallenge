package com.everis.ms.exception;


public class NotFoundException extends Exception {

    public NotFoundException() {
    }

    public NotFoundException(String date) {
        super(String.format("Data for Date %s Not found", date));
    }

    public NotFoundException(String date, Throwable cause) {
        super(String.format("Date %s Not found", date), cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    public NotFoundException(String date, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(String.format("Date %s Not found", date), cause, enableSuppression, writableStackTrace);
    }
}
