package org.book_my_show.exceptions;

public class ShowSlotUnavailableException extends RuntimeException{
    public ShowSlotUnavailableException(String message){
        super(message);
    }
}
