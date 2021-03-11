package dev.maurer.services;

import dev.maurer.daos.EmployeeDAO;
import dev.maurer.entities.Employee;
import dev.maurer.exceptions.EmployeeNotFoundException;
import dev.maurer.exceptions.UserLoginException;

public class EmployeeServiceImpl implements EmployeeService{

    EmployeeDAO employeeDAO;

    public EmployeeServiceImpl(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @Override
    public Employee login(Employee employee) throws UserLoginException {
        Employee loggedInEmployee = employeeDAO.login(employee);
        if (loggedInEmployee == null)
            throw new UserLoginException();
        return loggedInEmployee;
    }

    @Override
    public Employee getEmployeeById(int id) throws EmployeeNotFoundException {
        Employee employee = employeeDAO.getEmployeeById(id);
        if (employee == null)
            throw new EmployeeNotFoundException(id);
        return employee;
    }
}
