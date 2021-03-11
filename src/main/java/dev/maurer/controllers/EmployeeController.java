package dev.maurer.controllers;

import com.google.gson.Gson;
import dev.maurer.entities.Employee;
import dev.maurer.exceptions.UserLoginException;
import dev.maurer.services.EmployeeService;
import dev.maurer.utils.JwtUtil;
import io.javalin.http.Handler;

public class EmployeeController {

    private EmployeeService employeeService;
    private Gson gson;
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
        gson = new Gson();
    }

    public Handler loginHandler = ctx -> {
        try {
            String body = ctx.body();
            Employee employee = gson.fromJson(body, Employee.class);
            employee = employeeService.login(employee);

            ctx.result(JwtUtil.generate(employee.getType().toString(),employee.getName(),employee.getEmployeeId()));
            ctx.status(200);
        } catch (UserLoginException e) {
            ctx.result(e.getMessage());
            ctx.status(404);
        }
    };
}
