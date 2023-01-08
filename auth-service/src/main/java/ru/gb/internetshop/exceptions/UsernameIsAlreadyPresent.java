package ru.gb.internetshop.exceptions;

public class UsernameIsAlreadyPresent extends RuntimeException{
    public UsernameIsAlreadyPresent(String message) {
        super(message);
    }
}
