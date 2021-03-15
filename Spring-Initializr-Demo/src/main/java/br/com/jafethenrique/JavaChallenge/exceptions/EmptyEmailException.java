package br.com.jafethenrique.JavaChallenge.exceptions;

public class EmptyEmailException extends Exception {
    public EmptyEmailException(String message) {
        super(message);
    }
}
