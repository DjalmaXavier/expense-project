package com.dx.expense.exception;

public class EmailAlreadyExistException extends RuntimeException {
    public EmailAlreadyExistException() {
        super("E-mail já cadastrado!");
    }
}
