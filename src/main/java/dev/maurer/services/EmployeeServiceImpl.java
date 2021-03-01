package dev.maurer.services;

import dev.maurer.daos.EmployeeDAO;

public class EmployeeService {

    EmployeeDAO employeeDAO;

    public EmployeeService(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }


}
