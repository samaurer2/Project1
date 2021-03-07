package dev.maurer.services;

import dev.maurer.daos.EmployeeDAO;
import dev.maurer.entities.Employee;
import dev.maurer.exceptions.UserLoginException;

public class EmployeeServiceImpl implements EmployeeService{

    EmployeeDAO employeeDAO;

    public EmployeeServiceImpl(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @Override
    public Employee login(Employee employee) throws UserLoginException {
        return employeeDAO.login(employee);
    }
}
