package dev.maurer.controllers;

import com.google.gson.Gson;
import dev.maurer.entities.Employee;
import dev.maurer.exceptions.UserLoginException;
import dev.maurer.services.EmployeeService;
import io.javalin.http.Handler;

public class EmployeeController {

    EmployeeService employeeService;
    Gson gson;
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
        gson = new Gson();
    }

    public Handler loginHandler = ctx -> {
        try {
            String body = ctx.body();
            Employee employee = gson.fromJson(body, Employee.class);
            employee = employeeService.login(employee);
            ctx.result(gson.toJson(employee));

        } catch (UserLoginException e) {
            ctx.result(e.getMessage());
            ctx.status(404);
        }

    };
}
