package com.dx.expense.exception;

public class EmailAlreadyExistException extends RuntimeException {
    public EmailAlreadyExistException() {
        super("O e-mail já está cadastrado!");
    }
}
