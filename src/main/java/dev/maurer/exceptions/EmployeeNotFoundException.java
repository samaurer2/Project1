package dev.maurer.exceptions;

public class EmployeeNotFoundException extends Exception{
    public EmployeeNotFoundException(int id) {
        super("Employee with id " + id + " not found.");
    }
}
