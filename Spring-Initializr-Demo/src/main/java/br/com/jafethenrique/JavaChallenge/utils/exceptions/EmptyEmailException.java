package br.com.jafethenrique.JavaChallenge.utils.exceptions;

public class EmptyEmailException extends Exception {
    public EmptyEmailException(String message) {
        super(message);
    }
}
