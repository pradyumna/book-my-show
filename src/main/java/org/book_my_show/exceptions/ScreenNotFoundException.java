package org.book_my_show.exceptions;

public class ScreenNotFoundException extends RuntimeException{
    public ScreenNotFoundException(){super();}
    public ScreenNotFoundException(String message){super(message);}
}
