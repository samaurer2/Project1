package dev.maurer.exceptions;

public class UserLoginException extends Exception {
    public UserLoginException() {
        super("Username or password incorrect");
    }
}
