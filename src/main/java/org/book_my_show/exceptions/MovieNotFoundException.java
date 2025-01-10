package org.book_my_show.exceptions;

public class MovieNotFoundException extends RuntimeException{
    public MovieNotFoundException(){super();}
    public MovieNotFoundException(String message){super(message);}
}
