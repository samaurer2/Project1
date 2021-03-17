package dev.maurer.controllers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import dev.maurer.entities.*;
import dev.maurer.exceptions.ExpenseNotFoundException;
import dev.maurer.exceptions.SubmissionException;
import dev.maurer.exceptions.SubmissionFormatException;
import dev.maurer.services.EmployeeService;
import dev.maurer.services.ExpenseService;
import dev.maurer.utils.JwtUtil;
import io.javalin.http.Handler;


import java.util.Collections;
import java.util.List;

import static dev.maurer.app.App.logger;

public class ExpenseController {

    private ExpenseService expenseService;
    private EmployeeService employeeService;
    private Gson gson;

    public ExpenseController(ExpenseService expenseService, EmployeeService employeeService) {
        this.expenseService = expenseService;
        this.employeeService = employeeService;
        gson = new Gson();
    }

    private Employee getEmployee(DecodedJWT decodedJWT) {
        try {
            Integer id = decodedJWT.getClaim("employeeId").asInt();
            String type = decodedJWT.getClaim("type").asString();

            if (id == null || type == null)
                return null;

            if (type.equals(EmployeeType.EMPLOYEE.toString()) || type.equals(EmployeeType.MANAGER.toString()))
                return employeeService.getEmployeeById(id);

            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public Handler submitExpenseHandler = ctx -> {
        String jwt = ctx.header("Authorization");
        try {
            String body = ctx.body();
            DecodedJWT decodedJWT = JwtUtil.isValidJWT(jwt);
            Employee employee = getEmployee(decodedJWT);

            if (employee instanceof Manager)
                throw new SubmissionException("Managers cannot submit Expenses");

            Expense expense = gson.fromJson(body, Expense.class);
            expense = expenseService.submitExpense(employee, expense);
            expense.setEmployee(null);
            ctx.result(gson.toJson(expense));
            ctx.status(201);

        } catch (JWTVerificationException e) {
            logger.warn("Unauthorized JWT: " + jwt);
            ctx.result("Invalid authorization");
            ctx.status(403);
        } catch (SubmissionException e) {
            ctx.result(e.getMessage());
            ctx.status(403);
        } catch (SubmissionFormatException e) {
            ctx.result(e.getMessage());
            ctx.status(400);
        } catch (Exception e) {
            logger.error(e.getMessage());
            ctx.result("Error");
            ctx.status(400);
        }
    };

    public Handler getExpensesHandler = ctx -> {
        String jwt = ctx.header("Authorization");
        try {
            DecodedJWT decodedJWT = JwtUtil.isValidJWT(jwt);
            Employee employee = getEmployee(decodedJWT);

            List<Expense> allExpenses;
            if (employee instanceof Manager)
                allExpenses = expenseService.viewAllExpenses((Manager) employee);
            else
                allExpenses = expenseService.viewAllExpenses(employee);

            for (Expense e : allExpenses) {
                e.setEmployee(null); //sanitize employee to prevent LazyInitializationException or infinite json
            }
            Collections.sort(allExpenses);
            String expenses = gson.toJson(allExpenses);
            ctx.result(expenses);
            ctx.status(200);

        } catch (JWTVerificationException e) {
            logger.warn("Unauthorized JWT: " + jwt);
            ctx.result("Invalid authorization");
            ctx.status(403);
        }
    };

    public Handler getExpenseByIdHandler = ctx -> {
        String jwt = ctx.header("Authorization");
        try {
            DecodedJWT decodedJWT = JwtUtil.isValidJWT(jwt);
            Employee employee = getEmployee(decodedJWT);
            Expense expense = expenseService.viewExpense(employee, Integer.parseInt(ctx.pathParam("id")));
            expense.setEmployee(null);
            ctx.result(gson.toJson(expense));
            ctx.status(200);
        } catch (JWTVerificationException e) {
            logger.warn("Unauthorized JWT: " + jwt);
            ctx.result("Invalid authorization");
            ctx.status(403);
        } catch (ExpenseNotFoundException e) {
            ctx.result("No expense found");
            ctx.status(404);
        }

    };

    public Handler updateExpenseHandler = ctx -> {
        String jwt = ctx.header("Authorization");
        try {
            DecodedJWT decodedJWT = JwtUtil.isValidJWT(jwt);
            Employee manager = getEmployee(decodedJWT);

            if (manager instanceof Manager) {
                String body = ctx.body();
                int expenseId = Integer.parseInt(ctx.pathParam("id"));
                Expense expense = gson.fromJson(body, Expense.class);
                if (expense.getExpenseStatus() == ExpenseStatus.PENDING || expense.getExpenseStatus() == null)
                    throw new SubmissionException("Expense update must have a status that is not pending");
                expense.setExpenseId(expenseId);
                expense = expenseService.updateExpenseStatus((Manager) manager, expense);
                expense.setEmployee(null);
                System.out.println(expense);
                ctx.result(gson.toJson(expense));
                ctx.status(200);

            } else {
                throw new JWTVerificationException(jwt);
            }
        } catch (JWTVerificationException e) {
            logger.warn("Unauthorized JWT: " + jwt);
            ctx.result("Invalid authorization");
            ctx.status(403);
        } catch (ExpenseNotFoundException e) {
            ctx.result("Expense not found");
            ctx.status(404);
        } catch (SubmissionException e) {
            ctx.result(e.getMessage());
            ctx.status(400);
        }
    };
}
