package dev.maurer.services;

import dev.maurer.entities.Employee;
import dev.maurer.exceptions.UserLoginException;

public interface EmployeeService {

    public Employee login(Employee employee) throws UserLoginException;
}
