package org.book_my_show.exceptions;

public class TheatreNotFoundException extends RuntimeException{
    public TheatreNotFoundException(String message){
        super(message);
    }

    public TheatreNotFoundException(){
        super();
    }
}
