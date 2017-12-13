package com.nikos.exceptions;

public class SerializeException extends Exception{

	private static final long serialVersionUID = 1L;

	public SerializeException() {
		
	}

    public SerializeException(String message){
       super(message);
    }
    public SerializeException(String message,Exception ex){
        super(message,ex);
     }
    public SerializeException(Exception ex){
        super(ex);
     }
}